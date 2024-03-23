package org.mtroyanov.service.impl;

import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.entity.Category;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.Transaction;
import org.mtroyanov.entity.id.CurrencyId;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.repository.TransactionRepository;
import org.mtroyanov.service.CurrencyService;
import org.mtroyanov.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
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
        CurrencyId currencyId = createTransactionDto.getCurrencyId();
        BigDecimal sum = createTransactionDto.getSum();
        BigDecimal exchangeRate = currencyService.getCurrencyExchangeRate(currencyId);
        Category category = createTransactionDto.getCategory();
        Long accountFromId = createTransactionDto.getAccountFromId();

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(accountFromId);
        transaction.setAccountTo(createTransactionDto.getAccountToId());
        transaction.setTransactionSumm(sum);
        transaction.setCategory(category);
        transaction.setCurrencyId(currencyId);
        transaction.setTransactionSumInUsd(sum.multiply(exchangeRate));
        transaction.setDateTime(OffsetDateTime.now(ZoneId.systemDefault()));

        OffsetDateTime oneMonthBefore = OffsetDateTime.now().minusMonths(1);
        BigDecimal transactionSum = transactionRepository.findAllByDateTimeAfterAndCategoryAndAccountFrom(oneMonthBefore, category, accountFromId)
                .stream()
                .map(Transaction::getTransactionSumInUsd)
                .reduce(transaction.getTransactionSumInUsd(), BigDecimal::add);

        ExpenseLimit expenseLimit = limitRepository.getExpenseLimitByAccountIdAndCategory(accountFromId, category);
        if (transactionSum.compareTo(expenseLimit.getAmount()) > 0) {
            transaction.setExceededLimitId(expenseLimit.getId());
        }
        return transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDto> findTransactionsExceedingLimit(Long accountId) {
        List<Transaction> limitExceededTransactions = transactionRepository.findAllByAccountFromAndExceededLimitIdNotNull(accountId);
        List<TransactionDto> transactionDtoList = new ArrayList<>();
        for (Transaction limitExceededTransaction : limitExceededTransactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setAccountFromId(limitExceededTransaction.getAccountFrom());
            transactionDto.setAccountToId(limitExceededTransaction.getAccountTo());
            transactionDto.setCurrencyId(limitExceededTransaction.getCurrencyId());
            transactionDto.setSum(limitExceededTransaction.getTransactionSumm());
            transactionDto.setCategory(limitExceededTransaction.getCategory());
            transactionDto.setDateTime(limitExceededTransaction.getDateTime());
            transactionDto.setLimitSum(limitExceededTransaction.getExceededLimit().getAmount());
            transactionDto.setLimitDateTime(limitExceededTransaction.getExceededLimit().getDateTime());
            transactionDto.setLimitCurrencyShortname(limitExceededTransaction.getExceededLimit().getCurrencyId());
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }
}
