package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.LeaveApplication;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.LeaveRepository;
import sg.edu.nus.leaveapplication.util.SecurityService;
import sg.edu.nus.leaveapplication.util.UserService;
import sg.edu.nus.leaveapplication.util.UserValidator;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;


@Controller
public class LoginController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;
	
	private CredentialsRepository credRepos;
	@Autowired
	public void setCredRepos(CredentialsRepository credRepos) {
		this.credRepos = credRepos;
	}
	private EmployeeRepository employeeRepos;
	@Autowired
	public void setUserRepos(EmployeeRepository employeeRepos) {
		this.employeeRepos = employeeRepos;
	}
	private LeaveRepository leaveRepos;
	@Autowired
	public void setLeaveRepos(LeaveRepository leaveRepos) {
		this.leaveRepos = leaveRepos;
	}	
	
	@GetMapping({"/", "/login"})
	public String login(Model model, String error, String logout) {
		if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}
	
//	@PostMapping("/login") 
//	public String login(@ModelAttribute("cred") Credentials user, Model model) { 
//		Credentials u =credRepos.findByUsername(user.getUsername()); 
//		if(u == null) 
//			return "login";
//		else if(u.getPassword().equals(user.getPassword())) { 
//			//Pass User and Leave by user to view //definitely one UserId(one to one between credentials and user) 
//			Employee loginUser = employeeRepos.findById(u.getUserId()).get();
//			List<LeaveApplication> leaveList = leaveRepos.findByEmployee(loginUser); 
//			model.addAttribute("loginUser", loginUser); 
//			model.addAttribute("leaveList", leaveList); 
//			return "home"; 
//		} 
//		else 
//			return "login";
//	}
	
}
