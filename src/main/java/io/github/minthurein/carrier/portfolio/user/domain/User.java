package io.github.minthurein.carrier.portfolio.user.domain;


import io.github.minthurein.carrier.portfolio.way.comment.domain.Comment;
import io.github.minthurein.carrier.portfolio.way.domain.Way;
import io.github.minthurein.carrier.portfolio.way.domain.WayContents;
import io.github.minthurein.carrier.portfolio.way.domain.WayUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import io.github.minthurein.carrier.portfolio.user.domain.Email;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import io.github.minthurein.carrier.portfolio.user.domain.Profile;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.GenerationType.IDENTITY;

@Table(name = "users")
@Entity
public class User {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;

    @Embedded
    private Email email;

    @Embedded
    private Profile profile;

    @Embedded
    private Password password;

    @JoinTable(name = "user_followings",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followee_id", referencedColumnName = "id"))
    @OneToMany(cascade = REMOVE)
    private Set<User> followingUsers = new HashSet<>();

    @ManyToMany(mappedBy = "userRequested")
    private Set<Way> wayRequested = new HashSet<>();



    public static User of(Email email, UserName name, Password password) {
        return new User(email, new Profile(name), password);
    }

    private User(Email email, Profile profile, Password password) {
        this.email = email;
        this.profile = profile;
        this.password = password;
    }

    protected User() {
    }

    public Profile getProfile() {
        return profile;
    }

    public void changeEmail(Email email) {
        this.email = email;
    }

    public void changeImage(Image image) {
        profile.changeImage(image);
    }

    public void changePassword(Password password) {
        this.password = password;
    }

    public void changeName(UserName userName) {
        profile.changeUserName(userName);
    }

    public void changeBio(String bio) {
        profile.changeBio(bio);
    }

    public boolean matchesPassword(String rawPassword, PasswordEncoder passwordEncoder) {
        return password.matchesPassword(rawPassword, passwordEncoder);
    }

    public Profile viewProfile(User user) {
        return user.profile.withFollowing(followingUsers.contains(user));
    }

    public User followUser(User followee) {
        followingUsers.add(followee);
        return this;
    }

    public User unfollowUser(User followee) {
        followingUsers.remove(followee);
        return this;
    }

    public Way requestWay(Way wayToRequest) {
        wayRequested.add(wayToRequest);
        return wayToRequest.afterUserRequestWay(this);
    }

    public Way unrequestWay(Way wayToUnrequest) {
        wayRequested.remove(wayToUnrequest);
        return wayToUnrequest.afterUserUnRequestWay(this);
    }

    public void deleteWayComment(Way way, long commentId) {
        way.removeCommentByUser(this, commentId);
    }

    public Way writeWay(WayContents contents) {
        return new Way(this, contents);
    }

    public Way updateWay(Way way, WayUpdateRequest request) {
        if (way.getOwner() != this) {
            throw new IllegalAccessError("Not authorized to update this way");
        }
        way.updateWay(request);
        return way;
    }

    public Comment writeCommentToWay(Way way, String body) {
        return way.addComment(this, body);
    }

    public Set<Comment> viewWayComments(Way way) {
        return way.getComments().stream()
                .map(this::viewComment)
                .collect(toSet());
    }

    Comment viewComment(Comment comment) {
        viewProfile(comment.getUser());
        return comment;
    }

    public Long getId() {
        return id;
    }

    public Email getEmail() {
        return email;
    }

    public UserName getName() {
        return profile.getUserName();
    }

    String getBio() {
        return profile.getBio();
    }

    Image getImage() {
        return profile.getImage();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final var user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


}
