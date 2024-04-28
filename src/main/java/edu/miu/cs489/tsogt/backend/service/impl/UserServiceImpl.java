package edu.miu.cs489.tsogt.backend.service.impl;

import edu.miu.cs489.tsogt.backend.dto.user.JwtResponce;
import edu.miu.cs489.tsogt.backend.dto.user.UserRequest;
import edu.miu.cs489.tsogt.backend.exception.user.NonDuplicateCheckRegister;
import edu.miu.cs489.tsogt.backend.model.User;
import edu.miu.cs489.tsogt.backend.repository.RoleRepo;
import edu.miu.cs489.tsogt.backend.repository.UserRepo;
import edu.miu.cs489.tsogt.backend.service.UserService;
import edu.miu.cs489.tsogt.backend.utils.service.JWTManagementUtilityService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTManagementUtilityService jwtManagementUtilityService;
    private final RoleRepo roleRepo;

    public UserServiceImpl(UserRepo userRepo, PasswordEncoder passwordEncoder, JWTManagementUtilityService jwtManagementUtilityService, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtManagementUtilityService = jwtManagementUtilityService;
        this.roleRepo = roleRepo;
    }

    @Override
    public JwtResponce addUser(UserRequest user) throws NonDuplicateCheckRegister {
        var findUser = userRepo.findByUsername(user.username());
        var findEmail = userRepo.findByEmail(user.email());
        var findPhone = userRepo.findByPhoneNumber(user.phoneNumber());

        if(findUser.isPresent()) {
            throw new NonDuplicateCheckRegister("Username already exists");
        }
        if(findEmail.isPresent()){
            throw new NonDuplicateCheckRegister("Email already exists");
        }
        if(findPhone.isPresent()) {
            throw new NonDuplicateCheckRegister("Phone Number already exists");
        }

        User newUser = new User(
                user.firstName(),
                user.lastName(),
                user.email(),
                passwordEncoder.encode(user.password()),
                user.phoneNumber(),
                user.username(),
                true,
                true,
                true,
                true
         );
        newUser.setRoles(Collections.singletonList(roleRepo.findById(2).orElseThrow()));
        userRepo.save(newUser);
        JwtResponce responce = new JwtResponce(jwtManagementUtilityService.generateToken(newUser.getUsername()));
        return responce;
    }
}
