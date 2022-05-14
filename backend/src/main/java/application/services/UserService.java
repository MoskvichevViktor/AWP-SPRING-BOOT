package application.services;

import application.constants.UserRole;
import application.dto.UserRegistrationDto;
import application.exception.AwpError;
import application.models.security.Role;
import application.models.security.User;
import application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> save(User user) {
        if (isExistsUser(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<?> delete(User user) {
        if (!isExistsUser(user.getUsername())) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        userRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByName(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " is not found.")
        );

        return new org.springframework.security.core.userdetails.
                User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(Collections.singletonList(user.getRole())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<UserRole> roles) {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.toString())).collect(Collectors.toList());
    }

    public boolean isExistsUser(String username) {
        return userRepository.existsAllByUsernameEquals(username);
    }


    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createNewUser(UserRegistrationDto userDto) {
        Date now = new Date();
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(authService.encrypt(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setRole(userDto.getUserRole());
        return user;
    }


}

