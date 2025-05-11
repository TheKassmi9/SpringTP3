package com.abde.tp3.Enums;

import lombok.Getter;
import lombok.Setter;
@Getter
public enum Roles {
  USER("ROLE_USER"),
  ADMIN("ROLE_ADMIN"),
  DOCUMENT_UPLOADER("ROLE_DOCUMENT_UPLOADER");
  private final String role;
   Roles(String role){
    this.role = role;
  }
}
