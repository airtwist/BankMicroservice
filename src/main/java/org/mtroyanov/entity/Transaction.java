package org.mtroyanov.entity;


import org.mtroyanov.entity.id.CurrencyId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
    private BigDecimal transactionSumm;

    @Column(name = "sum_usd")
    private BigDecimal transactionSumInUsd;
    @Column(name = "expense_category")
    private Category category;

    @Column(name = "exceeded_limit_id")
    private Long exceededLimitId;
    @ManyToOne
    @JoinColumn(name = "exceeded_limit_id" ,referencedColumnName = "id", insertable = false, updatable = false)
    private ExpenseLimit exceededLimit;
    //Чтобы нам не приходилось доставать Currency из базы данных,
    //При создании транзакции мы сможем впихнуть CunrrencyId из ENUM
    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Currency currency;

    @Column(name = "currency_id")
    @Enumerated(EnumType.STRING)
    private CurrencyId currencyId;

    @Column(name = "datetime", columnDefinition = "DATETIME")
    private OffsetDateTime dateTime;

}
