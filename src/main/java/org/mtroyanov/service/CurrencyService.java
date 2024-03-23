package org.mtroyanov.service;

import org.mtroyanov.entity.id.CurrencyId;

import java.math.BigDecimal;

public interface CurrencyService {
    void updateExchangeRates();

    BigDecimal getCurrencyExchangeRate(CurrencyId currencyId);
}
