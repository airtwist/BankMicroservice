package org.mtroyanov.repository;

import org.mtroyanov.entity.Category;
import org.mtroyanov.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitRepository extends JpaRepository<ExpenseLimit, Long> {

    ExpenseLimit getExpenseLimitByAccountIdAndCategory(Long accountId, Category category);

}
