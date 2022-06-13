package application.services;

import application.constants.UserRole;
import application.dto.UserDto;
import application.dto.UserUpdateDto;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
        User user = userRepository.findById(id).orElseThrow(() ->
                new AwpException("user with id:" + id + " not found"));
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

    public UserDto getById(Long id) {
        return userRepository.findById(id).map(UserDto::new).orElse(null);
    }

    public ResponseEntity<?> save(UserUpdateDto userDto) {
        User user = userDto.valueOf(userDto);
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}

