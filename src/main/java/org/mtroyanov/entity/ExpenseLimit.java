package org.mtroyanov.entity;

import org.mtroyanov.entity.id.CurrencyId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

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

    @ManyToOne
    @JoinColumn(name="currency_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Currency currency;

    @Column(name = "currency_id")
    @Enumerated(EnumType.STRING)
    private CurrencyId currencyId;

    @Column(name = "category")
    private Category category;
    @Column(name = "datetime", columnDefinition = "DATETIME")
    private OffsetDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id",insertable = false,updatable = false)
    private Account account;

    @Column(name = "account_id")
    private Long accountId;

}