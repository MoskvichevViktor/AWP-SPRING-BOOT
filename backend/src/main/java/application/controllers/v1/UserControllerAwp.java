package application.controllers.v1;

import application.controllers.exception_handler.AbstractAwpExceptionHandlerController;
import application.dto.UserDto;
import application.dto.UserRegistrationDto;
import application.exception.AwpException;
import application.models.User;
import application.services.AuthService;
import application.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/users")
@RestController

@RequiredArgsConstructor
public class UserControllerAwp extends AbstractAwpExceptionHandlerController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAll() {
        List<User> users = userService.getAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PatchMapping("")
    public ResponseEntity<?> update(@RequestBody User user) {
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return userService.delete(id);
    }

    @SneakyThrows
    @PostMapping("")
    public ResponseEntity<?> createNewUser(@RequestBody UserRegistrationDto userDto) {
        if (userService.isExistsUser(userDto.getUsername())) {
            throw new AwpException("This username is occupied");
        }
        userDto.setPassword(authService.encrypt(userDto.getPassword()));
        return userService.createNewUser(userDto);
    }

}
