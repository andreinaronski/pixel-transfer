package com.pixel.transfer.service;

import com.pixel.transfer.dto.EmailDto;
import com.pixel.transfer.entity.EmailData;
import com.pixel.transfer.repository.EmailDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final EmailDataRepository emailDataRepository;

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void addEmail(Long userId, EmailDto emailDto) {
        if (emailDataRepository.existsByEmail(emailDto.getEmail())) {
            throw new IllegalArgumentException("Email is already taken");
        }

        EmailData emailData = EmailData.builder()
                .userId(userId)
                .email(emailDto.getEmail())
                .build();

        emailDataRepository.save(emailData);
    }

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void removeEmail(Long userId, Long id) {
        log.info("Deleting email with id={}.", id);
        EmailData emailData = getEmail(id);

        if (!emailData.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot remove email of another user");
        }

        long count = emailDataRepository.countByUserId(userId);
        if (count <= 1) {
            throw new IllegalStateException("User must have at least one email");
        }
        emailDataRepository.delete(emailData);
    }

    @Transactional
    @CacheEvict(cacheNames = {"users", "usersSearch"}, allEntries = true)
    public void updateEmail(Long userId, Long id, EmailDto emailDto) {
        log.info("Updating email with id={}.", id);
        if (emailDataRepository.existsByEmail(emailDto.getEmail())) {
            throw new IllegalArgumentException("New email is already taken");
        }
        EmailData emailData = getEmail(id);
        if (!emailData.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cannot update email of another user");
        }
        emailData.setEmail(emailDto.getEmail());
        emailDataRepository.save(emailData);
    }

    private EmailData getEmail(Long id) {
        return emailDataRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Email with id = %s not found", id)));
    }
}
