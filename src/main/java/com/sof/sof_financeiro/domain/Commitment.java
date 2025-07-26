package com.sof.sof_financeiro.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * Created By : Berkson Ximenes
 * Date : 25/07/2025
 **/
@Entity
@Table(name = "commitments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "commitment_id"))
public class Commitment extends BaseEntity {
    @Column(name = "commitment_number", nullable = false, unique = true)
    private String commitmentNumber;
    @Column(name = "commitment_date", nullable = false)
    private LocalDate commitmentDate;
    @Column(nullable = false)
    private BigDecimal value;
    private String note;
    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;
    @OneToMany(mappedBy = "commitment", fetch = FetchType.LAZY)
    private List<Payment> payments;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Commitment commit)) return false;
        return Objects.equals(this.id, commit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
