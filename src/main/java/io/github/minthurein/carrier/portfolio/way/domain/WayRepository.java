package io.github.minthurein.carrier.portfolio.way.domain;

import io.github.minthurein.carrier.portfolio.tag.domain.Tag;
import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;


public interface WayRepository extends Repository<Way, Long> {

    Way save(Way way);

    Page<Way> findAll(Pageable pageable);
    Page<Way> findAllByUserRequestedContains(User user, Pageable pageable);
    Page<Way> findAllByOwnerProfileUserName(UserName ownerName, Pageable pageable);
    Page<Way> findAllByContentsTagsContains(Tag tag, Pageable pageable);
    Optional<Way> findFirstByContentsTitleSlug(String slug);

    void deleteWayByOwnerAndContentsTitleSlug(User owner, String slug);

}
