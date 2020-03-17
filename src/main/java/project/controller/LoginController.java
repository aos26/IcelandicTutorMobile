package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.persistence.entities.Users;
import javax.servlet.http.HttpSession;
import project.service.UserService;

@Controller
public class LoginController {

    // Instance Variables
    private UserService userService;

    // Dependency Injection
    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    // Request mapping is the path that you want to map this method to
    // when the project is running and you enter "localhost:8080/login"
    // into a browser, this method is called
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String init(Model model){
        model.addAttribute("msg","Please enter your login details");
        return "login";
    }

    // Method that receives the POST request on the URL /login and receives
    // the ModelAttribute("users"). The attribute is the attribute that is
    // mapped to the form, so here we can get the user information (username, password)
    // that was entered into the form.
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submit(@ModelAttribute("users") Users users, HttpSession session, Model model){
        model.addAttribute("error", session.getAttribute("error"));
        session.removeAttribute("error");

        // Here we fetch the user input from the login form
        String userName = users.getUserName();
        String password = users.getPassword();
        String name = users.getName();
        Users exists = userService.getByUserName(userName);
        System.out.println(userName + " " + password);

        // Check whether the user exists in the database and checks that the
        // input fields are not null
        if (exists != null && userName != null && password != null) {

            // Check whether the entered password matches the one from the database
            if (password.equals(exists.getPassword())) {
                session.setAttribute("login", exists);
                return "redirect:/homepage";
            }
        }

        // Error message displayed if login fails
        model.addAttribute("error", "Invalid Details");
        return "login";
    }

}
