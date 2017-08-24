package health.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import health.domain.User;
import health.domain.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);
}
