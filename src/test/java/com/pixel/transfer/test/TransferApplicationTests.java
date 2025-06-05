package com.pixel.transfer.test;

import com.pixel.transfer.entity.Account;
import com.pixel.transfer.entity.User;
import com.pixel.transfer.repository.AccountRepository;
import com.pixel.transfer.repository.UserRepository;
import com.pixel.transfer.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TransferApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testTransferMoney() {
		User sender = User.builder()
				.name("Sender")
				.password("pass")
				.build();
		userRepository.save(sender);

		Account senderAccount = Account.builder()
				.user(sender)
				.balance(BigDecimal.valueOf(1000))
				.initialBalance(BigDecimal.valueOf(1000))
				.build();
		accountRepository.save(senderAccount);

		User receiver = User.builder()
				.name("Receiver")
				.password("pass").build();
		userRepository.save(receiver);

		Account receiverAccount = Account.builder()
				.user(receiver)
				.balance(BigDecimal.valueOf(500))
				.initialBalance(BigDecimal.valueOf(500))
				.build();
		accountRepository.save(receiverAccount);

		accountService.transferMoney(sender.getId(), receiver.getId(), BigDecimal.valueOf(200));

		Account senderAccAfter = accountRepository.findByUserId(sender.getId()).get();
		Account receiverAccAfter = accountRepository.findByUserId(receiver.getId()).get();

		assertEquals(
				BigDecimal.valueOf(800).setScale(2, RoundingMode.HALF_UP),
				senderAccAfter.getBalance().setScale(2, RoundingMode.HALF_UP)
		);
		assertEquals(
				BigDecimal.valueOf(700).setScale(2, RoundingMode.HALF_UP),
				receiverAccAfter.getBalance().setScale(2, RoundingMode.HALF_UP)
		);
	}
}
