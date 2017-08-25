package health.service;

import health.domain.User;
import health.domain.VerificationToken;
public interface UserService {
	public User findUserByName(String name);
	public void saveUser(User user);
	public User findUserByEmail(String email);
	public User getUserbyValiToken(String verificationToken);
	public void createVerificationToken(User user, String token);
	public void updateVerificationToken(User user, String token);
	public VerificationToken getVerificationToken(String VerificationToken);
	public int enableUser(User user);
}
