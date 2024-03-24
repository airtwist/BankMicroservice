package org.mtroyanov.entity;

import org.mtroyanov.entity.id.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class ExpenseLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount", nullable = false,columnDefinition = "DECIMAL(10,2) default '1000.00'")
    private BigDecimal amount = new BigDecimal("1000.00");

//    @ManyToOne
//    @JoinColumn(name="currency_id",referencedColumnName = "id",insertable = false,updatable = false)
//    private Currency currency;
//
//    @Column(name = "currency_id")
//    @Enumerated(EnumType.STRING)
//    private CurrencyId currencyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private Category category;
    @Column(name = "date_time")
    private LocalDateTime dateTime;
    @Column(name = "timezone")
    private String timezone;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id",insertable = false,updatable = false)
    private Account account;

    @Column(name = "account_id")
    private Long accountId;

    public void setZoneDateTime(OffsetDateTime dateTime) {
        this.dateTime = dateTime.toLocalDateTime();
        this.timezone = dateTime.getOffset().toString();
    }
    public OffsetDateTime getZoneDateTime() {
        return dateTime.atZone(ZoneId.of(timezone)).toOffsetDateTime();
    }
}