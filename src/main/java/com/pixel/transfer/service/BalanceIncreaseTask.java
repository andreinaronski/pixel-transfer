package com.pixel.transfer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class BalanceIncreaseTask {

    private final AccountService accountService;

    @Scheduled(fixedRate = 30_000)
    public void increaseBalance() {
        log.info("Starting scheduled balance increase");
        accountService.increaseBalances();
        log.info("Finished scheduled balance increase");
    }
}
