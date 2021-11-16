package io.github.minthurein.carrier.infrastructure.security.service;

import io.github.minthurein.carrier.infrastructure.jwt.utility.JWTDeserializer;
import io.github.minthurein.carrier.infrastructure.jwt.utility.JWTPayload;
import io.github.minthurein.carrier.infrastructure.security.filter.JWTAuthenticationFilter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static java.util.Collections.singleton;
import static java.util.Optional.of;

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final JWTDeserializer jwtDeserializer;

    public JWTAuthenticationProvider(JWTDeserializer jwtDeserializer) {
        this.jwtDeserializer = jwtDeserializer;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return of(authentication).map(JWTAuthenticationFilter.JWT.class::cast)
                .map(JWTAuthenticationFilter.JWT::getPrincipal)
                .map(Object::toString)
                .map(token -> new JWTAuthentication(token, jwtDeserializer.jwtPayloadFromJWT(token)))
                .orElseThrow(IllegalStateException::new);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationFilter.JWT.class.isAssignableFrom(authentication);
    }

    @SuppressWarnings("java:S2160")
    private class JWTAuthentication extends AbstractAuthenticationToken {

        private final JWTPayload jwtPayload;
        private final String token;

        private JWTAuthentication(String token, JWTPayload jwtPayload) {
            super(singleton(new SimpleGrantedAuthority("USER")));
            super.setAuthenticated(true);
            this.jwtPayload = jwtPayload;
            this.token = token;
        }

        @Override
        public Object getPrincipal() {
            return jwtPayload;
        }

        @Override
        public Object getCredentials() {
            return token;
        }
    }

}
