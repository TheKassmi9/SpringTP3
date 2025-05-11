package com.abde.tp3.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_document", schema = "public")
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String fileName;
  private Long fileSize;
  @NotBlank
  private String type;
  @NotBlank
  private String path;
  @CreatedDate
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private LocalDateTime uploadDate;
  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDateTime updatedDateTime;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnore
  private User user;
  @Column(nullable = true, columnDefinition = "text", name = "document_description")
  private String description;

}
