package com.example.batch25.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.batch25.config.JwtTokenUtil;
import com.example.batch25.config.MyUserDetails;
import com.example.batch25.dto.ChangePasswordRequest;
import com.example.batch25.dto.LoginRequest;
import com.example.batch25.dto.LoginResponse;
import com.example.batch25.model.User;
import com.example.batch25.repository.UserRepository;

@Controller
@RequestMapping("account")
@CrossOrigin
public class AccountController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetails userDetailsService;

    @Autowired
	private JwtTokenUtil jwtTokenUtil;

    @GetMapping("login")
    public String loginform(Model model){
        model.addAttribute("loginRequest", new LoginRequest());
        return "account/login";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new LoginResponse(token));
	}
    private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

    // @PostMapping("authenticate")
    // public String login(LoginRequest loginRequest, Model model, HttpSession session){
    //     Authentication authentication = authenticationManager
    //             .authenticate(
    //                     new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    //     SecurityContextHolder.getContext().setAuthentication(authentication);
    //     if (authentication.isAuthenticated()) {
    //         // session.setAttribute("email", loginRequest.getEmail());
    //         return "redirect:changepassword";
    //     }
    //     return "account/login";
    //     // User user = userRepository.login(loginRequest.getEmail(), loginRequest.getPassword());

    //     // if (user != null && loginRequest.getEmail().equals(user.getEmail()) && loginRequest.getPassword().equals(user.getPassword())) {
    //     //     session.setAttribute("email", user.getEmail());
    //     //     return "redirect:changepassword";
    //     // } else {
    //     //     return "account/login";
    //     // }
        
    // }

    @GetMapping("changepassword")
    public String changepasswordform(Model model, HttpSession session){
        // String email = (String) session.getAttribute("email");
        // model.addAttribute("email", email);
        model.addAttribute("changepasswordreq", new ChangePasswordRequest());
        return "account/changepassword";
    }

    @PostMapping("changepassword")
    public String changepassword(@ModelAttribute("email") String email, ChangePasswordRequest changePasswordRequest){
        User user = userRepository.findByEmail(email);
        if(passwordEncoder.matches(changePasswordRequest.getOldPass(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPass()));
            userRepository.save(user);
            return "redirect:/account/login";
        }
        // if(changePasswordRequest.getOldPass().equals(user.getPassword())){
        //     user.setPassword(changePasswordRequest.getNewPass());
        //     userRepository.save(user);
        //     return "redirect:/account/login";
        // }
        return "redirect:/account/login";
    }

}
