package com.oneenterprise.orderservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CONFIGURATION CONTRACT — everything Order Service needs to know about
 * *how* to reach User Service, in one typed, externalized place.
 *
 * Backed by the "user-service.*" prefix in application.yml. None of these
 * values are hard-coded in Java — change environments by changing
 * configuration (a property file, an environment variable, or a
 * command-line override), never by editing this class or the client code
 * that uses it.
 *
 * Example overrides:
 *   --user-service.base-url=http://user-service.internal:8081
 *   USER_SERVICE_BASEURL=http://user-service.internal:8081   (env var form)
 */
@ConfigurationProperties(prefix = "user-service")
public class UserServiceProperties {

    /** Where User Service can be reached, e.g. http://localhost:8081 */
    private String baseUrl;

    /** How long to wait to establish the TCP connection before giving up. */
    private int connectTimeoutMs = 3000;

    /** How long to wait for a response after the connection is made. */
    private int readTimeoutMs = 3000;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public int getConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(int connectTimeoutMs) {
        this.connectTimeoutMs = connectTimeoutMs;
    }

    public int getReadTimeoutMs() {
        return readTimeoutMs;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }
}
