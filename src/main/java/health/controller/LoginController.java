package health.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import health.domain.User;
import health.events.OnPasswordResetSubmittion;
import health.service.UserService;


@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public ModelAndView login(){	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	@RequestMapping(value="/resetpassword",method=RequestMethod.GET)
	public ModelAndView forgetPassword(){	
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("resetpassword");
		return modelAndView;
	}
	@RequestMapping(value="/resetpassword",method=RequestMethod.POST)
	public String resetPassword(Model model,@RequestParam("email")String email,WebRequest request,Error errors ){	
		User user = userService.findUserByEmail(email);
		if(user==null){
			model.addAttribute("error", "This email address is not valid!");
			return "/resetpassword";
		}
		try{
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new OnPasswordResetSubmittion(user, request.getLocale(), appUrl));
		}
		catch(Exception e){
			model.addAttribute("error", e);
			return "/resetpassword";
		}
		return "/resetconfirm";
	}
}



