package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.mappers.ExpenseMapper;
import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import com.sof.sof_financeiro.repository.ExpenseRepository;
import com.sof.sof_financeiro.util.NumberGeneratorUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final SetPaymentStatusService setPaymentStatusService;
    private final ExpenseMapper expenseMapper;
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(SetPaymentStatusService setPaymentStatusService,
                              ExpenseMapper expenseMapper, ExpenseRepository expenseRepository) {
        this.setPaymentStatusService = setPaymentStatusService;
        this.expenseMapper = expenseMapper;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public ExpenseDto save(ExpenseDto entity) {
        Expense expense = expenseMapper.expenseDtoToExpense(entity);
        if (expense.getId() == null) {
            Expense lastExpense = expenseRepository.findTopByOrderByIdDesc();
            String lastProtocol = lastExpense != null ? lastExpense.getProtocolNumber() : "";
            expense.setProtocolNumber(NumberGeneratorUtil.getNextProtocol(lastProtocol));
        }
        setPaymentStatusService.checkAndSetStatus(expense);
        Expense savedExpense = expenseRepository.save(expense);
        return expenseMapper.expenseToExpenseDto(savedExpense);
    }

    @Override
    public Optional<ExpenseDto> getById(Long id) {
        return expenseRepository.findById(id)
                .map(expenseMapper::expenseToExpenseDto);
    }

    @Override
    public void delete(Long id) {
        //TODO: fazer validação
        expenseRepository.deleteById(id);
    }
}
