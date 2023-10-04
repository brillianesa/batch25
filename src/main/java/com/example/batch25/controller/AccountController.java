package com.example.batch25.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.batch25.dto.ChangePasswordRequest;
import com.example.batch25.dto.LoginRequest;
import com.example.batch25.model.User;
import com.example.batch25.repository.UserRepository;

@Controller
@RequestMapping("account")
public class AccountController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("login")
    public String loginform(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "account/login";
    }

    @PostMapping("authenticate")
    public String login(LoginRequest loginRequest, Model model, HttpSession session){
        User user = userRepository.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user != null && loginRequest.getEmail().equals(user.getEmail()) && loginRequest.getPassword().equals(user.getPassword())) {
            session.setAttribute("email", user.getEmail());
            return "redirect:changepassword";
        } else {
            return "account/login";
        }
        
    }

    @GetMapping("changepassword")
    public String changepasswordform(Model model, HttpSession session){
        String email = (String) session.getAttribute("email");
        model.addAttribute("email", email);
        model.addAttribute("changepasswordreq", new ChangePasswordRequest());
        return "account/changepassword";
    }

    @PostMapping("changepassword")
    public String changepassword(@ModelAttribute("email") String email, ChangePasswordRequest changePasswordRequest){
        User user = userRepository.findByEmail(email);
        if(changePasswordRequest.getOldPass().equals(user.getPassword())){
            user.setPassword(changePasswordRequest.getNewPass());
            userRepository.save(user);
            return "redirect:/account/login";
        }
        return "redirect:/account/login";
    }

}