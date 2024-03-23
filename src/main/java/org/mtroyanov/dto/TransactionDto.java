package org.mtroyanov.dto;

import org.mtroyanov.entity.Category;
import org.mtroyanov.entity.id.CurrencyId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class TransactionDto {

    private Long accountFromId;

    private Long accountToId;

    private CurrencyId currencyId;

    private BigDecimal sum;

    private Category category;

    private OffsetDateTime dateTime;

    private BigDecimal limitSum;

    private OffsetDateTime limitDateTime;

    private CurrencyId limitCurrencyShortname;
}
