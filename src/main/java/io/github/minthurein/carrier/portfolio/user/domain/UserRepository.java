package io.github.minthurein.carrier.portfolio.user.domain;

import org.springframework.data.repository.Repository;
import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.user.domain.Email;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;

import java.util.Optional;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);
    Optional<User> findById(long id);
    Optional<User> findFirstByEmail(Email email);
    Optional<User> findFirstByProfileUserName(UserName userName);

}
