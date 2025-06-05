package com.pixel.transfer.repository;

import com.pixel.transfer.entity.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataRepository extends JpaRepository<PhoneData, Long> {

    boolean existsByPhone(String phone);

    long countByUserId(Long userId);
}
