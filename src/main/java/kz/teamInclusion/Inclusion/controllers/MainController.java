package kz.teamInclusion.Inclusion.controllers;

import kz.teamInclusion.Inclusion.model.Users;
import kz.teamInclusion.Inclusion.repository.CategoryRepositoty;
import kz.teamInclusion.Inclusion.repository.NewsRepositoty;
import kz.teamInclusion.Inclusion.repository.RoleRepository;
import kz.teamInclusion.Inclusion.repository.UserRepository;
import kz.teamInclusion.Inclusion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CategoryRepositoty categoryRepositoty;
    @Autowired
    NewsRepositoty newsRepositoty;
    @Autowired
    UserService userService;

    @GetMapping(path = "/")
    //@PreAuthorize("isAuthenticated()")
    public String getAllUsers(Model model){
        List<Users> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "index";
    }

    @GetMapping(path = "/login")
    public String enter(Model model){
        return "login";
    }

}
