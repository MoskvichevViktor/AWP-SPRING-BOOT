package application.controllers.v1;

import application.dto.AuthRequest;
import application.dto.AuthResponse;
import application.dto.UserRegistrationDto;
import application.exception.JwtAuthenticationException;
import application.exception.ResourceNotFoundException;
import application.models.User;
import application.services.UserService;
import application.utils.jwtsecuriru.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUserDetails() {
        String name = userService.getCurrentUserName();
        if (name == null) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findUserByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No user with name: " + name));
        return new ResponseEntity(new UserRegistrationDto(user), HttpStatus.OK);
    }

}

