package org.mtroyanov.repository;

import org.mtroyanov.entity.Category;
import org.mtroyanov.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDateTimeAfterAndCategoryAndAccountFrom(OffsetDateTime dateTimeBefore,
                                                                      Category category,
                                                                      Long accountId);
    List<Transaction> findAllByAccountFromAndExceededLimitIdNotNull(Long accountId);
}
