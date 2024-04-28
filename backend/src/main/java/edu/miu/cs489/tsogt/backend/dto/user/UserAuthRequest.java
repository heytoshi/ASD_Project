package edu.miu.cs489.tsogt.backend.dto.user;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
    @NotBlank(message = "Username cannot be null, empty or blank space(s)")
    private String username;
    @NotBlank(message = "Password cannot be null, empty or blank space(s)")
    private String password;
}
