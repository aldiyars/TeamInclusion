package kz.teamInclusion.Inclusion.controllers;

import kz.teamInclusion.Inclusion.model.Roles;
import kz.teamInclusion.Inclusion.model.Users;
import kz.teamInclusion.Inclusion.repository.RoleRepository;
import kz.teamInclusion.Inclusion.repository.UserRepository;
import kz.teamInclusion.Inclusion.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @GetMapping(path = "/login")
    public String enter(Model model){
        return "login";
    }

    @GetMapping(path = "/register")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addUser(Model model){

        List<Users> users = userRepository.findAll();
        model.addAttribute("users", users);
        List<Roles> role = roleRepository.findAll();
        model.addAttribute("roles", role);
        return "register";
    }

    @PostMapping(path = "/register")
    //@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String addUser(Model model,
                          @RequestParam(name = "firstName") String firstName,
                          @RequestParam(name = "lastName") String lastName,
                          @RequestParam(name = "surName") String surName,
                          @RequestParam(name = "iin") Long iin,
                          @RequestParam(name = "tel") Long tel,
                          @RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password,
                          @RequestParam(name = "role") int role){

        String redirect = "redirect:/register?error";
        Users user = userRepository.findByEmail(email);

        Roles roles = roleRepository.getOne((long) role);
        if(user==null){
            user = new Users(null, firstName, lastName, surName, iin, tel, email, password, roles);
            userService.registerUser(user);
            redirect = "redirect:/register?success";
        }
        return redirect;
    }

}
