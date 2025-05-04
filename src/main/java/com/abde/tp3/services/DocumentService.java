package com.abde.tp3.services;

import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;

public interface DocumentService {
  Document uploadDocument(MultipartFile file);

  List<Document> getAllDocuments();

  FileSystemResource downloaDocument(Long id);

  Document deleteDocument(Long id);
}
