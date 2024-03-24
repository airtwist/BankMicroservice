package org.mtroyanov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mtroyanov.api.HttpClient;
import org.mtroyanov.dto.CurrencyRate;
import org.mtroyanov.entity.Currency;
import org.mtroyanov.entity.id.CurrencyId;
import org.mtroyanov.repository.CurrencyRepository;
import org.mtroyanov.service.impl.CurrencyRateImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CurrencyRateImplTest {

    @Mock
    private CurrencyRepository currencyRepository;

    @Mock
    private HttpClient httpClient;

    @InjectMocks
    private CurrencyRateImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCurrencyExchangeRate_WithUSD_ShouldReturnOne() {
        CurrencyId currencyId = CurrencyId.USD;

        BigDecimal rate = currencyService.getCurrencyExchangeRate(currencyId);

        assertEquals(BigDecimal.ONE, rate);
        verifyNoInteractions(currencyRepository, httpClient);
    }

    @Test
    void getCurrencyExchangeRate_WithNonExistingCurrency_ShouldUpdateCurrency() {
        CurrencyId currencyId = CurrencyId.KZT;
        when(currencyRepository.findById(currencyId)).thenReturn(Optional.empty());

        CurrencyRate expectedRate = new CurrencyRate();
        expectedRate.setClose(BigDecimal.valueOf(1.1));
        expectedRate.setPreviousClose(BigDecimal.valueOf(1.05));
        expectedRate.setDatetime(LocalDate.now());
        when(httpClient.getExchangeRate(currencyId, CurrencyId.USD)).thenReturn(expectedRate);

        BigDecimal rate = currencyService.getCurrencyExchangeRate(currencyId);

        assertNotNull(rate);
        assertEquals(expectedRate.getClose(), rate);

        verify(currencyRepository).save(any(Currency.class));
    }

}
