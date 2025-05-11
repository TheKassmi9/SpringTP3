package com.abde.tp3.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abde.tp3.model.Document;
import com.abde.tp3.model.User;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
  User findUserByEmail(String email);

  // List<Document> findDocumentsByUserEmail(String email);
}
