package io.github.minthurein.carrier.portfolio.way.comment.domain;

import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.way.domain.Way;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "comments")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "way_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private Way way;

    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private User user;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "body", nullable = false)
    private String body;

    public Comment(Way way, User user, String body) {
        this.way = way;
        this.user = user;
        this.body = body;
    }

    protected Comment() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var comment = (Comment) o;
        return way.equals(comment.way) && user.equals(comment.user) && Objects.equals(createdAt, comment.createdAt) && body.equals(comment.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(way, user, createdAt, body);
    }
}
