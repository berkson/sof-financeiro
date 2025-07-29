package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {CommitmentMapper.class})
public interface ExpenseMapper {
    Expense expenseDtoToExpense(ExpenseDto expenseDto);

    ExpenseDto expenseToExpenseDto(Expense expense);
}
