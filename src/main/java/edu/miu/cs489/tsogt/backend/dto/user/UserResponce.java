package edu.miu.cs489.tsogt.backend.dto.user;

public record UserResponce(
    String firstName,
    String lastName,
    String email,
    String phoneNumber,
    String username
) {
}
