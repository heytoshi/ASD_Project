package edu.miu.cs489.tsogt.backend.dto.user;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String username,
        String phoneNumber
) {
}
