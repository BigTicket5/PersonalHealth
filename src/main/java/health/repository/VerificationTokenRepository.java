package health.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import health.domain.User;
import health.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

	VerificationToken findByToken(String token);
	VerificationToken findByUser(User user);
  
  	//update validation token
	
	@Modifying(clearAutomatically = true)
	@Transactional(propagation=Propagation.REQUIRED)
	@Query("update VerificationToken vt set vt.token = :token, vt.expiryDate =:expirydate where vt.user = :user")
	int updateToken(@Param("token") String token,@Param("expirydate") Date expirydate, @Param("user") User user );
}
