package com.pixel.transfer.repository;

import com.pixel.transfer.entity.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailDataRepository extends JpaRepository<EmailData, Long> {
    Optional<EmailData> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByUserId(Long userId);
}
