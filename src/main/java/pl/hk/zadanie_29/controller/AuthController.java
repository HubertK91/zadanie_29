package pl.hk.zadanie_29.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.zadanie_29.model.User;
import pl.hk.zadanie_29.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            Model model) {
        boolean showErrorMessage = error != null;
        model.addAttribute("showErrorMessage", showErrorMessage);
        return "loginForm";
    }

    @GetMapping("/registration")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "registrationForm";
    }

    @PostMapping("/registration")
    public String register(User user) {
        String username = user.getUsername();
        String rawPassword = user.getPassword();
        userService.registerUser(username, rawPassword);
        return "redirect:/loginForm";
    }

    @PostMapping("/updateDataUser")
    public String editDataUser(User user) {
        userService.updateUserData(user);
        return "redirect:/login";
    }
}
