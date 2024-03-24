package org.mtroyanov.repository;

import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.ExpenseLimit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitRepository extends JpaRepository<ExpenseLimit, Long> {

    ExpenseLimit findFirstByAccountIdAndCategoryOrderByDateTime(Long accountId, Category category);

}
