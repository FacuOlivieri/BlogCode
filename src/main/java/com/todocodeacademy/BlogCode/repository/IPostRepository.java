package com.todocodeacademy.BlogCode.repository;

import com.todocodeacademy.BlogCode.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
}
