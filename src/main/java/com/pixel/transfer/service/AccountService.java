package com.pixel.transfer.service;

import com.pixel.transfer.entity.Account;
import com.pixel.transfer.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    @Transactional
    @CacheEvict(cacheNames = "users", allEntries = true)
    public void increaseBalances() {
        List<Account> accounts = accountRepository.findAll();
        for (Account account : accounts) {
            BigDecimal current = account.getBalance();
            BigDecimal increased = current.multiply(BigDecimal.valueOf(1.1)).setScale(2, RoundingMode.HALF_UP);
            BigDecimal maxAllowed = account.getInitialBalance().multiply(BigDecimal.valueOf(2.07)).setScale(2, RoundingMode.HALF_UP);

            if (increased.compareTo(maxAllowed) > 0) {
                increased = maxAllowed;
            }

            if (increased.compareTo(current) > 0) {
                account.setBalance(increased);
                accountRepository.save(account);
            }
        }
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "users", key = "#fromUserId"),
            @CacheEvict(cacheNames = "users", key = "#toUserId")
    })
    public void transferMoney(Long fromUserId, Long toUserId, BigDecimal amount) {
        if (fromUserId.equals(toUserId)) {
            throw new IllegalArgumentException("Cannot transfer to the same user");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account fromAccount = accountRepository.findByUserId(fromUserId)
                .orElseThrow(() -> new NoSuchElementException("Sender account not found"));
        Account toAccount = accountRepository.findByUserId(toUserId)
                .orElseThrow(() -> new NoSuchElementException("Receiver account not found"));

        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
