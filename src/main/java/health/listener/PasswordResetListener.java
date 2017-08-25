package health.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import health.domain.User;
import health.events.OnPasswordResetSubmittion;
import health.events.OnRegistrationCompleteEvent;
import health.service.UserService;

@Component
public class PasswordResetListener implements ApplicationListener<OnPasswordResetSubmittion>{
	@Autowired
    private UserService userService;
  
    @Autowired
    private MessageSource messages;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
    public void onApplicationEvent(OnPasswordResetSubmittion event) {
        this.PasswordReset(event);
    }
    /* Sending Password reset request email to user's email which he/she filled.
     * */
	private void PasswordReset(OnPasswordResetSubmittion event) {
		// TODO Auto-generated method stub
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		
		userService.updateVerificationToken(user, token);
		
		String recipientAddress = user.getEmail();
        String subject = "Password Reset Confirmation";
        String resetUrl 
          = event.getAppUrl() + "/passwordResetConfirm?token=" + token;
        resetUrl += "&email=" + recipientAddress;
        
        String message = messages.getMessage("message.resetpassd", null, event.getLocale());
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "http://localhost:8080" + resetUrl);
        mailSender.send(email);
		
	}

}
