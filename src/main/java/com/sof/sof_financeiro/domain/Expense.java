package com.sof.sof_financeiro.domain;

import com.sof.sof_financeiro.enums.ExpenseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/

@Table(name = "expenses")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "expense_id"))
public class Expense extends BaseEntity {

    @Column(name = "protocol_number", unique = true, nullable = false)
    private String protocolNumber;
    @Column(name = "expense_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;
    @Column(name = "protocol_date")
    private LocalDateTime protocolDate;
    @Column(name = "expire_date")
    private LocalDate expireDate;
    @Column(nullable = false)
    private String creditor;
    private String description;
    @Column(nullable = false)
    private BigDecimal value;
    @OneToMany(mappedBy = "expense", fetch = FetchType.LAZY)
    private List<Commitment> commitment;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Expense exp)) return false;
        return Objects.equals(this.id, exp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
