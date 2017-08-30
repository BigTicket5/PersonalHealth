package health.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import health.domain.HealthRecord;
import health.domain.User;
import health.service.HealthRecordService;
import health.service.UserService;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HealthRecordService recordService;
	
	@RequestMapping(value="/myhealth/myhealthinfo",method=RequestMethod.GET)
	public ModelAndView myhealthinfo(){
		ModelAndView modelandview = new ModelAndView();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentEmail = authentication.getName();
		User user = userService.findUserByEmail(currentEmail);
		List<HealthRecord> healthlist = recordService.findByUser(user);
		modelandview.addObject("username", user.getFirstName());
		modelandview.addObject("recordlist", healthlist);
		modelandview.setViewName("/myhealth/myhealthinfo");
		return  modelandview;
	}
}
