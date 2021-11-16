package io.github.minthurein.carrier.portfolio.way.service;


import io.github.minthurein.carrier.portfolio.tag.service.TagService;
import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import io.github.minthurein.carrier.portfolio.user.service.UserFindService;
import io.github.minthurein.carrier.portfolio.way.domain.Way;
import io.github.minthurein.carrier.portfolio.way.domain.WayContents;
import io.github.minthurein.carrier.portfolio.way.domain.WayRepository;
import io.github.minthurein.carrier.portfolio.way.domain.WayUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.data.util.Optionals.mapIfAllPresent;

@Service
public class WayService implements WayFindService {

    private final UserFindService userFindService;
    private final TagService tagService;
    private final WayRepository wayRepository;

    WayService(UserFindService userFindService, TagService tagService, WayRepository wayRepository) {
        this.userFindService = userFindService;
        this.tagService = tagService;
        this.wayRepository = wayRepository;
    }

    @Transactional
    public Way createNewWay(long ownerId, WayContents contents) {
        final var tagsReloaded = tagService.reloadAllTagsIfAlreadyPresent(contents.getTags());
        contents.setTags(tagsReloaded);
        return userFindService.findById(ownerId)
                .map(owner -> owner.writeWay(contents))
                .map(wayRepository::save)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Page<Way> getWays(Pageable pageable) {
        return wayRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Way> getFeedByUserId(long userId, Pageable pageable) {
        return userFindService.findById(userId)
                .map(user -> wayRepository.findAllByUserRequestedContains(user, pageable)
                        .map(owner -> owner.updateRequestByUser(user)))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Page<Way> getWayByOwnerName(String owner, Pageable pageable) {
        return wayRepository.findAllByOwnerProfileUserName(new UserName(owner), pageable);
    }

    @Transactional(readOnly = true)
    public Page<Way> getWaysByTag(String tagValue, Pageable pageable) {
        return tagService.findByValue(tagValue)
                .map(tag -> wayRepository.findAllByContentsTagsContains(tag, pageable))
                .orElse(Page.empty());
    }

    @Transactional
    public Way updateWay(long userId, String slug, WayUpdateRequest request) {
        return mapIfAllPresent(userFindService.findById(userId), getWayBySlug(slug),
                (user, owner) -> user.updateWay(owner, request))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Way requestWay(long userId, String waySlugToRequest) {
        return mapIfAllPresent(
                userFindService.findById(userId), getWayBySlug(waySlugToRequest),
                User::requestWay)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public Way unrequestWay(long userId, String waySlugToUnRequest) {
        return mapIfAllPresent(
                userFindService.findById(userId), getWayBySlug(waySlugToUnRequest),
                User::unrequestWay)
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Way> getWayBySlug(String slug) {
        return wayRepository.findFirstByContentsTitleSlug(slug);
    }

    @Transactional
    public void deleteWayBySlug(long userId, String slug) {
        userFindService.findById(userId)
                .ifPresentOrElse(user -> wayRepository.deleteWayByOwnerAndContentsTitleSlug(user, slug),
                        () -> {throw new NoSuchElementException();});
    }

    @Transactional(readOnly = true)
    public Page<Way> getWayRequestedByUsername(UserName username, Pageable pageable) {
        return userFindService.findByUsername(username)
                .map(user -> wayRepository.findAllByUserRequestedContains(user, pageable)
                        .map(way -> way.updateRequestByUser(user)))
                .orElse(Page.empty());
    }

}
