package com.chat.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchUsersDto {
    @NotBlank @Size(min = 1)
    private String username;
}
