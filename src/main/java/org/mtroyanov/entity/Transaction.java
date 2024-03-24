package org.mtroyanov.entity;


import org.mtroyanov.entity.id.Category;
import org.mtroyanov.entity.id.CurrencyId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_from")
    private Long accountFrom;

    @Column(name = "account_to")
    private Long accountTo;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "sum_usd")
    private BigDecimal sumUsd;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "exceeded_limit_id")
    private Long exceededLimitId;

    @ManyToOne
    @JoinColumn(name = "exceeded_limit_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ExpenseLimit exceededLimit;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency currency;

    @Column(name = "currency_id")
    @Enumerated(EnumType.STRING)
    private CurrencyId currencyId;

    @Column(name = "date_time", columnDefinition = "DATETIME")
    private LocalDateTime dateTime;

    @Column(name = "timezone")
    private String timezone;

    public void setZoneDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime.toLocalDateTime();
        this.timezone = dateTime.getOffset().toString();
    }

    public OffsetDateTime getZoneDateTime() {
        return dateTime.atZone(ZoneId.of(timezone)).toOffsetDateTime();
    }
}