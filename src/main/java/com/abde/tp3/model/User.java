package com.abde.tp3.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.abde.tp3.Enums.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
  @Id
  @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence",
  allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
  private Long id;
  @Email
  private String email;
  @NotBlank
  private String username;
  @NotBlank
  @Size(min = 8)
  @JsonIgnore
  private String password;
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  @Column(name = "created_at", updatable = false,insertable = true, columnDefinition = "timestamp")
  private LocalDateTime createdAt;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") )
  private List<Roles> roles;

  @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  private List<Document> documents;
  private Integer documentCount = 0;

}
