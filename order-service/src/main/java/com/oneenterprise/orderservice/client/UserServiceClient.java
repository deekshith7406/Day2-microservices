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
           
            log.info("User {} not found in User Service", userId);
            throw new RelatedUserNotFoundException(userId);
        } catch (ResourceAccessException ex) {
            if (ex.getCause() instanceof SocketTimeoutException) {
              
                log.warn("User Service at {} timed out responding for user {}", baseUrl, userId);
            } else {
              
                log.warn("Could not connect to User Service at {} for user {}: {}",
                        baseUrl, userId, ex.getMessage());
            }
            throw new UserServiceUnavailableException(
                    "Could not reach User Service at " + baseUrl, ex);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
           
            log.warn("User Service returned {} for user {}", ex.getStatusCode(), userId);
            throw new UserServiceUnavailableException(
                    "User Service returned an unexpected error: " + ex.getStatusCode(), ex);
        }
    }
}
