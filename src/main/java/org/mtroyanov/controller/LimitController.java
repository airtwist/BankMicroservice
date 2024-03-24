package org.mtroyanov.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.dto.UpdateLimitDto;
import org.mtroyanov.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag( name = "Лимиты",
        description = "Обновление лимитов"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/client-api/limits")
public class LimitController {
    private final LimitService limitService;

    @PostMapping("/update-limits")
    public void updateLimit(@RequestBody UpdateLimitDto updateLimitDto){
        for (LimitDto limit : updateLimitDto.getLimits()) {
            limitService.updateLimit(updateLimitDto.getAccountId(),limit);
        }
    }

}
