package io.github.minthurein.carrier.portfolio.user.data;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.minthurein.carrier.portfolio.user.domain.User;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("user")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class UserModel {

    private String email;
    private String username;
    private String token;
    private String bio;
    private String image;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UserModel(String email, String username, String token, String bio, String image) {
      this.email = email;
      this.username = username;
      this.token = token;
      this.bio = bio;
      this.image = image;
    }

    public static UserModel fromUserAndToken(User user, String token) {
        return new UserModel(
                String.valueOf(user.getEmail()),
                String.valueOf(user.getName()),
                token,
                "",
                "");
    }



}
