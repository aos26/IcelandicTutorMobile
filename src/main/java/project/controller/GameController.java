package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import project.persistence.entities.Level;
import project.persistence.entities.Question;
import project.persistence.entities.Users;
import project.persistence.entities.Category;
import project.service.QuestionService;
import project.service.UserService;
import project.service.LevelService;
import project.service.CategoryService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class GameController {
    private UserService userService;
    private LevelService levelService;
    private CategoryService categoryService;
    private QuestionService questionService;
    private Integer score;
    private Integer questionNR;
    private Integer new_score = 0;
    private Long current_lvl_id;
    private Long current_cat_id;

    @Autowired
    public GameController(UserService userService, LevelService levelService, CategoryService categoryService, QuestionService questionService)
    {
        this.userService = userService;
        this.levelService = levelService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.score = 0;
        this.questionNR = 0;
    }

    // Go to game
    @RequestMapping(value = "/game/{cat_id}/{lvl_id}", method = RequestMethod.GET)
    public String game(@PathVariable("cat_id") Long cat_id, @PathVariable("lvl_id") Long lvl_id, HttpSession session, Model model){
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            System.out.println(current_cat_id + " lvl: " + current_lvl_id);
            if(current_lvl_id != lvl_id || current_cat_id != cat_id){
                this.score = 0;
                this.questionNR = 0;
            }
            model.addAttribute("currScore", this.score);
            model.addAttribute("questionNR", this.questionNR);
            List<Question> question = questionService.getQuestionByCatIdAndLvlId(cat_id, lvl_id);

            int lengd = question.size();
            int index = (int)(Math.random()*lengd);
            int randomOrder = (int)(Math.random()*3);
            if(lvl_id == 3){
                model.addAttribute("questionImg", question.get(index).getQuestion_image());
            }
            else {
                model.addAttribute("question", question.get(index).getQuestionWord());
            }
            model.addAttribute("lvl_id", lvl_id);
            model.addAttribute("order", randomOrder);
            model.addAttribute("answer", question.get(index).getAnswer());
            model.addAttribute("wrongAnswer1", question.get(index).getWrongAnswer1());
            model.addAttribute("wrongAnswer2", question.get(index).getWrongAnswer2());

            return "game";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

    @RequestMapping(value = "/game/{cat_id}/{lvl_id}", method = RequestMethod.POST)
    public String getAnswerPost(@PathVariable("cat_id") Long cat_id, @PathVariable("lvl_id") Long lvl_id, HttpSession session, Model model, HttpServletRequest request){
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            
            if (request.getParameter("answer") != null) {

                model.addAttribute("svarMsg", "Your previous answer was correct!");

                this.score += 1;
                //System.out.println("rett svar! " + this.score);


            }
            else {
                //System.out.println("Rangt svar");

                model.addAttribute("svarMsg", "Your previous answer was incorrect!");

            }
            this.questionNR += 1;
            if(score == 10){
                //Reikna score nota loggedInUser
                Integer currScore = loggedInUser.getScore();
                int incorrect = questionNR - score;
                //int new_score = (score*10)-(incorrect*5);
                this.new_score = (score*10)-(incorrect*5);
                if (this.new_score < 0) {
                    this.new_score = 0;
                }
                loggedInUser.setScore(new_score + currScore);
                userService.save(loggedInUser);

                return "redirect:/gamecomplete";
            }
            current_cat_id = cat_id;
            current_lvl_id = lvl_id;
            game(cat_id, lvl_id, session, model);


            return "game";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }

    // Request mapping to "localhost:8080/gamecomplete"
    // user must be logged in to access the page, otherwise they are
    // redirected to the login page
    @RequestMapping(value = "/gamecomplete", method = RequestMethod.GET)
    public String gameComplete(HttpSession session, Model model) {
        Users loggedInUser = (Users) session.getAttribute("login");
        if (loggedInUser != null) {
            model.addAttribute("msg", loggedInUser.getName());
            model.addAttribute("gameScore", this.new_score);
            model.addAttribute("questionsAnswered", this.questionNR);
            model.addAttribute("rightQuestions", this.score);

            this.score=0;
            this.questionNR=0;
            return "gamecomplete";
        }

        session.setAttribute("error", "User must be logged in!");
        return "redirect:/login";
    }
}
