package io.github.minthurein.carrier.portfolio.way.comment.data;

import io.github.minthurein.carrier.portfolio.user.data.ProfileModel;
import io.github.minthurein.carrier.portfolio.way.comment.domain.Comment;


import java.time.ZoneId;
import java.time.ZonedDateTime;


public class CommentModel {

    private CommentModelNested comment;

    public CommentModelNested getComment() {
        return comment;
    }

    public CommentModel(CommentModelNested fromComment) {
        this.comment = fromComment;
    }

    public static CommentModel fromComment(Comment comment) {
        return new CommentModel(CommentModelNested.fromComment(comment));
    }


    public static class CommentModelNested {
        private long id;
        private String body;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;
        private ProfileModel.ProfileModelNested user;

        public long getId() {
            return id;
        }

        public String getBody() {
            return body;
        }

        public ZonedDateTime getCreatedAt() {
            return createdAt;
        }

        public ZonedDateTime getUpdatedAt() {
            return updatedAt;
        }

        public ProfileModel.ProfileModelNested getUser() {
            return user;
        }

        public CommentModelNested(Long id, String body, ZonedDateTime createdAt, ZonedDateTime updatedAt, ProfileModel.ProfileModelNested user) {
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        }

        static CommentModelNested fromComment(Comment comment) {
            return new CommentModelNested(comment.getId(),
                    comment.getBody(),
                    comment.getCreatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    comment.getUpdatedAt().atZone(ZoneId.of("Asia/Seoul")),
                    ProfileModel.ProfileModelNested.fromProfile(comment.getUser().getProfile()));
        }
    }
}
