package io.github.minthurein.carrier.portfolio.user.api;

import io.github.minthurein.carrier.infrastructure.jwt.utility.JWTSerializer;
import io.github.minthurein.carrier.portfolio.user.data.UserLoginRequestDTO;
import io.github.minthurein.carrier.portfolio.user.data.UserModel;
import io.github.minthurein.carrier.portfolio.user.data.UserPostRequestDTO;
import io.github.minthurein.carrier.portfolio.user.data.UserPutRequestDTO;
import io.github.minthurein.carrier.portfolio.user.domain.Email;
import io.github.minthurein.carrier.portfolio.user.service.UserService;
import io.github.minthurein.carrier.infrastructure.jwt.service.UserJWTPayload;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.of;

@RestController
class UserRestController {

    private final UserService userService;
    private final JWTSerializer jwtSerializer;

    UserRestController(UserService userService, JWTSerializer jwtSerializer) {
        this.userService = userService;
        this.jwtSerializer = jwtSerializer;
    }

    @PostMapping("/users")
    public UserModel postUser(@Valid @RequestBody UserPostRequestDTO dto) {
        final var userSaved = userService.signUp(dto.toSignUpRequest());
        return UserModel.fromUserAndToken(userSaved, jwtSerializer.jwtFromUser(userSaved));
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserModel> loginUser(@Valid @RequestBody UserLoginRequestDTO dto) {
        return of(userService.login(new Email(dto.getEmail()), dto.getPassword())
                .map(user -> UserModel.fromUserAndToken(user, jwtSerializer.jwtFromUser(user))));
    }

    @GetMapping("/user")
    public ResponseEntity<UserModel> getUser(@AuthenticationPrincipal UserJWTPayload jwtPayload) {
        return of(userService.findById(jwtPayload.getUserId())
                .map(user -> UserModel.fromUserAndToken(user, getCurrentCredential())));
    }

    @PutMapping("/user")
    public UserModel putUser(@AuthenticationPrincipal UserJWTPayload jwtPayload,
                             @Valid @RequestBody UserPutRequestDTO dto) {
        final var userUpdated = userService.updateUser(jwtPayload.getUserId(), dto.toUpdateRequest());
        return UserModel.fromUserAndToken(userUpdated, getCurrentCredential());
    }

    private static String getCurrentCredential() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getCredentials()
                .toString();
    }
}
