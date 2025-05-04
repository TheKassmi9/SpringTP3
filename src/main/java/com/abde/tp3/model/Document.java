package com.abde.tp3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String fileName;
  private Long fileSize;
  private String type;
  private String path;

}
