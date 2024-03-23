package org.mtroyanov.service.impl;

import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;
    @Override
    public ExpenseLimit updateLimit(Long accountId, LimitDto limitDto) {
        ExpenseLimit newExpenseLimit = new ExpenseLimit();
        newExpenseLimit.setAmount(limitDto.getAmount());
        newExpenseLimit.setDateTime(OffsetDateTime.now(ZoneId.systemDefault()));
        newExpenseLimit.setCategory(limitDto.getCategory());
        newExpenseLimit.setAccountId(accountId);
        return limitRepository.save(newExpenseLimit);
    }
}
