package org.mtroyanov.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Schema(description = "Курс валют")
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyRate {
    private String symbol;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal close;
    @JsonProperty("previous_close")
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal previousClose;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate datetime;
    private long timestamp;

}
