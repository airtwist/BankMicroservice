package org.mtroyanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtroyanov.dto.CreateAccountDto;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.Account;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.repository.AccountRepository;
import org.mtroyanov.service.LimitService;
import org.mtroyanov.service.impl.AccountServiceImpl;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private LimitService limitService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createAccount_ShouldInitializeAccountWithDefaultAndCustomLimits() {
        CreateAccountDto createAccountDto = new CreateAccountDto();
        LimitDto customLimit = new LimitDto(Category.PRODUCT, new BigDecimal("1500"));
        createAccountDto.getLimits().add(customLimit);

        Account savedAccount = new Account();
        savedAccount.setId(1L); // Assuming IDs are assigned by the repository on save
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        accountService.createAccount(createAccountDto);

        verify(accountRepository).save(any(Account.class));

        for (Category category : Category.values()) {
            verify(limitService).updateLimit(eq(savedAccount.getId()), eq(new LimitDto(category, AccountServiceImpl.DEFAULT_LIMIT_USD)));
        }

        verify(limitService).updateLimit(eq(savedAccount.getId()), eq(customLimit));
    }
}
