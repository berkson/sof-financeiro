package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    Expense findTopByOrderByIdDesc();

    @Query(value = "UPDATE Expense e SET e.status = :status WHERE e.id = :expenseId")
    @Modifying
    void saveStatus(@Param(value = "expenseId") Long expenseId, ExpenseStatus status);
}
