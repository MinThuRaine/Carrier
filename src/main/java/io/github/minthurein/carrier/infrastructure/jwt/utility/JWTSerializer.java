package io.github.minthurein.carrier.infrastructure.jwt.utility;

import io.github.minthurein.carrier.portfolio.user.domain.User;

public interface JWTSerializer {

    String jwtFromUser(User user);

}
