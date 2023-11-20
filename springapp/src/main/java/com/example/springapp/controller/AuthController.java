package com.example.springapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springapp.model.User;
import com.example.springapp.security.JwtUtil;
import com.example.springapp.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	private UserService userService;

	@PostMapping("/register")
    public boolean handler1(@RequestBody User user){
        return userService.registerUser(user);
    }


    @PostMapping("/login")
    public ResponseEntity<String> handler2(@RequestBody User user)throws Exception{

        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("bad credentials");
        }

        UserDetails userDetails=this.userService.loadUserByUsername(user.getEmail());
        String token=jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    
}
