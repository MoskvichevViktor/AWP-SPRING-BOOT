package application.controllers.v1;

import application.dto.AuthRequest;
import application.dto.AuthResponse;
import application.dto.UserDto;
import application.exception.JwtAuthenticationException;
import application.exception.ResourceNotFoundException;
import application.models.User;
import application.services.UserService;
import application.utils.jwtsecuriru.JwtTokenUtil;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("")
    public ResponseEntity<?> createAuthToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new JwtAuthenticationException("Incorrect username or password"), HttpStatus.UNAUTHORIZED);
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

