package application.services;

import application.constants.UserRole;
import application.models.security.Role;
import application.models.security.User;
import application.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        return userRepository.save(user);
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

}

