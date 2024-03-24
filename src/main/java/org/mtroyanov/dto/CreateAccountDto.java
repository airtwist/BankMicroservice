package org.mtroyanov.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Создание аккаунта")
@Data
@NoArgsConstructor


public class CreateAccountDto {
    private List<LimitDto> limits = new ArrayList<>();

}
