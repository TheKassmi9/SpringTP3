package com.abde.tp3.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.abde.tp3.Enums.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class UserDTO {
    private final Long id;
    private final String username;
    private final String email;
    private final List<Roles> roles;
    private final LocalDateTime createdAt;
    private final Integer documentCount;
}
