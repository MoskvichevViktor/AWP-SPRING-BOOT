package application.controllers.v1;

import application.dto.UserDto;
import application.dto.UserRegistrationDto;
import application.exception.AwpError;
import application.models.security.User;
import application.services.AuthService;
import application.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/users")
@RestController

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

    @PostMapping("")
    public ResponseEntity<?> createNewUser(@RequestBody UserRegistrationDto userDto) {
        if (userService.isExistsUser(userDto.getUsername())) {
            return new ResponseEntity<>(new AwpError("This username is occupied"), HttpStatus.CONFLICT);
        }
        Date now = new Date();
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(authService.encrypt(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setRole(userDto.getUserRole());
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
