package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.persistence.entities.Users;
import project.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ScoreController {

    private UserService userService;

    // Dependency Injection
    @Autowired
    public ScoreController(UserService userService) {

        this.userService = userService;
    }

    // Request mapping to "localhost:8080/scoreboard"
    // user must be logged in to access the page, otherwise they are
    // redirected to the login page
    @RequestMapping(value = "/scoreboard", method = RequestMethod.GET)
    public String scoreboard(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            model.addAttribute("name", loggedInUser.getUserName());
            model.addAttribute("score", loggedInUser.getScore());
            List<Users> users = userService.getScoreOrder();

            model.addAttribute("name1", users.get(0).getUserName());
            model.addAttribute("score1", users.get(0).getScore());
            model.addAttribute("name2", users.get(1).getUserName());
            model.addAttribute("score2", users.get(1).getScore());
            model.addAttribute("name3", users.get(2).getUserName());
            model.addAttribute("score3", users.get(2).getScore());
            int userscore = loggedInUser.getScore();
            String username = loggedInUser.getUserName();
            int count = 0;
            int place = 0;
            while(count<users.size()){
                if(username.equals(users.get(count).getUserName() ) && userscore==users.get(count).getScore()){
                    place = count + 1;
                    model.addAttribute("place",place);
                    count = users.size();
                }
                count +=1;
            }
            //model.addAttribute("answer", question.get(index).getAnswer());
            //model.addAttribute("wrongAnswer1", question.get(index).getWrongAnswer1());
            //model.addAttribute("wrongAnswer2", question.get(index).getWrongAnswer2());
            return "scoreboard";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }
}
