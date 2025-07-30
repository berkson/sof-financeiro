package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import com.sof.sof_financeiro.enums.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public interface ExpenseService extends BaseService<ExpenseDto, Long> {
    Page<ExpenseDto> getExpensesFiltered(ExpenseType type, ExpenseStatus status, Pageable pageable);
}
