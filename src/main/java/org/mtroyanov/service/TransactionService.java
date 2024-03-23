package org.mtroyanov.service;

import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.entity.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction getTransaction(Transaction transaction);

    Transaction transact(CreateTransactionDto createTransactionDto);

    List<TransactionDto> findTransactionsExceedingLimit(Long accountId);

}
