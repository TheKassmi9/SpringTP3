package com.abde.tp3.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.abde.tp3.model.Document;
import com.abde.tp3.model.User;
import com.abde.tp3.repos.DocumentRepository;
import com.abde.tp3.repos.UserRepo;
import com.abde.tp3.services.DocumentService;

import lombok.AllArgsConstructor;
import lombok.Data;

@Slf4j
@Service
@Data
@AllArgsConstructor
@Transactional
public class DocumentServiceImpl implements DocumentService {
  private final DocumentRepository documentRepository;
  private final UserRepo userRepo;
  final String HOME = System.getProperty("user.home");
  final String uploadsDir = "uploads";

  @Override
  public Optional<Document> getDocument(Long id) {
    Optional<Document> document = documentRepository.findById(id);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (document.isPresent() && userDetails != null) {
      if (document.get().getUser().getEmail().equals(userDetails.getUsername()))
        return document;
    }
    return null;
  }

  @Override
  public Document deleteDocument(Long id) {
    Optional<Document> document = documentRepository.findById(id);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (document.isPresent() && userDetails != null) {
      if (document.get().getUser().getEmail().equals(userDetails.getUsername())) {
        User user = userRepo.findUserByEmail(userDetails.getUsername());
        documentRepository.deleteById(id);
        deleteFileFromFS(document.get().getPath());
        user.setDocumentCount(user.getDocumentCount() - 1);
        return document.get();
      }
    }
    return null;
  }

  @Override
  public byte[] downloaDocument(Long id) {
    Optional<Document> document = documentRepository.findById(id);
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (document.isPresent() && userDetails != null) {
      if (document.get().getUser().getEmail().equals(userDetails.getUsername()))
        return downlodMultipartFile(HOME + "\\" + uploadsDir + "\\" + document.get().getPath());
    }
    return null;

  }

  @Override
  public List<Document> getAllDocuments() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    log.info("UserDetails: " + userDetails.getUsername());
    if (userDetails != null) {
//      User user= userRepo.findUserByEmail(userDetails.getUsername());
      return documentRepository.findDocumentsByUserEmail(userDetails.getUsername());
    }
    return new ArrayList<>();
  }

  @Override
  public Document uploadDocument(MultipartFile file, String description) {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (userDetails != null) {

      User user = userRepo.findUserByEmail(userDetails.getUsername());
      Document document = new Document();
      document.setFileName(file.getOriginalFilename());
      document.setFileSize(file.getSize());
      document.setType(file.getContentType());
      document.setPath(generateFilePath(file.getOriginalFilename()));
      document.setUser(user);
      document.setDescription(description);
      log.info("Before saving User updates into the DB: count: "+user.getDocumentCount());
      documentRepository.save(document);
      saveToFileSystem(file);
      user.setDocumentCount(user.getDocumentCount() + 1);
      userRepo.save(user);
      return document;
    }
    return null;
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
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (document.isPresent() && userDetails != null) {
      if (document.get().getUser().getEmail().equals(userDetails.getUsername())) {
        deleteFileFromFS(document.get().getPath());
        document.get().setFileName(file.getOriginalFilename());
        document.get().setFileSize(file.getSize());
        document.get().setPath(generateFilePath(file.getOriginalFilename()));
        document.get().setType(file.getContentType());
        documentRepository.save(document.get());
        saveToFileSystem(file);
        return document.get();
      }
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
