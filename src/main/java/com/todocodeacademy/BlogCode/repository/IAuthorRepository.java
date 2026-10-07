package com.todocodeacademy.BlogCode.repository;

import com.todocodeacademy.BlogCode.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorRepository extends JpaRepository<Author, Long> {
}
