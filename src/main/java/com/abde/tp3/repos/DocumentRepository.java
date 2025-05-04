package com.abde.tp3.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abde.tp3.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

}
