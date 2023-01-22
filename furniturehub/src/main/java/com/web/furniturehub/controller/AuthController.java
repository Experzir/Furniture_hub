package com.web.furniturehub.controller;

import java.security.Principal;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.furniturehub.dto.UserDto;
import com.web.furniturehub.model.Category;
import com.web.furniturehub.model.Ftype;
import com.web.furniturehub.model.Furniture;
import com.web.furniturehub.model.Style;
import com.web.furniturehub.model.User;
import com.web.furniturehub.repository.FtypeRepository;
import com.web.furniturehub.repository.FurnitureRepository;
import com.web.furniturehub.repository.StyleRepository;
import com.web.furniturehub.repository.UserRepository;
import com.web.furniturehub.service.UserService;

@Controller
public class AuthController {
    private UserRepository userRepository;
    private UserService userService;
    private FurnitureRepository furnitureRepository;
    private StyleRepository styleRepository;
    private FtypeRepository ftRepository;

    public AuthController(UserRepository userRepository, UserService userService,
            FurnitureRepository furnitureRepository, StyleRepository styleRepository, FtypeRepository ftRepository) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.furnitureRepository = furnitureRepository;
        this.styleRepository = styleRepository;
        this.ftRepository = ftRepository;
    }

    // handler method to handle home page request
    @GetMapping({ "/", "/index" })
    public String home() {
        return "index";
    }

    // handler method to handle user registration form request
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // create model object to store form data
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    // handler method to handle user registration form submit request
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
            BindingResult result,
            Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    // handler method to handle login request
    // USER
    @GetMapping("/user")
    public String getAllUsers(Model model, HttpServletRequest request) {

        List<Furniture> fList = furnitureRepository.findAll();
        List<Ftype> tList = ftRepository.findAll();
        List<Style> sList = styleRepository.findAll();

        User user = findUser(request);
        List<Category> category = user.getCategorys();

        model.addAttribute("fList", fList);
        model.addAttribute("tList", tList);
        model.addAttribute("sList", sList);
        model.addAttribute("category", category);
        return "home";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    private User findUser(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userRepository.findByEmail(principal.getName());
        return user;
    }

}
