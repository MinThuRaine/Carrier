package io.github.minthurein.carrier.portfolio.user.domain;

import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import io.github.minthurein.carrier.portfolio.user.domain.Image;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Transient;

@Embeddable
public class Profile {

    @Embedded
    private UserName userName;

    @Column(name = "bio")
    private String bio;

    @Embedded
    private Image image;

    @Transient
    private boolean following;

    public Profile(UserName userName) {
        this(userName, null, null);
    }

    public Profile(UserName userName, String bio, Image image) {
        this.userName = userName;
        this.bio = bio;
        this.image = image;


    }

    protected Profile() {
    }

    public UserName getUserName() {
        return userName;
    }
    public String getBio() {
        return bio;
    }
    public Image getImage() {
        return image;
    }

    public Profile withFollowing(boolean following) {
        return this;
    }

    public boolean isFollowing() {
        return following;
    }

    public void changeUserName(UserName userName) {
        this.userName = userName;
    }
    public void changeBio(String bio) {
        this.bio = bio;
    }
    public void changeImage(Image image) {
        this.image = image;
    }
}
