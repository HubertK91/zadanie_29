package pl.hk.zadanie_29.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.hk.zadanie_29.model.User;
import pl.hk.zadanie_29.model.UserRole;
import pl.hk.zadanie_29.service.UserService;

import java.util.*;


@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String adminPanel(Model model){
        List<User> users = userService.findAllWithoutCurrentUser();
        model.addAttribute("users", users);
        Set<UserRole> userRole = null;
        for (User user : users) {
            userRole = user.getRoles();
        }
        model.addAttribute("roles", userRole);
        return "admin";
    }

    @PostMapping("/updateRole/{id}")
    public String updateRole(@PathVariable Long id, Model model){
        User user = userService.findUserById(id);
        Set<UserRole> roles = user.getRoles();
        List<UserRole> list = new ArrayList<>(roles);
        UserRole userRole = null;
        for (UserRole role : list) {
            userRole = role;
        }
        model.addAttribute("roles", userRole);
        model.addAttribute("user", user);
        return "roleEdit";
    }

    @PostMapping("/updateRole")
    public String updateRole(User user){
        userService.updateUserRole(user);
        return "redirect:/admin";
    }
}
