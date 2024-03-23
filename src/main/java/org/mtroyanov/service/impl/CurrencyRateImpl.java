package org.mtroyanov.service.impl;

import org.mtroyanov.api.HttpClient;
import org.mtroyanov.dto.CurrencyRate;
import org.mtroyanov.entity.Currency;
import org.mtroyanov.entity.id.CurrencyId;
import org.mtroyanov.repository.CurrencyRepository;
import org.mtroyanov.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CurrencyRateImpl implements CurrencyService {
    private HttpClient httpClient;

    private CurrencyRepository currencyRepository;

    public CurrencyRateImpl(CurrencyRepository currencyRepository, HttpClient httpClient) {
        this.currencyRepository = currencyRepository;
        this.httpClient = httpClient;
    }

    @Override
    public void updateExchangeRates() {
        for (CurrencyId currencyId : CurrencyId.values()) {
            Currency currency = currencyRepository.findById(currencyId)
                    .orElse(new Currency(currencyId));
            updateCurrency(currency);
        }
    }
    @Override
    public BigDecimal getCurrencyExchangeRate(CurrencyId currencyId) {
        if (currencyId.equals(CurrencyId.USD)) {
            return BigDecimal.ONE;
        }
        return currencyRepository.findById(currencyId)
                .orElseGet(() -> updateCurrency(new Currency(currencyId)))
                .getClose();
    }

    private Currency updateCurrency(Currency currency) {
        CurrencyRate newCurrencyRate = httpClient.getExchangeRate(CurrencyId.USD, currency.getId());
        currency.setClose(newCurrencyRate.getClose());
        currency.setPreviousClose(newCurrencyRate.getPreviousClose());
        currency.setExchangeDate(newCurrencyRate.getDatetime());
        currencyRepository.save(currency);
        return currency;
    }
}
