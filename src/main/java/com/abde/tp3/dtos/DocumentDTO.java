package com.abde.tp3.dtos;

public record DocumentDTO(Long id, String fileName, Long fileSize, String fileType, String ownerUserName, String path) {
}
