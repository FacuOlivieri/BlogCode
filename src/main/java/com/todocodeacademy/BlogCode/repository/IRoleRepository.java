package com.todocodeacademy.BlogCode.repository;

import com.todocodeacademy.BlogCode.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
}
