package com.abde.tp3.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abde.tp3.model.Document;
import com.abde.tp3.model.User;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
  @Query("SELECT d FROM Document d WHERE d.user.email = :email")
  List<Document> findDocumentsByUserEmail(@Param("email") String email);
}
