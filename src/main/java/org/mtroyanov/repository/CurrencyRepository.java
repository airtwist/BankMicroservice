package org.mtroyanov.repository;

import org.mtroyanov.entity.Currency;
import org.mtroyanov.entity.id.CurrencyId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, CurrencyId> {

}
