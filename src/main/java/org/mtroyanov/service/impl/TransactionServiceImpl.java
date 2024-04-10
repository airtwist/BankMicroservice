package org.mtroyanov.service.impl;

import lombok.RequiredArgsConstructor;
import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.Transaction;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.mapper.TransactionMapper;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.repository.TransactionRepository;
import org.mtroyanov.service.CurrencyService;
import org.mtroyanov.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final CurrencyService currencyService;

    private final LimitRepository limitRepository;


    @Override
    public Transaction getTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction transact(CreateTransactionDto createTransactionDto) {
        BigDecimal exchangeRate = currencyService.getCurrencyExchangeRate(createTransactionDto.getCurrencyId());
        Transaction transaction = TransactionMapper.mapToTransaction(createTransactionDto, exchangeRate);
        ExpenseLimit expenseLimit = limitRepository.findFirstByAccountIdAndCategoryOrderByDateTime(transaction.getAccountFrom(), transaction.getCategory());
        if (getTransactionSum(transaction).compareTo(expenseLimit.getAmount()) > 0) {
            transaction.setExceededLimitId(expenseLimit.getId());
        }
        transactionRepository.save(transaction);
        return transaction;
    }

    @Override
    public List<TransactionDto> findTransactionsExceedingLimit(Long accountId) {
        List<Transaction> limitExceededTransactions = transactionRepository.findAllByAccountFromAndExceededLimitIdNotNull(accountId);
        return limitExceededTransactions.stream().map(TransactionMapper::mapToTransactionDto).collect(Collectors.toList());
    }
    public BigDecimal getTransactionSum(Transaction transaction){
        LocalDateTime dateTimeBefore = transaction.getDateTime().minusMonths(1L);
        Category category = transaction.getCategory();
        Long accountId = transaction.getAccountFrom();
        return transactionRepository.findAllByDateTimeAfterAndCategoryAndAccountFrom(dateTimeBefore, category, accountId).stream()
                .map(Transaction::getSumUsd)
                .reduce(transaction.getSumUsd(), BigDecimal::add);
    }
}
