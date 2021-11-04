package io.github.thurein.carrier.domain.jwt;

import java.io.Serializable;

public interface JWTPayload extends Serializable {

    long getUserId();
    boolean isExpired();

}
