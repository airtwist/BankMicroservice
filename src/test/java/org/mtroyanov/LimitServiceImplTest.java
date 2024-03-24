package org.mtroyanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.service.impl.LimitServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneId;

class LimitServiceImplTest {

    @Mock
    private LimitRepository limitRepository;

    @InjectMocks
    private LimitServiceImpl limitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateLimit_ShouldSaveNewExpenseLimit() {
        Long accountId = 1L;
        LimitDto limitDto = new LimitDto();
        limitDto.setAmount(new BigDecimal("1000.00"));
        limitDto.setCategory(Category.PRODUCT);
        ExpenseLimit savedExpenseLimit = new ExpenseLimit();
        savedExpenseLimit.setAccountId(accountId);
        savedExpenseLimit.setAmount(limitDto.getAmount());
        savedExpenseLimit.setCategory(limitDto.getCategory());
        savedExpenseLimit.setZoneDateTime(OffsetDateTime.now(ZoneId.systemDefault()));
        when(limitRepository.save(any(ExpenseLimit.class))).thenReturn(savedExpenseLimit);

        ExpenseLimit result = limitService.updateLimit(accountId, limitDto);

        verify(limitRepository).save(any(ExpenseLimit.class));
        assertNotNull(result);
        assertEquals(limitDto.getAmount(), result.getAmount());
        assertEquals(limitDto.getCategory(), result.getCategory());
        assertEquals(accountId, result.getAccountId());
    }
}
