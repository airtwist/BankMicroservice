package org.mtroyanov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Schema(description = "Обновление лимитов")
@NoArgsConstructor
@Data
public class UpdateLimitDto {
    @Schema(description = "Аккаунт")
    private Long accountId;
    @Schema(description = "Список из лимитов")
    private List<LimitDto> limits;

}
