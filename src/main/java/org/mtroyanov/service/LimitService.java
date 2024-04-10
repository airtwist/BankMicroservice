package org.mtroyanov.service;

import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.entity.ExpenseLimit;
import org.mtroyanov.entity.id.Category;

public interface LimitService {
    ExpenseLimit updateLimit(Long accountId, LimitDto limitDto);


}
