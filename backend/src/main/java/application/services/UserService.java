package application.services;

import application.constants.UserRole;
import application.dto.UserRegistrationDto;
import application.exception.AwpException;
import application.models.User;
import application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public ResponseEntity<?> save(User user) {
        if (user.getRole() == null) {
            return new ResponseEntity<>(new IllegalArgumentException("Role can't be empty"), HttpStatus.NOT_IMPLEMENTED);
        }
        if (isExistsUser(user.getUsername())) {
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @SneakyThrows
    @Transactional
    public ResponseEntity<?> delete(Long id) {
        User user = getUserById(id).orElseThrow(() ->
                new AwpException("user with id:" + id + " tot found"));
        if (user == null || user.getUsername().equals("admin")) {
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

    public ResponseEntity<?> createNewUser(UserRegistrationDto userDto) {
        Date now = new Date();
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        //TODO определиться с ролью по умолчанию
        user.setRole(UserRole.ROLE_MANAGER);
        user.setEmail(userDto.getEmail());
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

