package io.github.minthurein.carrier.portfolio.way.comment.service;

import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.user.service.UserFindService;
import io.github.minthurein.carrier.portfolio.way.comment.domain.Comment;
import io.github.minthurein.carrier.portfolio.way.service.WayFindService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

import static org.springframework.data.util.Optionals.mapIfAllPresent;

@Service
public class CommentService {

    private final UserFindService userFindService;
    private final WayFindService wayFindService;

    CommentService(UserFindService userFindService, WayFindService wayFindService) {
        this.userFindService = userFindService;
        this.wayFindService = wayFindService;
    }

    @Transactional
    public Comment createComment(long userId, String slug, String body) {
        return mapIfAllPresent(userFindService.findById(userId), wayFindService.getWayBySlug(slug),
                (user, way) -> user.writeCommentToWay(way, body))
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Set<Comment> getComments(long userId, String slug) {
        return mapIfAllPresent(userFindService.findById(userId), wayFindService.getWayBySlug(slug),
                User::viewWayComments)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional
    public void deleteCommentById(long userId, String slug, long commentId) {
        final var wayContainsComments = wayFindService.getWayBySlug(slug)
                .orElseThrow(NoSuchElementException::new);
        userFindService.findById(userId)
                .ifPresentOrElse(user -> user.deleteWayComment(wayContainsComments, commentId),
                        () -> {throw new NoSuchElementException();});
    }

}
