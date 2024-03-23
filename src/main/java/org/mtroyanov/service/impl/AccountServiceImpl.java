package org.mtroyanov.service.impl;

import org.mtroyanov.dto.CreateAccountDto;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.Account;
import org.mtroyanov.entity.Category;
import org.mtroyanov.repository.AccountRepository;
import org.mtroyanov.service.AccountService;
import org.mtroyanov.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    public static final BigDecimal DEFAULT_LIMIT_USD = BigDecimal.valueOf(1000);

    private final AccountRepository accountRepository;

    private final LimitService limitService;

    @Override
    public void createAccount(CreateAccountDto createAccountDto) {
        Account account = new Account();
        accountRepository.save(account);
        for (Category category : Category.values()) {
            LimitDto limitDto = new LimitDto(category, DEFAULT_LIMIT_USD);
            limitService.updateLimit(account.getId(), limitDto);
        }
        for (LimitDto limit : createAccountDto.getLimits()) {
            limitService.updateLimit(account.getId(), limit);
        }
    }
}
