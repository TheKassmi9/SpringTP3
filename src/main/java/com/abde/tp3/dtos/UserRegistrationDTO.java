package com.abde.tp3.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegistrationDTO {
  private String username, password, email;
}
