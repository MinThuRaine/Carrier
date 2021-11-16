package io.github.minthurein.carrier.portfolio.way.comment.data;

import io.github.minthurein.carrier.portfolio.way.comment.domain.Comment;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class MultipleCommentModel {

    private List<CommentModel.CommentModelNested> comments;

    public List<CommentModel.CommentModelNested> getComments() {
        return comments;
    }

    public MultipleCommentModel(List<CommentModel.CommentModelNested> commentsCollected) {
    this.comments = commentsCollected;
    }

    public static MultipleCommentModel fromComments(Set<Comment> comments) {
        final var commentsCollected = comments.stream().map(CommentModel.CommentModelNested::fromComment)
                .collect(toList());
        return new MultipleCommentModel(commentsCollected);
    }
}
