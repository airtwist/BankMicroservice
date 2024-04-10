package org.mtroyanov.component;

import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.Account;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.service.AccountService;
import org.mtroyanov.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.mtroyanov.service.LimitService;
import org.springframework.data.domain.Limit;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Component
public class ScheduledTaskService {

    private final CurrencyService currencyService;


    @Scheduled(cron = "0 0 1 * * ?")
    public void updateExchangeRateDaily() {
        currencyService.updateExchangeRates();
    }

}