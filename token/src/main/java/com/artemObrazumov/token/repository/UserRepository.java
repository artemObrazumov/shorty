package com.artemObrazumov.token.repository;

import com.artemObrazumov.token.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByName(String name);
    Optional<Long> findIdByName(String name);
}