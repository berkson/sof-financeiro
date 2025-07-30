package com.sof.sof_financeiro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "payment_id"))
public class Payment extends BaseEntity {
    @Column(name = "payment_number", nullable = false, unique = true)
    private String paymentNumber;
    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;
    private String note;
    @ManyToOne
    @JoinColumn(name = "commitment_id")
    private Commitment commitment;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Payment pay)) return false;
        return Objects.equals(this.id, pay.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}


