package com.pixel.transfer.service;

import com.pixel.transfer.dto.PhoneDto;
import com.pixel.transfer.entity.PhoneData;
import com.pixel.transfer.repository.PhoneDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class PhoneService {

    private final PhoneDataRepository phoneDataRepository;

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void addPhone(Long userId, String phone) {
        if (phoneDataRepository.existsByPhone(phone)) {
            throw new IllegalArgumentException("Phone is already taken");
        }

        PhoneData phoneData = PhoneData.builder()
                .userId(userId)
                .phone(phone)
                .build();
        phoneDataRepository.save(phoneData);
    }

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void removePhone(Long userId, Long id) {
        PhoneData phoneData = getPhone(id);

        if (!phoneData.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot remove phone of another user");
        }

        if (phoneDataRepository.countByUserId(userId) <= 1) {
            throw new IllegalStateException("User must have at least one phone");
        }
        phoneDataRepository.delete(phoneData);
    }

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void updatePhone(Long userId, Long id, PhoneDto newPhoneDto) {
        if (phoneDataRepository.existsByPhone(newPhoneDto.getPhone())) {
            throw new IllegalArgumentException("New phone is already taken");
        }

        PhoneData phoneData = getPhone(id);

        if (!phoneData.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot update phone of another user");
        }
        phoneData.setPhone(newPhoneDto.getPhone());
        phoneDataRepository.save(phoneData);
    }

    private PhoneData getPhone(Long id) {
        return phoneDataRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Phone with id = %s not found", id)));
    }
}
