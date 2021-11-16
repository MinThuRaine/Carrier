package io.github.minthurein.carrier.portfolio.user.service;

import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import java.util.Optional;

public interface UserFindService {

    public Optional<User> findById(long id);
    public Optional<User> findByUsername(UserName userName);

}