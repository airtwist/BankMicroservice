package org.mtroyanov.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mtroyanov.dto.CreateAccountDto;
import org.mtroyanov.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@Tag( name = "Аккаунты",
        description = "Создание аккаунта"
)

@AllArgsConstructor
@RestController
@RequestMapping("/client-api/accounts")
public class AccountController {

    private final AccountService accountService;
    @Operation(summary = "Создать аккаунт", description = "Создает аккаунт с указанными лимитами, или создает аккаунт с лимитами по умолчанию на каждую категорию в 1000$")
    @PostMapping("/create")
    public void createAccount(@RequestBody CreateAccountDto createAccountDTO) {
        accountService.createAccount(createAccountDTO);
    }
}
