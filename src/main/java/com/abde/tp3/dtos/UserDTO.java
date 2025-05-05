package com.abde.tp3.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public record UserDTO(Long id, String username, String email, Set<String> roles, LocalDateTime createdAt,
    Integer documentCount) {

}
