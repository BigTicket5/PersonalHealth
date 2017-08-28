package health.controller;
import java.util.Calendar;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import health.domain.User;
import health.domain.VerificationToken;
import health.events.OnRegistrationCompleteEvent;
import health.service.UserService;


@Controller
public class RegistrationController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	ApplicationEventPublisher eventPublisher;
	
	@Autowired
    private MessageSource messages;
	
	@RequestMapping(value="/registration",method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user",user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	/* registration post process
	 * */
	@RequestMapping(value="/registration",method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult,WebRequest request, Errors errors){
		User userExists = userService.findUserByEmail(user.getEmail());
		if(userExists!=null){
			bindingResult.rejectValue("Email","error.user","This email has been used!");
		}
		if(bindingResult.hasErrors())
		{
			return new ModelAndView("registration","user",user);
		}
		else{
			/*save new user*/
			userService.saveUser(user);
		}
		try{
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));
		}
		catch(Exception e){
			return new ModelAndView("emailError", "user", user);
		}
		return new ModelAndView("successRegister","user",user);
	}
	
	@RequestMapping(value={"/home","/health/home"}, method = RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");
		modelAndView.setViewName("home");
		return modelAndView;
	}
	
	@RequestMapping(value = "/regitrationConfirm*", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(WebRequest request, @RequestParam("token") String token) {
	    Locale locale = request.getLocale();
	    ModelAndView model = new ModelAndView();
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addObject("message", message);
	        model.setViewName("/invalidlink/token");
	        return model;
	    }
	    User user = verificationToken.getUser();
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addObject("message", messageValue);
	        model.setViewName("/invalidlink/expired");
	        return model;
	    } 
	    user.setEnabled(true); 
	    userService.enableUser(user); 
	    model.setViewName("login");
	    return model; 
	}

}
