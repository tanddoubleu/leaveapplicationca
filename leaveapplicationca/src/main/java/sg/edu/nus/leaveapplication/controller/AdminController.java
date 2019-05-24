package sg.edu.nus.leaveapplication.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sg.edu.nus.leaveapplication.model.Credentials;
import sg.edu.nus.leaveapplication.model.Employee;
import sg.edu.nus.leaveapplication.repo.CredentialsRepository;
import sg.edu.nus.leaveapplication.repo.EmployeeRepository;
import sg.edu.nus.leaveapplication.util.SecurityService;
import sg.edu.nus.leaveapplication.util.UserService;
import sg.edu.nus.leaveapplication.util.UserValidator;
@Controller

public class AdminController {
	
	private  EmployeeRepository employeeRepo;
	private CredentialsRepository credRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;
	
	@Autowired
	public void setCredRepo(CredentialsRepository credRepo) {
		this.credRepo = credRepo;
	}



	@Autowired
	public void setEmployeeRepo(EmployeeRepository employeeRepo) {
		this.employeeRepo = employeeRepo;
	}
	
    @GetMapping("/adduser")
    public String registration(Model model) {
        model.addAttribute("userForm", new Credentials());
	    List<Employee> u = employeeRepo.findManagers(); 
		model.addAttribute("managers", u);
        return "registration";
    }

    @PostMapping("/adduser")
    public String addRegistration(@ModelAttribute("userForm") Credentials userForm, BindingResult bindingResult) {
    	userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);
        return "redirect:/adminhome";
    }
	
//	@GetMapping("/adduser")	
//	public String showadduserform(Model model) {
//		List<Employee> u = employeeRepo.findByRole(); 
//		model.addAttribute("roles", u);
//		model.addAttribute("form", new Employee());
//		return "adduser";
//	}
//	
//	@PostMapping("/adduser")
//	public String addUser(@Valid Credentials user, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            return "adduser";
//        }    
//        credRepo.save(user);   
//        model.addAttribute("users", credRepo.findAll());
//        
//        return "adminhome";
//    }
	
	@RequestMapping(path="/adminhome" ,method = RequestMethod.GET)
	public String listmethod(Model model) {		
		model.addAttribute("users", credRepo.findAll());
		return "adminhome";
	
	}
	
	@GetMapping("/edit{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
	    Credentials user = credRepo.findById(id)
	      .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    List<Employee> u = employeeRepo.findManagers(); 
		model.addAttribute("managers", u);
	    model.addAttribute("user", user);
	    return "edituser";
	}
	
	@PostMapping("/update{id}")
	public String updateUser(@ModelAttribute("updateUser") Credentials updateUser, @PathVariable("id") long id, @Valid Credentials user, 
	  BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        user.setUserId(id);
	        return "edituser";
	    } else {
	    	Credentials oldUser = credRepo.findById(id)
	    			.orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    	oldUser.getEmployee().setCompensationhours(updateUser.getEmployee().getCompensationhours());
	    	oldUser.getEmployee().setContact(updateUser.getEmployee().getContact());
	    	oldUser.getEmployee().setDesignation(updateUser.getEmployee().getDesignation());
	    	oldUser.getEmployee().setEmail(updateUser.getEmployee().getEmail());
	    	oldUser.getEmployee().setLeaveEntitled(updateUser.getEmployee().getLeaveEntitled());
	    	oldUser.getEmployee().setName(updateUser.getEmployee().getName());
	    	oldUser.getEmployee().setReportsTo(updateUser.getEmployee().getReportsTo());    	
	    	credRepo.save(oldUser);
	    }
	    return "redirect:/adminhome";
	}
	
	@RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String deleteProduct(@PathVariable(name = "id") long id) {
		credRepo.delete(credRepo.findById(id).orElse(null));
        return "redirect:/adminhome";
    }
	
	
	
}
