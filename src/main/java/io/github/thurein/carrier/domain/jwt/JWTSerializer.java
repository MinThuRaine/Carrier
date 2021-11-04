package io.github.thurein.carrier.domain.jwt;

import io.github.thurein.carrier.domain.user.User;

public interface JWTSerializer {

    String jwtFromUser(User user);

}
