package pl.hk.zadanie_29.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.hk.zadanie_29.model.User;
import pl.hk.zadanie_29.model.UserRole;
import pl.hk.zadanie_29.service.UserService;

import java.util.List;
import java.util.Set;


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
        for (int i = 0; i < users.size(); i++) {
            userRole = users.get(i).getRoles();
        }
        model.addAttribute("roles", userRole);
        return "admin";
    }

    @GetMapping("/addRoleAdmin")
    public String addRoleAdmin(@RequestParam Long id){
        userService.setRoleUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/deleteRoleAdmin")
    public String deleteRoleAdmin(@RequestParam Long id){
        userService.setRoleUserById(id);
        return "redirect:/admin";
    }
}
