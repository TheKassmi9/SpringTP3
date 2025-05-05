package com.abde.tp3.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;
import com.abde.tp3.repos.DocumentRepository;
import com.abde.tp3.services.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;
  final String HOME = System.getProperty("user.home");
  final String uploadsDir = "uploads";

  DocumentServiceImpl(DocumentRepository documentRepository) {
    this.documentRepository = documentRepository;
  }

  @Override
  public Optional<Document> getDocument(Long id) {
    // TODO Auto-generated method stub
    return documentRepository.findById(id);
  }

  @Override
  public Document deleteDocument(Long id) {
    Optional<Document> document = documentRepository.findById(id);
    if (document.isPresent()) {
      documentRepository.deleteById(id);
      deleteFileFromFS(document.get().getPath());
      return document.get();
    }
    return null;
  }

  @Override
  public byte[] downloaDocument(Long id) {
    Optional<Document> document = documentRepository.findById(id);
    if (document.isPresent()) {
      // FileSystemResource fileSystemResource = new FileSystemResource(
      // HOME + uploadsDir + "\\" + document.get().getPath());
      // return fileSystemResource;
      return downlodMultipartFile(HOME + "\\" + uploadsDir + "\\" + document.get().getPath());
    }
    return null;

  }

  @Override
  public List<Document> getAllDocuments() {
    return documentRepository.findAll();
  }

  @Override
  public Document uploadDocument(MultipartFile file) {
    Document document = new Document();
    document.setFileName(file.getOriginalFilename());
    document.setFileSize(file.getSize());
    document.setType(file.getContentType());
    document.setPath(generateFilePath(file.getOriginalFilename()));
    documentRepository.save(document);
    saveToFileSystem(file);
    return document;
  }

  private String generateFilePath(String filename) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String timestamp = dateFormat.format(new Date());
    String[] parts = filename.split("\\.(?=[^.]*$)", 2);
    String name = "";
    String extension = "";
    if (parts.length == 2) {
      name = parts[0];
      extension = parts[1];
    } else {
      name = parts[0];
    }
    return name + timestamp + "." + extension;
  }

  private String saveToFileSystem(MultipartFile file) {
    final String filename = generateFilePath(file.getOriginalFilename());
    final File file2 = new File(HOME + "\\" + uploadsDir, filename);
    try {
      Path uploads = Paths.get(HOME, uploadsDir);
      System.out.println(uploads.getFileName().toString());
      if (Files.notExists(uploads)) {
        Files.createDirectory(uploads);
      }
      try (FileOutputStream fileOutputStream = new FileOutputStream(file2)) {
        Path filePath = Paths.get(HOME + "\\" + uploadsDir + "\\" + filename);
        if (Files.notExists(filePath)) {
          System.out.println("File Path: " + filePath);
          Files.createFile(filePath);
        }
        fileOutputStream.write(file.getBytes());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return filename;
  }

  private byte[] downlodMultipartFile(String path) {
    File file = new File(path);
    try (FileInputStream fileInputStream = new FileInputStream(file)) {
      byte[] data = fileInputStream.readAllBytes();
      return data;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public Document updateDoc(MultipartFile file, Long id) {
    Optional<Document> document = documentRepository.findById(id);
    if (document.isPresent()) {
      deleteFileFromFS(document.get().getPath());
      document.get().setFileName(file.getOriginalFilename());
      document.get().setFileSize(file.getSize());
      document.get().setPath(generateFilePath(file.getOriginalFilename()));
      document.get().setType(file.getContentType());
      documentRepository.save(document.get());
      saveToFileSystem(file);
      return document.get();
    }
    return null;
  }

  void deleteFileFromFS(String path) {
    Path deletePath = Paths.get(HOME, new String[] { uploadsDir, path });
    try {
      Files.delete(deletePath);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
