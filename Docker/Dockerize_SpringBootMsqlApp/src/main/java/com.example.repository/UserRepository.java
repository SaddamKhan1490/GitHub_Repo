package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Users;

public interface UserRepository extends JpaRepository <Users, Integer> {
	
}
