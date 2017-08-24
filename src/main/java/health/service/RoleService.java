package health.service;

import health.domain.Role;

public interface RoleService {
	public Role findByRole(String roleName);
	public void saveRole(Role role);
}
