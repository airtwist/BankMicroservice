package org.mtroyanov.dto;

import org.mtroyanov.entity.Category;
import org.mtroyanov.entity.id.CurrencyId;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateTransactionDto {

    private Long accountFromId;

    private Long accountToId;

    private BigDecimal sum;

    private Category category;

    private CurrencyId currencyId;

}
