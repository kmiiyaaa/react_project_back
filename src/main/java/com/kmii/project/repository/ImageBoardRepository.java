package com.kmii.project.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.entity.ImageBoard;

public interface ImageBoardRepository extends JpaRepository<ImageBoard, Long> {
    Page<ImageBoard> findAll(Pageable pageable);
}