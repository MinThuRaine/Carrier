package io.github.minthurein.carrier.portfolio.user.data;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import io.github.minthurein.carrier.portfolio.user.domain.Profile;

public class ProfileModel {

    ProfileModelNested profile;

    public ProfileModelNested getProfile() {
        return profile;
    }

    public ProfileModel(ProfileModelNested fromProfile) {
        this.profile = fromProfile;
    }

    public static ProfileModel fromProfile(Profile profile) {
        return new ProfileModel(ProfileModelNested.fromProfile(profile));
    }

    public static class ProfileModelNested {
        private String username;
        private String bio;
        private String image;
        private boolean following;

        public String getUsername() {
            return username;
        }

        public String getBio() {
            return bio;
        }

        public String getImage() {
            return image;
        }

        public boolean isFollowing() {
            return following;
        }

        public ProfileModelNested(String username, String bio, String image, boolean following) {
        this.username = username;
        this.bio = bio;
        this.image = image;
        this.following = following;
        }

        public static ProfileModelNested fromProfile(Profile profile) {
            return new ProfileModelNested(String.valueOf(profile.getUserName()),
                    profile.getBio(),
                    String.valueOf(profile.getImage()),
                    profile.isFollowing());
        }
    }
}
