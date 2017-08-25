package health.repository;
import health.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User,Long> {
	User findByFirstName(String firstName);
	User findByEmail(String email);
	
	//update user set their status to enabled since they go through email
	//validation
	
	@Modifying(clearAutomatically = true)
	@Transactional(propagation=Propagation.REQUIRED)
	@Query("update User u set u.enabled = :enabled where u.email = :email")
	int enableUser(@Param("enabled")boolean enabled, @Param("email")String email);
	
}
