package application.controllers.v1;

import application.controllers.exception_handler.AbstractAwpExceptionHandlerController;
import application.dto.AuthRequest;
import application.dto.AuthResponse;
import application.dto.UserDto;
import application.exception.AwpException;
import application.models.User;
import application.services.UserService;
import application.utils.jwtsecuriru.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController extends AbstractAwpExceptionHandlerController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @PostMapping("")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new AwpException("Incorrect username or password");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        AuthResponse response = new AuthResponse(token);
        Optional<User> loggedUser = userService.findUserByName(authRequest.getUsername());
        loggedUser.ifPresent(user -> {
            UserDto profile = new UserDto(user);
            response.setProfile(profile);
        });
        return ResponseEntity.ok(response);
    }

}

