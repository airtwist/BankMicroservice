package org.mtroyanov.service.impl;

import org.apache.commons.math3.util.Precision;
import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.entity.id.Category;
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
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        BigDecimal sum = createTransactionDto.getSum().setScale(2, RoundingMode.HALF_UP);
        BigDecimal exchangeRate = currencyService.getCurrencyExchangeRate(currencyId);
        Category category = createTransactionDto.getCategory();
        Long accountFromId = createTransactionDto.getAccountFromId();

        Transaction transaction = new Transaction();
        transaction.setAccountFrom(accountFromId);
        transaction.setAccountTo(createTransactionDto.getAccountToId());
        transaction.setSum(sum);
        transaction.setCategory(category);
        transaction.setCurrencyId(currencyId);
        BigDecimal result = sum.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        transaction.setSumUsd(result);
        transaction.setZoneDateTime(OffsetDateTime.now(ZoneId.systemDefault()));

        LocalDateTime monthBefore = transaction.getDateTime().minusMonths(1L);

        BigDecimal transactionSum = transactionRepository.findAllByDateTimeAfterAndCategoryAndAccountFrom(monthBefore, category, accountFromId).stream()
                .map(Transaction::getSumUsd)
                .reduce(transaction.getSumUsd(), BigDecimal::add);

        ExpenseLimit expenseLimit = limitRepository.findFirstByAccountIdAndCategoryOrderByDateTime(accountFromId, category);
        if (transactionSum.compareTo(expenseLimit.getAmount()) > 0) {
            transaction.setExceededLimitId(expenseLimit.getId());
        }
        transactionRepository.save(transaction);
        return transaction;
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
            transactionDto.setSum(limitExceededTransaction.getSum());
            transactionDto.setCategory(limitExceededTransaction.getCategory());
            transactionDto.setDateTime(limitExceededTransaction.getZoneDateTime());
            transactionDto.setLimitSum(limitExceededTransaction.getExceededLimit().getAmount());
            transactionDto.setLimitDateTime(limitExceededTransaction.getExceededLimit().getZoneDateTime());
            transactionDto.setLimitCurrencyShortname(CurrencyId.USD);
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }
}
