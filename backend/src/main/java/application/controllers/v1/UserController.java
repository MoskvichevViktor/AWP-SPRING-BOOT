package application.controllers.v1;

import application.dto.UserDto;
import application.dto.UserRegistrationDto;
import application.exception.AwpError;
import application.models.User;
import application.services.AuthService;
import application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/users")
@RestController//для json

@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<UserDto> getAll() {
        List<User> users = userService.getAll();
        return users.stream().map(UserDto::new).collect(Collectors.toList());
    }


    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/update")
    public ResponseEntity<?> saveOrUpdate(@RequestBody User user) {
        return userService.save(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/del")
    public ResponseEntity<?> delete(@RequestBody User user) {
        return userService.delete(user);
    }

    @PostMapping("")
    public ResponseEntity<?> createNewUser(@RequestBody UserRegistrationDto userDto) {
        if (userService.isExistsUser(userDto.getUsername())) {
            return new ResponseEntity<>(new AwpError("This username is occupied"), HttpStatus.CONFLICT);
        }
        userDto.setPassword(authService.encrypt(userDto.getPassword()));
        return userService.createNewUser(userDto);
    }

}
