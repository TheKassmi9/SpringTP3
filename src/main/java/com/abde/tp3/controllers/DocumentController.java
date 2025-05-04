package com.abde.tp3.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;
import com.abde.tp3.services.DocumentServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping(name = "/api")
public class DocumentController {
  private final DocumentServiceImpl documentServiceImpl;

  DocumentController(DocumentServiceImpl documentServiceImpl) {
    this.documentServiceImpl = documentServiceImpl;
  }

  @PostMapping("/document/upload")
  public ResponseEntity<Document> upload(@RequestPart("file") MultipartFile file) throws URISyntaxException {
    return ResponseEntity.created(URI.create("/document/docId")).body(documentServiceImpl.uploadDocument(file));
  }

  @GetMapping(name = "/documents")
  public ResponseEntity<List<Document>> getDocs() {
    return ResponseEntity.ok().body(documentServiceImpl.getAllDocuments());
  }

  @GetMapping(name = "/document/{id}")
  public byte[] downloadDoc(@PathVariable Long id) {
    try {
      return documentServiceImpl.downloaDocument(id).getContentAsByteArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @DeleteMapping(name = "/document/{id}")
  public ResponseEntity<Document> deleteDocument(@PathVariable Long id) {
    return ResponseEntity.ok().body(documentServiceImpl.deleteDocument(id));
  }

  @PutMapping("/document/upload/{id}")
  public ResponseEntity<Document> update(@RequestPart("file") MultipartFile file, @PathVariable Long id) {
    return ResponseEntity.created(URI.create("/document/docId")).body(documentServiceImpl.updateDoc(file, id));
  }
}
