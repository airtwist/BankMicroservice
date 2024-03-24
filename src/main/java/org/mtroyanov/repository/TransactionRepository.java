package org.mtroyanov.repository;

import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDateTimeAfterAndCategoryAndAccountFrom(LocalDateTime dateTimeBefore,
                                                                      Category category,
                                                                      Long accountId);
    List<Transaction> findAllByAccountFromAndExceededLimitIdNotNull(Long accountId);
}
