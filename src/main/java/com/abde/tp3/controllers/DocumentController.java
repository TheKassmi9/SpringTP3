package com.abde.tp3.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;
import com.abde.tp3.services.impl.DocumentServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api")
public class DocumentController {
  private final DocumentServiceImpl documentServiceImpl;

  DocumentController(DocumentServiceImpl documentServiceImpl) {
    this.documentServiceImpl = documentServiceImpl;
  }

  @PostMapping(value = "/document/upload")
  public ResponseEntity<Document> upload(@RequestPart("file") MultipartFile file) throws URISyntaxException {
    return ResponseEntity.created(URI.create("/document/docId")).body(documentServiceImpl.uploadDocument(file));
  }

  @GetMapping("/documents")
  public ResponseEntity<List<Document>> getDocs() {
    return ResponseEntity.ok().body(documentServiceImpl.getAllDocuments());
  }

  @GetMapping("/document/{id}")
  public ResponseEntity<byte[]> downloadDoc(@PathVariable Long id) {
    Optional<Document> document = documentServiceImpl.getDocument(id);
    // return documentServiceImpl.downloaDocument(id);
    if (document.isPresent()) {
      // Map<String, String> headers = new HashMap<>();
      // headers.put("Content-Type", document.get().getType());
      // headers.put("Content-Disposition", "inline; " +
      // document.get().getFileName());
      // headers.put("Content-Length", document.get().getFileSize().toString());
      return ResponseEntity.ok()
          .header("Content-Type", document.get().getType())
          .header("Content-Disposition", "attachment; filename=" + document.get().getFileName())
          .header("Content-Length", document.get().getFileSize().toString())
          .body(documentServiceImpl.downloaDocument(id));
    }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/document/{id}")
  public ResponseEntity<Document> deleteDocument(@PathVariable Long id) {
    return ResponseEntity.ok().body(documentServiceImpl.deleteDocument(id));
  }

  @PutMapping("/document/upload/{id}")
  public ResponseEntity<Document> update(@RequestPart("file") MultipartFile file, @PathVariable Long id) {
    return ResponseEntity.created(URI.create("/document/docId")).body(documentServiceImpl.updateDoc(file, id));
  }
}
