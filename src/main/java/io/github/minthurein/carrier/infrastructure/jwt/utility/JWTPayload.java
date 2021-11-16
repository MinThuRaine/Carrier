package io.github.minthurein.carrier.infrastructure.jwt.utility;

import java.io.Serializable;

public interface JWTPayload extends Serializable {

    long getUserId();
    boolean isExpired();

}
