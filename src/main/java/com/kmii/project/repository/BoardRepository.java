package com.kmii.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}
