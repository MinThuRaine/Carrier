package io.github.minthurein.carrier.infrastructure.jwt.utility;
import io.github.minthurein.carrier.infrastructure.jwt.utility.JWTPayload;


public interface JWTDeserializer {

    JWTPayload jwtPayloadFromJWT(String jwtToken);

}
