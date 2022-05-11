package application.repositories.secueity;

import application.constants.RolesNameEnum;
import application.models.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    Role findByName(String name);
    Role findByName(RolesNameEnum name);
}
