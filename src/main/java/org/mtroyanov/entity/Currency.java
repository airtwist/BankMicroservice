package org.mtroyanov.entity;

import org.mtroyanov.entity.id.CurrencyId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor

@Getter
@Setter
@Entity
public class Currency {
    @Id
    @Enumerated(EnumType.STRING)
    private CurrencyId id;

    @Column(name = "close")
    private BigDecimal close;

    @Column(name = "previous_close")
    private BigDecimal previousClose;

    @Column(name = "exchange_date")
    private LocalDate exchangeDate;

    public Currency(CurrencyId id) {
        this.id = id;
    }

}
