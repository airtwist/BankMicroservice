package org.mtroyanov.entity;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


//    @Column(name = "product_amount", nullable = false,columnDefinition = "DECIMAL(10,2) default '0.00'")
//    private BigDecimal productAmount = BigDecimal.ZERO;
//
//    @Column(name = "service_amount", nullable = false,columnDefinition = "DECIMAL(10,2) default '0.00'")
//    private BigDecimal serviceAmount = BigDecimal.ZERO;


}


