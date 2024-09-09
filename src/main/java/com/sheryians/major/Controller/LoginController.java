package com.sheryians.major.Controller;

import com.sheryians.major.Model.Role;
import com.sheryians.major.Model.User;
import com.sheryians.major.repository.RoleRepository;
import com.sheryians.major.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerGet(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request, Model model) throws ServletException {
        if (bindingResult.hasErrors()) {
            // Log validation errors to the console
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "register";
        }

        String password = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(password));

        List<Role> roles = new ArrayList<>();
        Optional<Role> roleOptional = roleRepository.findById(2);
        if (roleOptional.isPresent()) {
            roles.add(roleOptional.get());
        } else {
            model.addAttribute("errorMessage", "Role not found.");
            return "register";
        }

        try {
            userRepository.save(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "An error occurred while saving the user.");
            return "register";
        }

        // Temporarily remove this line for debugging
        // request.login(user.getEmail(), password);

        return "redirect:/";
    }

}
