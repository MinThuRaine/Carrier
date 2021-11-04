package io.github.thurein.carrier.application.user;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.thurein.carrier.domain.user.Email;
import io.github.thurein.carrier.domain.user.UserName;
import io.github.thurein.carrier.domain.user.UserSignUpRequest;
import lombok.Value;

import javax.validation.constraints.NotBlank;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
@Value
class UserPostRequestDTO {

    @javax.validation.constraints.Email
    String email;
    @NotBlank
    String username;
    @NotBlank
    String password;

    UserSignUpRequest toSignUpRequest() {
        return new UserSignUpRequest(
                new Email(email),
                new UserName(username),
                password);
    }

}
