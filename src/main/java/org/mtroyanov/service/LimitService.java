package org.mtroyanov.service;

import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.ExpenseLimit;

public interface LimitService {
    ExpenseLimit updateLimit(Long accountId, LimitDto limitDto);



}
