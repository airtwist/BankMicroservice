package org.mtroyanov.component;

import org.mtroyanov.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class ScheduledTaskService {
    private final CurrencyService currencyService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void updateExchangeRateDaily() {
        currencyService.updateExchangeRates();
    }
    
}