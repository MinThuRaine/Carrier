package io.github.minthurein.carrier.infrastructure.jwt.service;

import io.github.minthurein.carrier.infrastructure.jwt.utility.JWTPayload;
import io.github.minthurein.carrier.portfolio.user.domain.User;

import static java.lang.String.format;
import static java.time.Instant.now;

public class UserJWTPayload implements JWTPayload {

    private final long sub;
    private final String name;
    private final long iat;

    static UserJWTPayload of(User user, long epochSecondExpired) {
        return new UserJWTPayload(user.getId(), String.valueOf(user.getEmail()), epochSecondExpired);
    }

    UserJWTPayload(long sub, String name, long iat) {
        this.sub = sub;
        this.name = name;
        this.iat = iat;
    }

    @Override
    public long getUserId() {
        return sub;
    }

    @Override
    public boolean isExpired() {
        return iat < now().getEpochSecond();
    }

    @Override
    public String toString() {
        return format("{\"sub\":%d,\"name\":\"%s\",\"iat\":%d}", sub, name, iat);
    }
}
