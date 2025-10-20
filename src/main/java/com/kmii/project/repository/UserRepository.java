package com.kmii.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kmii.project.entity.SiteUser;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

	public Optional<SiteUser> findByUsername(String username); //중복 체크용

}
