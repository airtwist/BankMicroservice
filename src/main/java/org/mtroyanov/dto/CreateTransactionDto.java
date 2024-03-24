package org.mtroyanov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.id.CurrencyId;
import lombok.Data;

import java.math.BigDecimal;
@Schema(description = "Создание тразакции")
@NoArgsConstructor
@Data
public class CreateTransactionDto {
    @Schema(description = "Банковский счет клиента")
    private Long accountFromId;
    @Schema(description = "Банковский счет контрагента")
    private Long accountToId;
    @Schema(description = "Сумма транзакции")
    private BigDecimal sum;
    @Schema(description = "Категория расхода")
    private Category category;
    @Schema(description = "Валюта счета")
    private CurrencyId currencyId;

}
