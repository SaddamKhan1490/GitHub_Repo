/**
  * Created by Saddam on 05/04/2018
  */
  
package com.example.repository;

import com.example.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
