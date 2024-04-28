package edu.miu.cs489.tsogt.backend.controller;

import edu.miu.cs489.tsogt.backend.dto.user.JwtResponce;
import edu.miu.cs489.tsogt.backend.dto.user.UserAuthRequest;
import edu.miu.cs489.tsogt.backend.dto.user.UserRequest;
import edu.miu.cs489.tsogt.backend.exception.user.NonDuplicateCheckRegister;
import edu.miu.cs489.tsogt.backend.service.UserService;
import edu.miu.cs489.tsogt.backend.utils.service.JWTManagementUtilityService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private JWTManagementUtilityService jwtService;
    private AuthenticationManager authManager;
    private UserService userService;

    public UserController(JWTManagementUtilityService jwtService,
                          AuthenticationManager authManager,
                          UserService userService) {
        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userService = userService;
    }

    @PostMapping(value = "/project/api/v1/service/public/login")
    public String authenticateUser(@Valid @RequestBody UserAuthRequest request) throws Exception {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        return jwtService.generateToken(request.getUsername());
    }

    @PostMapping(value = "/project/api/v1/service/public/register")
    public ResponseEntity<JwtResponce> registerUser(@Valid @RequestBody UserRequest request) throws NonDuplicateCheckRegister {
        return ResponseEntity.ok(userService.addUser(request));
    }
}
