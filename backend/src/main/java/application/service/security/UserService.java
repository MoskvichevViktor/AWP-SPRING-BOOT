package application.service.security;

import application.constants.RolesNameEnum;
import application.models.security.User;

import java.util.List;

public interface UserService {
    User register(User user, RolesNameEnum rolesNameEnum);

    List<User> getAll();

    User findByUserName(String userName);

    User findById(Long id);

    void delete(Long id);
}
