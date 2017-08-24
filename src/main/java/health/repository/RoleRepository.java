package health.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import health.domain.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role,Long> {
	Role findByRole(String roleName);
	Role findById(int id);
}
