package org.mtroyanov.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.mtroyanov.dto.LimitDto;
import org.mtroyanov.dto.UpdateLimitDto;
import org.mtroyanov.service.LimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag( name = "Лимиты",
        description = "Обновление лимитов"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/client-api/limits")
public class LimitController {
    private final LimitService limitService;

    @PutMapping("/update-limits")
    public void updateLimit(@RequestBody UpdateLimitDto updateLimitDto){
        for (LimitDto limit : updateLimitDto.getLimits()) {
            limitService.updateLimit(updateLimitDto.getAccountId(),limit);
        }
    }

}
