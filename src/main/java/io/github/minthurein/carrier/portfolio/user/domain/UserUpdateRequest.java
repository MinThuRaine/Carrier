package io.github.minthurein.carrier.portfolio.user.domain;

import java.util.Optional;
import io.github.minthurein.carrier.portfolio.user.domain.Email;
import io.github.minthurein.carrier.portfolio.user.domain.UserName;
import io.github.minthurein.carrier.portfolio.user.domain.Image;
import static java.util.Optional.ofNullable;

public class UserUpdateRequest {

    private final Email emailToUpdate;
    private final UserName userNameToUpdate;
    private final String passwordToUpdate;
    private final Image imageToUpdate;
    private final String bioToUpdate;


    public static UserUpdateRequestBuilder builder() {
        return new UserUpdateRequestBuilder();
    }

    public Optional<Email> getEmailToUpdate() {
        return ofNullable(emailToUpdate);
    }

    public Optional<UserName> getUserNameToUpdate() {
        return ofNullable(userNameToUpdate);
    }

    public Optional<String> getPasswordToUpdate() {
        return ofNullable(passwordToUpdate);
    }

    public Optional<Image> getImageToUpdate() {
        return ofNullable(imageToUpdate);
    }

    public Optional<String> getBioToUpdate() {
        return ofNullable(bioToUpdate);
    }

    private UserUpdateRequest(UserUpdateRequestBuilder builder) {
        this.emailToUpdate = builder.emailToUpdate;
        this.userNameToUpdate = builder.userNameToUpdate;
        this.passwordToUpdate = builder.passwordToUpdate;
        this.imageToUpdate = builder.imageToUpdate;
        this.bioToUpdate = builder.bioToUpdate;
    }

    public static class UserUpdateRequestBuilder {
        private Email emailToUpdate;
        private UserName userNameToUpdate;
        private String passwordToUpdate;
        private Image imageToUpdate;
        private String bioToUpdate;

        public UserUpdateRequestBuilder emailToUpdate(Email emailToUpdate) {
            this.emailToUpdate = emailToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder userNameToUpdate(UserName userNameToUpdate) {
            this.userNameToUpdate = userNameToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder passwordToUpdate(String passwordToUpdate) {
            this.passwordToUpdate = passwordToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder imageToUpdate(Image imageToUpdate) {
            this.imageToUpdate = imageToUpdate;
            return this;
        }

        public UserUpdateRequestBuilder bioToUpdate(String bioToUpdate) {
            this.bioToUpdate = bioToUpdate;
            return this;
        }

        public UserUpdateRequest build() {
            return new UserUpdateRequest(this);
        }

    }
}
