package com.assessment.tournament.infrastructure.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class JwtClaimsResolverTest {

    private JwtClaimsResolver jwtClaimsResolver;
    private static final String VALID_JWT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
    private static final String AUTH_HEADER = "Bearer " + VALID_JWT;

    @BeforeEach
    void setUp() {
        jwtClaimsResolver = new JwtClaimsResolver();
    }

    @Test
    void extractToken_ShouldReturnJwtToken_WhenValidAuthorizationHeader() {
        // Act
        String token = jwtClaimsResolver.extractToken(AUTH_HEADER);

        // Assert
        assertEquals(VALID_JWT, token);
    }
}
