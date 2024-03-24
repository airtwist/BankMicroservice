package org.mtroyanov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import org.mtroyanov.entity.id.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Schema(description = "Сохранение лимитов")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LimitDto {
    @Schema(description = "Категория лимита")
    private Category category;
    @Schema(description = "Сумма лимита")
    private BigDecimal amount;
}
