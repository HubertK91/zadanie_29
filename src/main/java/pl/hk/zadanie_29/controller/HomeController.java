package pl.hk.zadanie_29.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.zadanie_29.model.User;
import pl.hk.zadanie_29.service.UserService;

import java.util.List;

@Controller
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String homepage() {
        return "home";
    }

     @GetMapping("/secure")
    public String secure(Model model) {
         User currentUser = userService.findCurrentUser();
         model.addAttribute("currentUser",currentUser);
         return "secure";
    }
}
