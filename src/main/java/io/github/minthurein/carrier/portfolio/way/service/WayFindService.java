package io.github.minthurein.carrier.portfolio.way.service;

import io.github.minthurein.carrier.portfolio.way.domain.Way;

import java.util.Optional;

public interface WayFindService {

    Optional<Way> getWayBySlug(String slug);
}
