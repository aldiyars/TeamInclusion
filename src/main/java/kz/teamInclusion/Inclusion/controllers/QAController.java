package kz.teamInclusion.Inclusion.controllers;

import kz.teamInclusion.Inclusion.model.QACategory;
import kz.teamInclusion.Inclusion.model.Questions;
import kz.teamInclusion.Inclusion.model.Users;
import kz.teamInclusion.Inclusion.repository.AnswersRepository;
import kz.teamInclusion.Inclusion.repository.QACategoryRepository;
import kz.teamInclusion.Inclusion.repository.QuestionsRepository;
import kz.teamInclusion.Inclusion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
public class QAController {

    @Autowired
    AnswersRepository answersRepository;
    @Autowired
    QuestionsRepository questionsRepository;
    @Autowired
    QACategoryRepository qaCategoryRepository;
    @Autowired
    UserRepository userRepository;



    //Create new QA category
    @PostMapping(path = "/createqacategory")
    private String createQACategory(@RequestParam String name){
        QACategory qaCategory = qaCategoryRepository.getByName(name);
        String redirect = "redirect:/ask?Error";
        if (qaCategory==null){
            QACategory qaCategoryNew = new QACategory(null,name);
            qaCategoryRepository.save(qaCategoryNew);
            redirect = "redirect:/questions?Success";
        }
        return redirect;
    }

    //Home Page QA
    @GetMapping(path = "/questions")
    @PreAuthorize("isAuthenticated()")
    public String questions(Model model){
        model.addAttribute("allQuestions", questionsRepository.findAll());
        return "questions";
    }

    public Users thisUserData(){
        Users userData = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            userData = userRepository.findByEmail(secUser.getUsername());
        }
        return userData;
    }

    //ask
    @GetMapping(path = "/ask")
    @PreAuthorize("isAuthenticated()")
    public String ask(Model model) {
        model.addAttribute("qacategories", qaCategoryRepository.findAll());
        Users users = new Users();
        users = userRepository.findByEmail(thisUserData().getEmail());
        model.addAttribute("thisuserid",users);
        return "ask";
    }

    //Create new Question
    @PostMapping(path = "createQuestion")
    public String createQuestion(@RequestParam Users user, @RequestParam String title,
                                 @RequestParam String desc, @RequestParam Long qacategory){

        QACategory qaCategory = qaCategoryRepository.getOne(qacategory);
        Date date = new Date();
        Questions questions = new Questions(null, user, title, desc, qaCategory ,date);
        questionsRepository.save(questions);
        return "redirect:/questions?CreatedQuestionSuccess";
    }

    //Single Question
    @GetMapping(path = "/question/{id}")
    public String oneAsk(@PathVariable Long id, Model model){
        model.addAttribute("singlequestion", questionsRepository.getOne(id));
//        int quantity = answersRepository.getByQuestion(id).size();
//        model.addAttribute("answersbyid", quantity);
        return "singleqa";
    }

}
