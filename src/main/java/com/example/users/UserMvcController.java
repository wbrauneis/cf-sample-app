package com.example.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserMvcController {

    private CrudRepository crudRepository;

    @Autowired
    public UserMvcController(CrudRepository<User, String> crudRepository) {
        this.crudRepository = crudRepository;
    }

    @GetMapping("/users.html")
    String users(Model model) {
        model.addAttribute("users", crudRepository.findAll());
        return "users"; // src/main/resources/templates/ + $X + .html
    }

    @GetMapping("/useredit")
    public String userForm(Model model) {
        model.addAttribute("user", new User());
        return "user-edit";
    }

    @PostMapping("/useredit")
    public String greetingSubmit(@ModelAttribute User user) {
        // Persist to database here
        return "user-result";
    }

}

/*
 * @RestController class UserRestController {
 *
 * @Autowired private UserRepository userRepository;
 *
 * @RequestMapping("/users") Collection<User> users() { return userRepository.findAll(); } }
 */
