package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
    Commitment findTopByOrderByIdDesc();
    Boolean existsCommitmentByExpense_Id(Long id);
    List<Commitment> findCommitmentsByExpense_Id(Long id);

    @Query("SELECT COALESCE(SUM(c.value), 0) FROM Commitment c WHERE c.expense.id = :expenseId")
    BigDecimal sumCommitmentsByExpense(@Param(value = "expenseId") Long expenseId);
}
