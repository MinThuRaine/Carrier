package io.github.minthurein.carrier.infrastructure.jwt.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.github.minthurein.carrier.infrastructure.jwt.service.HmacSHA256JWTService;
import java.nio.charset.StandardCharsets;

@Configuration
public class JWTConfiguration {

    private static final byte[] SECRET = "SOME_SIGNATURE_SECRET".getBytes(StandardCharsets.UTF_8);
    private static final int JWT_DURATION_SECONDS = 2 * 60 * 60;

    @Bean
    HmacSHA256JWTService hmacSHA256JWTService(ObjectMapper objectMapper) {
        return new HmacSHA256JWTService(SECRET, JWT_DURATION_SECONDS, objectMapper);
    }

}
