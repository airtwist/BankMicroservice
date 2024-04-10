package org.mtroyanov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.id.CurrencyId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
@Schema(description = "Сохранение транзакций")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDto {
    @Schema(description = "Банковский счет клиента")
    private Long accountFromId;
    @Schema(description = "Банковский счет контрагента")
    private Long accountToId;
    @Schema(description = "Валюта счета")
    private CurrencyId currencyId;
    @Schema(description = "Сумма транзакции")
    private BigDecimal sum;
    @Schema(description = "Категория расхода")
    private Category category;
    @Schema(description = "Время операции")
    private OffsetDateTime dateTime;
    @Schema(description = "Сумма лимита")
    private BigDecimal limitSum;
    @Schema(description = "Дата установки лимита")
    private OffsetDateTime limitDateTime;
    @Schema(description = "Валюта лимита")
    private CurrencyId limitCurrencyShortname;


}
