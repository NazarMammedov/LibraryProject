package com.example.library_project.controller;

import com.example.library_project.model.User;
import com.example.library_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }
    @GetMapping("/login/error")
    public String loginError(Model model){
        model.addAttribute("error", "Det har oppstått en feil, brukeren eksisterer ikke, eller så har du skrevet feil e-post eller passord i feltene");
        return "login";
    }
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

   @PostMapping("/register-user")
    public String registration(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, Model model){
        User user = new User();
       if (name.isBlank() || email.isBlank() || password.isBlank()){
           model.addAttribute("error", "Du har tomme felter!");
           return "registration";
       } else if (userService.findByEmail(email) != null) {
           model.addAttribute("error", "E-posten "+email+" blir brukt av en annen bruker!");
           return "registration";
       }else{
           user.setName(name);
           user.setEmail(email);
           user.setPassword(password);
           userService.createUser(user);
           return "redirect:/login";

       }


   }
}
