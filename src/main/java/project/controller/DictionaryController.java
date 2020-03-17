package project.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.persistence.entities.Category;
import project.persistence.entities.Question;
import project.persistence.entities.Users;
import project.persistence.repositories.CategoryRepository;
import project.persistence.repositories.QuestionRepository;
import project.service.CategoryService;
import project.service.QuestionService;
import project.service.UserService;

import javax.servlet.http.HttpSession;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DictionaryController {
    private CategoryService categoryService;
    private UserService userService;
    private QuestionService questionService;

    // Dependency Injection
    @Autowired
    public DictionaryController(UserService userService, CategoryService categoryService, QuestionService questionService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.questionService = questionService;
    }


    // Request mapping to "localhost:8080/dictionary"
    // user must be logged in to access the page, otherwise they are
    // redirected to the login page
    @RequestMapping(value = "/dictionary", method = RequestMethod.GET)
    public String dictionary(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            return "dictionary";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

    // Method that redirects to a category
    @RequestMapping(value = "/dictionary/{cat_id}/{lvl_id}", method = RequestMethod.GET)
    public String categoryGetCategory(@PathVariable("cat_id") Long cat_id, @PathVariable("lvl_id") Long lvl_id, HttpSession session, Model model){
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            List<Question> question = questionService.getAllQuestionByCat(cat_id,lvl_id);
            int teljari = 0;
            int lengd = question.size();
            List<String> isl = new ArrayList<String>();
            List<String> ensk = new ArrayList<String>();
            while(teljari<lengd){
                isl.add(question.get(teljari).getQuestionWord());
                ensk.add(question.get(teljari).getAnswer());
                teljari += 1;
            }
            model.addAttribute("isl",isl);
            model.addAttribute("ensk",ensk);

            return "translations";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

}

