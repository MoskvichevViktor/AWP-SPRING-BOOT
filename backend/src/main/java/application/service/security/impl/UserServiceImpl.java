package application.service.security.impl;

import application.models.security.Role;
import application.models.security.User;
import application.repositories.secueity.RoleRepository;
import application.repositories.secueity.UserRepository;
import application.constants.RolesNameEnum;
import application.service.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует юзера
     *
     * @param user      с ролью
     * @param rolesName и сохраняет в БД
     * @return зарегистрированный юзер
     */
    @Override
    public User register(User user, RolesNameEnum rolesName) {
        if (roleRepository.findByName(rolesName) == null) {
            log.warn("IN register - role: {} not found", rolesName);
            return null;
        }
        Role roleUser = roleRepository.findByName(rolesName);
        List<Role> roleList = new ArrayList<>();
        roleList.add(roleUser);
        // Храним пароль в закодированном виде
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roleList);
        User registeredUser = userRepository.save(user);
        log.info("IN register - user: {} successful registration", user);
        return registeredUser;
    }

    @Override
    public List<User> getAll() {

        List<User> userList = userRepository.findAll();
        if (userList.size() == 0) {
            log.warn("IN getAll - user not found");
            return userList;
        }
        log.info("IN getAll - {} users found", userList.size());
        return userList;
    }

    @Override
    public User findByUserName(String userName) {
        User resultUser = userRepository.findByUsername(userName);
        if (resultUser == null) {
            log.info("IN findByUserName name- {},  users not found", userName);
            return null;
        }
        log.info("IN findByUserName name- {},  users found {}", userName, resultUser);
        return resultUser;
    }

    @Override
    public User findById(Long id) {
        User resultUser = userRepository.findById(id).orElse(null);
        if (resultUser == null) {
            log.warn("IN findById user id {} not found", id);
            return null;
        }
        log.info("IN findById id- {},  users found {}", id, resultUser);
        return resultUser;
    }

    @Override
    public void delete(Long id) {
        if (userRepository.findById(id).orElse(null) == null) {
            log.warn("IN delete user with id {} not found", id);
        }
        userRepository.deleteById(id);
        log.info("IN delete user with id {} successful deleted", id);
    }
}
