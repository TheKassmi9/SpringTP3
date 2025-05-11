package com.abde.tp3.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateDTO {
  @NotBlank
  private String password;
  @NotBlank
  @Size(min = 6)
  private String newPassword;
  @NotBlank
  @Size(min = 6)
  private String confirmPassword;
  @NotBlank
  private String username;
}
