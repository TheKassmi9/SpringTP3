package com.abde.tp3.services;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;

public interface DocumentService {
  Document uploadDocument(MultipartFile file, String description);

  List<Document> getAllDocuments();

  Optional<Document> getDocument(Long id);

  byte[] downloaDocument(Long id);

  Document deleteDocument(Long id);
}
