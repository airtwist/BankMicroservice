package org.mtroyanov.controller;

import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client-api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/exceeded-transactions/{accountId}")
    public List<TransactionDto> getExceededTransactions(@PathVariable("accountId") Long accountId){
       return transactionService.findTransactionsExceedingLimit(accountId);
    }
    @PutMapping("/create")
    public void createTransaction(@RequestBody CreateTransactionDto createTransactionDto){
        transactionService.transact(createTransactionDto);
    }
}
