package edu.miu.cs489.tsogt.backend.service;

import edu.miu.cs489.tsogt.backend.dto.user.JwtResponce;
import edu.miu.cs489.tsogt.backend.dto.user.UserRequest;
import edu.miu.cs489.tsogt.backend.exception.user.NonDuplicateCheckRegister;

public interface UserService {
    JwtResponce addUser(UserRequest user) throws NonDuplicateCheckRegister;
}
