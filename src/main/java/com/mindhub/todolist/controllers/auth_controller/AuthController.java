package com.mindhub.todolist.controllers.auth_controller;

import com.mindhub.todolist.config.JwtUtils;
import com.mindhub.todolist.dtos.LoginUser;
import com.mindhub.todolist.dtos.NewUser;
import com.mindhub.todolist.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtil;

    @Autowired
    private UserServices userServices;

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginUser loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody NewUser newUser) {
        if(newUser.email().isBlank() || newUser.password().isBlank() || newUser.name().isBlank()) {
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        userServices.createNewUser(newUser);
        return new ResponseEntity<>("Se creo la persona", HttpStatus.CREATED);
    }

}
