package org.mtroyanov.dto;

import org.mtroyanov.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@AllArgsConstructor
@Data
public class LimitDto {
    private Category category;
    private BigDecimal amount;
}
