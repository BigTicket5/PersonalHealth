package health.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping(value="/myhealth/myhealthinfo",method=RequestMethod.GET)
	public ModelAndView myhealthinfo(){
		ModelAndView modelandview = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentEmail = authentication.getName();
		modelandview.setViewName("/myhealth/myhealthinfo");
		return  modelandview;
	}
}
