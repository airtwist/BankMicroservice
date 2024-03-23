package org.mtroyanov.dto;

import lombok.Data;

import java.util.List;
@Data
public class UpdateLimitDto {
    private Long accountId;
    private List<LimitDto> limits;

}
