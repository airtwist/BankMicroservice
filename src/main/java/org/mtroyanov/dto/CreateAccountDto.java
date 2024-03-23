package org.mtroyanov.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class CreateAccountDto {
    private List<LimitDto> limits;
}
