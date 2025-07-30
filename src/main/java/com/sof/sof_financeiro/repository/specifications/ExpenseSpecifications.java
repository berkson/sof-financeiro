package com.sof.sof_financeiro.repository.specifications;

import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import com.sof.sof_financeiro.enums.ExpenseType;
import org.springframework.data.jpa.domain.Specification;

/**
 * Created By : Berkson Ximenes
 * Date : 29/07/2025
 **/

public class ExpenseSpecifications {
    public static Specification<Expense> hasExpenseType(ExpenseType expenseType) {
        return (root, query, cb) -> expenseType == null
                ? cb.conjunction()
                : cb.equal(root.get("expenseType"), expenseType);
    }

    public static Specification<Expense> hasStatus(ExpenseStatus status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }
}
