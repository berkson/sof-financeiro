package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.enums.ExpenseStatus;
import com.sof.sof_financeiro.repository.CommitmentRepository;
import com.sof.sof_financeiro.repository.ExpenseRepository;
import com.sof.sof_financeiro.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/
@Service
public class SetExpenseStatusService {
    private final CommitmentRepository commitmentRepository;
    private final PaymentRepository paymentRepository;
    private final ExpenseRepository expenseRepository;

    public SetExpenseStatusService(CommitmentRepository commitmentRepository, PaymentRepository paymentRepository, ExpenseRepository expenseRepository) {
        this.commitmentRepository = commitmentRepository;
        this.paymentRepository = paymentRepository;
        this.expenseRepository = expenseRepository;
    }

    public void checkAndSetStatus(Expense expense) {
        if (expense.getId() == null) {
            expense.setStatus(ExpenseStatus.WAITING_COMMITMENT);
            return;
        }
        BigDecimal commitmentSum = commitmentRepository.sumCommitmentsByExpense(expense.getId());
        int cmp = commitmentSum.compareTo(expense.getValue());

        if (commitmentSum.compareTo(BigDecimal.ZERO) == 0) {
            changeStatus(expense.getId(), ExpenseStatus.WAITING_COMMITMENT);
            expense.setStatus(ExpenseStatus.WAITING_COMMITMENT);
            return;
        }

        if (cmp < 0) {
            changeStatus(expense.getId(), ExpenseStatus.PARTIAL_COMMITMENT);
            expense.setStatus(ExpenseStatus.PARTIAL_COMMITMENT);
            return;
        }

        // valor da soma dos empenhos é igual ao valor da despesa
        if (cmp == 0) {
            List<Commitment> commitments = commitmentRepository.findCommitmentsByExpense_Id(expense.getId());
            BigDecimal paymentSum = paymentRepository.sumPaymentsByCommitments(commitments);
            int paymentCmp = paymentSum.compareTo(BigDecimal.ZERO);

            // Não há registro de pagamentos
            if (paymentCmp == 0) {
                changeStatus(expense.getId(), ExpenseStatus.WAITING_PAYMENT);
                expense.setStatus(ExpenseStatus.WAITING_PAYMENT);
            } else if (paymentSum.compareTo(expense.getValue()) < 0) {
                changeStatus(expense.getId(), ExpenseStatus.PARTIAL_PAYMENT);
                expense.setStatus(ExpenseStatus.PARTIAL_PAYMENT);
            } else if (paymentSum.compareTo(expense.getValue()) == 0) {
                changeStatus(expense.getId(), ExpenseStatus.PAID);
                expense.setStatus(ExpenseStatus.PAID);
            }
        }

    }

    private void changeStatus(Long expenseId, ExpenseStatus status) {
        expenseRepository.saveStatus(expenseId, status);
    }
}
