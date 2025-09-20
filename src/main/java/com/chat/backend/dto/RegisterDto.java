package com.chat.backend.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter
public class RegisterDto {
    @NotBlank(message = "Please enter a username")
    @Size(min = 4, max = 25)
    private String username;
    @NotBlank(message = "Please enter a valid email")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotBlank(message = "Please enter your password")
    @Size(min = 7, max = 30, message = "Password must be between 7 and 30 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!#%*?&])[A-Za-z0-9@$!#%*?&]+$",
            message = "Password must contain at least one uppercase letter, one number, and one symbol"
    )
    private String password;
}
