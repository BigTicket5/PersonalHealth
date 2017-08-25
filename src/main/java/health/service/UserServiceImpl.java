package health.service;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import health.domain.Role;
import health.domain.User;
import health.domain.VerificationToken;
import health.repository.RoleRepository;
import health.repository.UserRepository;
import health.repository.VerificationTokenRepository;
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
    private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	@Override
	public User findUserByName(String name) {
		return userRepository.findByFirstName(name);
	}
	
	@Override
	public User findUserByEmail(String email){
		return userRepository.findByEmail(email);
	}
	@Override
	public User getUserbyValiToken(String verificationToken){
		 User user = tokenRepository.findByToken(verificationToken).getUser();
	     return user;
	}
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setIsActive(1);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	public void createVerificationToken(User user, String token) {
		// TODO Auto-generated method stub
		VerificationToken myToken = new VerificationToken(token, user, 0);
        tokenRepository.save(myToken);
		
	}

	@Override
	public VerificationToken getVerificationToken(String verificationToken) {
		// TODO Auto-generated method stub
		return tokenRepository.findByToken(verificationToken);
	}

	@Override
	public int enableUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.enableUser(user.isEnabled(), user.getEmail());
	}

	@Override
	public void updateVerificationToken(User user, String token) {
		// TODO Auto-generated method stub
		VerificationToken myToken = new VerificationToken(token, user, 1);
		if(myToken.getUser()!=null)
		{
			tokenRepository.updateToken(token,myToken.getExpiryDate(),user);
		}
	}

}
