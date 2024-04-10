package org.mtroyanov.service.impl;

import lombok.RequiredArgsConstructor;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.mapper.LimitMapper;
import org.mtroyanov.repository.LimitRepository;
import org.mtroyanov.service.LimitService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@RequiredArgsConstructor
@Service
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;

    @Override
    public ExpenseLimit updateLimit(Long accountId, LimitDto limitDto) {
        return limitRepository.save(LimitMapper.mapToExpenseLimit(limitDto,accountId));
    }

}
