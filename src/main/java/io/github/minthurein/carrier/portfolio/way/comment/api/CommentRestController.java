package io.github.minthurein.carrier.portfolio.way.comment.api;

import io.github.minthurein.carrier.infrastructure.jwt.service.UserJWTPayload;
import io.github.minthurein.carrier.portfolio.way.comment.data.CommentModel;
import io.github.minthurein.carrier.portfolio.way.comment.data.CommentPostRequestDTO;
import io.github.minthurein.carrier.portfolio.way.comment.data.MultipleCommentModel;
import io.github.minthurein.carrier.portfolio.way.comment.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
class CommentRestController {

    private final CommentService commentService;

    CommentRestController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/ways/{slug}/comments")
    public CommentModel postComments(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                     @PathVariable String slug, @Valid @RequestBody CommentPostRequestDTO dto) {
        final var commentAdded = commentService.createComment(jwtPayload.getUserId(), slug, dto.getBody());
        return CommentModel.fromComment(commentAdded);
    }

    @GetMapping("/ways/{slug}/comments")
    public MultipleCommentModel getComments(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                                            @PathVariable String slug) {
        final var comments = commentService.getComments(jwtPayload.getUserId(), slug);
        return MultipleCommentModel.fromComments(comments);
    }

    @DeleteMapping("/ways/{slug}/comments/{id}")
    public void deleteComment(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                              @PathVariable String slug, @PathVariable long id) {
        commentService.deleteCommentById(jwtPayload.getUserId(), slug, id);
    }
}
