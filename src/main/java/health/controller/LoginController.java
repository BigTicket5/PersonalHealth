package health.controller;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import health.domain.User;
import health.domain.VerificationToken;
import health.events.OnPasswordResetSubmittion;
import health.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private MessageSource messages;
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request){
		ModelAndView modelandview = new ModelAndView();
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if (inputFlashMap != null) {
			String useremail = (String) inputFlashMap.get("email");
			modelandview.addObject("useremail", useremail);
		}
		modelandview.setViewName("login");
		return modelandview;
	}
	
	@RequestMapping(value="/resetpassword/reset",method=RequestMethod.GET)
	public ModelAndView forgetPassword(){	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/resetpassword/reset");
		return modelAndView;
	}
	
	@RequestMapping(value="/resetpassword/reset",method=RequestMethod.POST)
	public String resetPassword(Model model,@RequestParam("email")String email,WebRequest request,Error errors ){	
		User user = userService.findUserByEmail(email);
		if(user==null){
			model.addAttribute("error", "This email address is not valid!");
			return "/resetpassword/reset";
		}
		try{
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnPasswordResetSubmittion(user, request.getLocale(), appUrl));
		}
		catch(Exception e){
			model.addAttribute("error", e);
			return "/resetpassword/reset";
		}
		return "redirect:/resetpassword/confirm?email="+email;
	}
	
	@RequestMapping(value="/resetpassword/confirm*",method=RequestMethod.GET)
	public ModelAndView resetconfirm(@RequestParam("email") String email){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("email", email);
		modelAndView.setViewName("/resetpassword/confirm");
		return modelAndView;
	}
	
	@RequestMapping(value = "/resetpassword/update*", method = RequestMethod.GET)
	public ModelAndView passwordReset(WebRequest request,@RequestParam("token") String token) {
	    Locale locale = request.getLocale();
	    ModelAndView model = new ModelAndView(); 
	    VerificationToken verificationToken = userService.getVerificationToken(token);
	    if (verificationToken == null) {
	        String message = messages.getMessage("auth.message.invalidToken", null, locale);
	        model.addObject("message", message);
	        model.setViewName("/invalidlink/token");
	        return model;
	    }
	    Calendar cal = Calendar.getInstance();
	    if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
	        String messageValue = messages.getMessage("auth.message.expired", null, locale);
	        model.addObject("message", messageValue);
	        model.setViewName("/invalidlink/expired");
	        return model;
	    } 
	    model.addObject("token", token);
	    model.setViewName("/resetpassword/update");
	    return model;
	}
	
	@RequestMapping(value="/resetpassword/update",method=RequestMethod.POST)
	public RedirectView  updatepassword(@RequestParam Map<String,String>requestParams,
			RedirectAttributes  redirectAttributes){
		String token = requestParams.get("token");
		User user = userService.getVerificationToken(token).getUser();
		if(user!=null){
			user.setPassword(requestParams.get("password"));
			userService.saveUser(user);
			userService.disableVerificationToken(user, token);
		}
		redirectAttributes.addFlashAttribute("email", user.getEmail());
		RedirectView redirectView = new RedirectView();
		redirectView.setContextRelative(true);
		redirectView.setUrl("/login");
	    return redirectView;
	}
}



