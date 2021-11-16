package io.github.minthurein.carrier.portfolio.tag.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends Repository<Tag, Long> {

    List<Tag> findAll();

    Optional<Tag> findFirstByValue(String value);
}
