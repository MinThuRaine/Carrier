package io.github.minthurein.carrier.portfolio.way.domain;

import io.github.minthurein.carrier.portfolio.user.domain.User;
import io.github.minthurein.carrier.portfolio.way.comment.domain.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "ways")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Way {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @JoinColumn(name = "owner_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = EAGER)
    private User owner;

    @Embedded
    private WayContents contents;

    @Column(name = "created_at")
    @CreatedDate
    private Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Instant updatedAt;

    @JoinTable(name = "way_request",
            joinColumns = @JoinColumn(name = "way_id", referencedColumnName = "id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false))
    @ManyToMany(fetch = EAGER, cascade = PERSIST)
    private Set<User> userRequested = new HashSet<>();

    @OneToMany(mappedBy = "way", cascade = {PERSIST, REMOVE})
    private Set<Comment> comments = new HashSet<>();


    @Transient
    private boolean requested = false;

    public Way(User owner, WayContents contents) {
        this.owner = owner;
        this.contents = contents;
    }

    protected Way() {
    }

    public Way afterUserRequestWay(User user) {
        userRequested.add(user);
        return updateRequestByUser(user);
    }

    public Way afterUserUnRequestWay(User user) {
        userRequested.remove(user);
        return updateRequestByUser(user);
    }

    public void updateWay(WayUpdateRequest updateRequest) {
        contents.updateWayContentsIfPresent(updateRequest);
    }

    public Way updateRequestByUser(User user) {
        requested = userRequested.contains(user);
        return this;
    }

    public Comment addComment(User owner, String body) {
        final var commentToAdd = new Comment(this, owner, body);
        comments.add(commentToAdd);
        return commentToAdd;
    }

    public void removeCommentByUser(User user, long commentId) {
        final var commentsToDelete = comments.stream()
                .filter(comment -> comment.getId().equals(commentId))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
        if ( user.equals(owner) && !user.equals(commentsToDelete.getUser())) {
            throw new IllegalAccessError("Not authorized to delete comment");
        }
        comments.remove(commentsToDelete);
        System.out.println(comments);
        System.out.println(comments);

    }

    public User getOwner() {
        return owner;
    }

    public WayContents getContents() {
        return contents;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public int getRequestedCount() { return userRequested.size(); }

    public boolean isRequested() { return requested; }

    public Set<Comment> getComments() {
        return comments;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var way = (Way) o;
        return owner.equals(way.owner) && contents.getTitle().equals(way.contents.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, contents.getTitle());
    }

}
