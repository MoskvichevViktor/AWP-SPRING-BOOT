package application.controllers.v1;

import application.dto.AuthRequest;
import application.dto.AuthResponse;
import application.dto.UserDetailsForRegistrationDto;
import application.exception.AwpError;
import application.exception.JwtAuthenticationException;
import application.models.security.Role;
import application.models.security.User;
import application.services.RolesService;
import application.services.UserService;
import application.utils.jwtsecuriru.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RolesService rolesService;


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

    @PutMapping("")
    public ResponseEntity<?> createNewUser(@RequestBody UserDetailsForRegistrationDto userRegDto) {
        if (userService.isExistsUser(userRegDto.getUsername())) {
            return new ResponseEntity<>(new AwpError("This username is occupied"), HttpStatus.UNAUTHORIZED);
        }
        Date now = new Date();
        User user = new User();
        user.setUsername(userRegDto.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRegDto.getPassword()));
        user.setCreated_at(now);
        user.setUpdated_at(now);
        Role role = rolesService.getEmptyRole();
        user.setRoles(Collections.singletonList(role));
        userService.save(user);
        AuthRequest request = new AuthRequest(userRegDto.getUsername(), userRegDto.getPassword());
        return createAuthToken(request);
    }


}
