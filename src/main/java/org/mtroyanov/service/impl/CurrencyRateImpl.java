package org.mtroyanov.service.impl;

import org.mtroyanov.api.HttpClient;
import org.mtroyanov.dto.CurrencyRate;
import org.mtroyanov.entity.Currency;
import org.mtroyanov.entity.id.CurrencyId;
import org.mtroyanov.mapper.CurrencyMapper;
import org.mtroyanov.repository.CurrencyRepository;
import org.mtroyanov.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
public class CurrencyRateImpl implements CurrencyService {
    private final HttpClient httpClient;

    private final CurrencyRepository currencyRepository;

    public CurrencyRateImpl(CurrencyRepository currencyRepository, HttpClient httpClient) {
        this.currencyRepository = currencyRepository;
        this.httpClient = httpClient;
    }

    @Override
    public void updateExchangeRates() {
        Arrays.stream(CurrencyId.values())
                .map(currencyId -> currencyRepository
                        .findById(currencyId)
                        .orElse(new Currency(currencyId)))
                .forEach(this::updateCurrency);
    }

    @Override
    public BigDecimal getCurrencyExchangeRate(CurrencyId currencyId) {
        if (currencyId.equals(CurrencyId.USD)) {
            return BigDecimal.ONE;
        }
        Currency currency = currencyRepository.findById(currencyId)
                .orElseGet(() -> updateCurrency(new Currency(currencyId)));
        if (currency.getClose() == null) {
            updateCurrency(currency);
        }
        return currency.getClose();
    }

    private Currency updateCurrency(Currency currency) {
        CurrencyRate newCurrencyRate = httpClient.getExchangeRate(currency.getId(), CurrencyId.USD);
        currency = CurrencyMapper.mapToCurrency(newCurrencyRate);
        currencyRepository.save(currency);
        return currency;
    }
}
