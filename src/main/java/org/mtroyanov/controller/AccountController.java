package org.mtroyanov.controller;

import org.mtroyanov.dto.CreateAccountDto;
import org.mtroyanov.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client-api/accounts")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public void createAccount(@RequestBody CreateAccountDto createAccountDTO) {
        accountService.createAccount(createAccountDTO);
    }
}
