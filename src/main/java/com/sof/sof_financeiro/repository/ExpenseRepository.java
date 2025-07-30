package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExpenseRepository extends JpaRepository<Expense, Long>, JpaSpecificationExecutor<Expense> {
    Expense findTopByOrderByIdDesc();
}
