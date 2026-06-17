package com.vaishnavishealthcare.config.security;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Standard JWT claims used by the service.
 *
 * <ul>
 *   <li>{@code iss} — issuer (organisation issuing the token)</li>
 *   <li>{@code aud} — audience (intended recipient)</li>
 *   <li>{@code sub} — subject (the user/agent the token represents)</li>
 *   <li>{@code iat} — issued-at (epoch seconds)</li>
 *   <li>{@code exp} — expiry (epoch seconds)</li>
 *   <li>{@code prm} — opaque param/permission claim</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtPayload {

    private String iss;
    private String aud;
    private String sub;
    private long iat;
    private long exp;
    private String prm;

    public JwtPayload(String issuer, String audience, String subject, String param, long validitySeconds) {
        this.iss = issuer;
        this.aud = audience;
        this.sub = subject;
        this.iat = Instant.now().getEpochSecond();
        this.exp = this.iat + validitySeconds;
        this.prm = param;
    }
}
