package org.mtroyanov.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag( name = "Транзакции",
        description = ""
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/integration-api/transactions")
public class TransactionIntegrationController {

    private final TransactionService transactionService;

    @PutMapping("/create")
    public void createTransaction(@RequestBody CreateTransactionDto createTransactionDto){
        transactionService.transact(createTransactionDto);
    }
}
