package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.persistence.entities.Users;
import project.service.UserService;


@Controller
public class RegisterController {

    // Instance Variables
    private UserService userService;

    // Dependency injection
    @Autowired
    public RegisterController(UserService userService){
        this.userService = userService;
    }

    // Request mapping is the path that you want to map this method to
    // when the project is running and you enter "localhost:8080/register"
    // into a browser, this method is called
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createUser(Model model) {
        model.addAttribute("msg", "Please Enter Your Information");
        model.addAttribute("createUser", new Users());
        return "register";
    }

    // Method that receives the POST request on the URL /register and receives
    // the ModelAttribute("createUser"). The attribute is the attribute that is
    // mapped to the form, so here we can get the user information
    // that was entered into the registration form.
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUserPost(@ModelAttribute("createUser") Users users, Model model) {
        Users exists = this.userService.getByUserName(users.getName());
        if(exists != null){
            model.addAttribute("error","User already exists");
            return "/register";
        }

        // Make sure fields are not empty
        if(users.getPassword().equals("") || users.getUserName().equals("") || users.getName().equals("")) {
            model.addAttribute("error", "Fill in all sections");
            return "/register";
        }
        users.setPassword(users.getPassword());     //TODO hash password
        userService.save(users);

        model.addAttribute("createUser", new Users());
        return "redirect:login";
    }
}