package com.group2.library_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.group2.library_management.entity.RefreshToken;
import com.group2.library_management.entity.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    int deleteByUser(User user);
}
