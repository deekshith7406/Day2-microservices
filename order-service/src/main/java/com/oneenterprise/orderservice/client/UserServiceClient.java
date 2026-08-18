package com.oneenterprise.orderservice.client;

import com.oneenterprise.orderservice.config.UserServiceProperties;
import com.oneenterprise.orderservice.dto.UserSummary;
import com.oneenterprise.orderservice.exception.RelatedUserNotFoundException;
import com.oneenterprise.orderservice.exception.UserServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;

/**
 * The one place in Order Service that knows how to talk to User Service.
 * All connection details (base URL, timeouts) come from
 * {@link UserServiceProperties} — externalized configuration, per the
 * Day 2 "stop hard-coding environment details" challenge.
 *
 * Day 2 focus: the network can fail in more than one way, and this class
 * tells those ways apart rather than collapsing them into one generic error:
 *   - user genuinely doesn't exist       → RelatedUserNotFoundException (404)
 *   - can't connect / DNS failure         → UserServiceUnavailableException (503)
 *   - connected, but response took too
 *     long (read timeout)                 → UserServiceUnavailableException (503)
 *   - User Service itself errored (5xx)   → UserServiceUnavailableException (503)
 * All three "unavailable" cases still return the same 503 to Order Service's
 * own callers (that's the right contract — Order Service's problem is the
 * same either way), but they are logged differently so whoever operates
 * this service can tell a slow dependency from a dead one.
 */
@Component
public class UserServiceClient {

    private static final Logger log = LoggerFactory.getLogger(UserServiceClient.class);

    private final RestTemplate restTemplate;
    private final UserServiceProperties userServiceProperties;

    public UserServiceClient(RestTemplate restTemplate, UserServiceProperties userServiceProperties) {
        this.restTemplate = restTemplate;
        this.userServiceProperties = userServiceProperties;
    }

    public UserSummary getUserById(Long userId) {
        String baseUrl = userServiceProperties.getBaseUrl();
        String url = baseUrl + "/api/users/" + userId;
        try {
            return restTemplate.getForObject(url, UserSummary.class);
        } catch (HttpClientErrorException.NotFound ex) {
            // User Service responded, and it said "no such user" — 404 is a
            // legitimate business outcome, not an infrastructure failure.
            log.info("User {} not found in User Service", userId);
            throw new RelatedUserNotFoundException(userId);
        } catch (ResourceAccessException ex) {
            if (ex.getCause() instanceof SocketTimeoutException) {
                // Connected fine, but User Service didn't answer within
                // readTimeoutMs — "too slow" is a different problem than "down".
                log.warn("User Service at {} timed out responding for user {}", baseUrl, userId);
            } else {
                // Connection refused / DNS failure — User Service is down
                // or unreachable, not merely slow.
                log.warn("Could not connect to User Service at {} for user {}: {}",
                        baseUrl, userId, ex.getMessage());
            }
            throw new UserServiceUnavailableException(
                    "Could not reach User Service at " + baseUrl, ex);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            // Any other 4xx/5xx from User Service — treat as unavailable
            // from Order Service's point of view.
            log.warn("User Service returned {} for user {}", ex.getStatusCode(), userId);
            throw new UserServiceUnavailableException(
                    "User Service returned an unexpected error: " + ex.getStatusCode(), ex);
        }
    }
}
