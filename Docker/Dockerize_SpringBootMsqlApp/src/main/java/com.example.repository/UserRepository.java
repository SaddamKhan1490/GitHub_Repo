/**
  * Created by Saddam on 04/31/2018
  */

package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Users;

public interface UserRepository extends JpaRepository <Users, Integer> {
	
}
