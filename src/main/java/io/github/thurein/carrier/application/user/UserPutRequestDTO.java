package io.github.thurein.carrier.application.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.thurein.carrier.domain.user.Email;
import io.github.thurein.carrier.domain.user.Image;
import io.github.thurein.carrier.domain.user.UserName;
import io.github.thurein.carrier.domain.user.UserUpdateRequest;
import lombok.Value;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;
import static java.util.Optional.ofNullable;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
class UserPutRequestDTO {

    String email;
    String username;
    String password;
    String bio;
    String image;

    UserUpdateRequest toUpdateRequest() {
        return UserUpdateRequest.builder()
                .emailToUpdate(ofNullable(email).map(Email::new).orElse(null))
                .userNameToUpdate(ofNullable(username).map(UserName::new).orElse(null))
                .imageToUpdate(ofNullable(image).map(Image::new).orElse(null))
                .passwordToUpdate(password)
                .bioToUpdate(bio)
                .build();
    }
}
