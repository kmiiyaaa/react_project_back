package com.kmii.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
