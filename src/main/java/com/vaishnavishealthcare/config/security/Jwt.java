package com.vaishnavishealthcare.config.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import tools.jackson.databind.ObjectMapper;
import com.vaishnavishealthcare.config.AppProperties;
import com.vaishnavishealthcare.common.exception.BadTokenException;
import com.vaishnavishealthcare.common.exception.InternalException;
import com.vaishnavishealthcare.common.exception.TokenExpiredException;

import lombok.RequiredArgsConstructor;

/**
 * Minimal, dependency-free JWT utility using HMAC-SHA256 (HS256) with the
 * shared secret from {@link AppProperties}. It mirrors the reference service's
 * {@code core/JWT} module ({@code encode}/{@code validate}/{@code decode}).
 *
 * <p>HS256 is used instead of RS256 so no external library or key files are
 * required; the signing secret is configured via {@code app.jwt.secret}.
 */
@Component
@RequiredArgsConstructor
public class Jwt {

    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final String HEADER_JSON = "{\"alg\":\"HS256\",\"typ\":\"JWT\"}";

    private final AppProperties properties;
    private final ObjectMapper objectMapper;

    private final Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private final Base64.Decoder decoder = Base64.getUrlDecoder();

    /** Signs the payload and returns a compact JWT string. */
    public String encode(JwtPayload payload) {
        try {
            String header = encoder.encodeToString(HEADER_JSON.getBytes(StandardCharsets.UTF_8));
            String body = encoder.encodeToString(objectMapper.writeValueAsBytes(payload));
            String signingInput = header + "." + body;
            return signingInput + "." + sign(signingInput);
        } catch (Exception e) {
            throw new InternalException("Token generation failure");
        }
    }

    /** Verifies the signature and rejects expired tokens. */
    public JwtPayload validate(String token) {
        JwtPayload payload = verifyAndParse(token);
        if (payload.getExp() < Instant.now().getEpochSecond()) {
            throw new TokenExpiredException();
        }
        return payload;
    }

    /** Verifies the signature but ignores expiry. */
    public JwtPayload decode(String token) {
        return verifyAndParse(token);
    }

    private JwtPayload verifyAndParse(String token) {
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new BadTokenException();
            }
            String signingInput = parts[0] + "." + parts[1];
            if (!constantTimeEquals(sign(signingInput), parts[2])) {
                throw new BadTokenException();
            }
            byte[] payloadBytes = decoder.decode(parts[1]);
            return objectMapper.readValue(payloadBytes, JwtPayload.class);
        } catch (BadTokenException | TokenExpiredException e) {
            throw e;
        } catch (Exception e) {
            throw new BadTokenException();
        }
    }

    private String sign(String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGORITHM);
        byte[] secret = properties.getJwt().getSecret().getBytes(StandardCharsets.UTF_8);
        mac.init(new SecretKeySpec(secret, HMAC_ALGORITHM));
        return encoder.encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
    }

    private boolean constantTimeEquals(String a, String b) {
        return MessageDigest.isEqual(
                a.getBytes(StandardCharsets.UTF_8),
                b.getBytes(StandardCharsets.UTF_8));
    }
}
