package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.*;
import project.persistence.entities.Category;
import project.persistence.entities.Level;
import project.persistence.entities.Users;
import project.persistence.entities.Question;
import project.service.QuestionService;
import project.service.UserService;
import project.service.CategoryService;
import project.service.LevelService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {
    private UserService userService;
    private QuestionService questionService;
    private CategoryService categoryService;
    private LevelService levelService;

    @Autowired
    public AdminController(UserService userService, QuestionService questionService,
                           CategoryService categoryService, LevelService levelService) {
        this.userService = userService;
        this.questionService = questionService;
        this.levelService = levelService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            List<Category> categories = categoryService.findAll();
            List<Level> levels = levelService.findAll();
            model.addAttribute("categoryList", categories);
            model.addAttribute("levelList", levels);
            model.addAttribute("msg", loggedInUser.getName());
            model.addAttribute("addQuestion", new Question());
            return "admin";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public String addQuestionQ(@ModelAttribute("addQuestion") Question question, HttpSession session, Model model) {
        Users loggedInUser = (Users)session.getAttribute("login");
        if(loggedInUser != null) {
            questionService.save(question);
            model.addAttribute("addQuestion", new Question());

            return "admin";
        }
        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

}
