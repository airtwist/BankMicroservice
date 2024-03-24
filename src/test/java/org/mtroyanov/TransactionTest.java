package org.mtroyanov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mtroyanov.dto.CreateTransactionDto;
import org.mtroyanov.dto.TransactionDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.Transaction;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.id.CurrencyId;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.repository.TransactionRepository;
import org.mtroyanov.service.CurrencyService;
import org.mtroyanov.service.impl.TransactionServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {
    private final static long LIMIT_ID = 123L;
    private final static BigDecimal LIMIT = new BigDecimal("500.00");
    private final static BigDecimal EXCHANGE_RATE = new BigDecimal("0.01");
    private final static ExpenseLimit EXPENSE_LIMIT = new ExpenseLimit() {{
        setId(LIMIT_ID);
        setAmount(LIMIT);
        setZoneDateTime(OffsetDateTime.now(ZoneId.systemDefault()));
    }};


    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private LimitRepository limitRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    void transact_ShouldCreateTransaction() {
        when(limitRepository.findFirstByAccountIdAndCategoryOrderByDateTime(any(), any())).thenReturn(EXPENSE_LIMIT);
        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(currencyService.getCurrencyExchangeRate(any())).thenReturn(EXCHANGE_RATE);

        CreateTransactionDto createTransactionDto = new CreateTransactionDto();
        createTransactionDto.setCurrencyId(CurrencyId.USD);
        createTransactionDto.setSum(new BigDecimal("100.00"));
        createTransactionDto.setCategory(Category.PRODUCT);
        createTransactionDto.setAccountFromId(1L);
        createTransactionDto.setAccountToId(2L);

        Transaction result = transactionService.transact(createTransactionDto);

        assertThat(result.getSum()).isEqualTo(new BigDecimal("100.00"));
        assertThat(result.getSumUsd()).isEqualTo(new BigDecimal("1.00"));
        assertThat(result.getExceededLimitId()).isNull();
    }

    @Test
    void transact_ShouldMarkLimitAsExceeded_WhenSumExceedsLimit() {
        CreateTransactionDto dto = new CreateTransactionDto();
        dto.setCurrencyId(CurrencyId.RUB);
        dto.setSum(new BigDecimal("30000"));
        dto.setCategory(Category.PRODUCT);
        dto.setAccountFromId(1L);
        dto.setAccountToId(2L);

        BigDecimal existingTransactionsSumUsd = new BigDecimal("300");


        Transaction existingTransaction = new Transaction();
        existingTransaction.setSumUsd(existingTransactionsSumUsd);

        when(transactionRepository.findAllByDateTimeAfterAndCategoryAndAccountFrom(
                any(LocalDateTime.class), eq(dto.getCategory()), eq(dto.getAccountFromId())))
                .thenReturn(List.of(existingTransaction));

        when(transactionRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(limitRepository.findFirstByAccountIdAndCategoryOrderByDateTime(any(), any())).thenReturn(EXPENSE_LIMIT);
        when(currencyService.getCurrencyExchangeRate(any())).thenReturn(EXCHANGE_RATE);

        Transaction result = transactionService.transact(dto);

        assertNotNull(result.getExceededLimitId());
        assertEquals(LIMIT_ID, result.getExceededLimitId(), "The exceeded limit ID should match the expected limit ID.");
    }

    @Test
    void findTransactionsExceedingLimit_ReturnsCorrectData() {
        // Given
        Long accountId = 1L;
        Transaction mockTransaction = new Transaction();
        // Assume setters are available to set up mockTransaction
        mockTransaction.setAccountFrom(accountId);
        mockTransaction.setAccountTo(2L);
        mockTransaction.setCurrencyId(CurrencyId.USD);
        mockTransaction.setSum(new BigDecimal("100"));
        ExpenseLimit exceededLimit = new ExpenseLimit();
        exceededLimit.setId(LIMIT_ID);
        exceededLimit.setAmount(LIMIT);
        exceededLimit.setZoneDateTime(OffsetDateTime.now(ZoneId.systemDefault()));
        mockTransaction.setExceededLimit(exceededLimit);
        mockTransaction.setZoneDateTime(OffsetDateTime.now(ZoneId.systemDefault()));

        when(transactionRepository.findAllByAccountFromAndExceededLimitIdNotNull(accountId))
                .thenReturn(List.of(mockTransaction));

        List<TransactionDto> result = transactionService.findTransactionsExceedingLimit(accountId);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        TransactionDto resultDto = result.get(0);

        assertEquals(mockTransaction.getAccountFrom(), resultDto.getAccountFromId());
        assertEquals(mockTransaction.getAccountTo(), resultDto.getAccountToId());
        assertEquals(mockTransaction.getSum(), resultDto.getSum());

        assertEquals(exceededLimit.getAmount(), resultDto.getLimitSum());
    }
}
