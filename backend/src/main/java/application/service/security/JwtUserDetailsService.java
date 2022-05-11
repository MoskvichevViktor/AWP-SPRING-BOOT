package application.service.security;

import application.models.security.User;
import application.utils.jwtsecuriru.JwtUser;
import application.utils.jwtsecuriru.JwtUserFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Метод генерирует и возвращает JwtUserDetails из юзера с именем
     *
     * @param username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);
        if (user == null) {
            log.warn("IN JwtUserDetailsService. User with name: {} not found", username);
            throw new UsernameNotFoundException("User with name: " + username + " not found");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN loadUserByUsername - jwtUser with username {} successfully loaded", username);
        return jwtUser;
    }
}
