package io.github.thurein.carrier.domain.jwt;

public interface JWTDeserializer {

    JWTPayload jwtPayloadFromJWT(String jwtToken);

}
