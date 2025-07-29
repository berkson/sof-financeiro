package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.services.CommitmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created By : Berkson Ximenes
 * Date : 29/07/2025
 **/

public class ExpenseCanBeExcludedValidatorTest {

    private ExpenseCanBeExcludedValidator validator;

    @Mock
    private CommitmentService commitmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new ExpenseCanBeExcludedValidator();
        validator.setCommitmentService(commitmentService);
    }

    @Test
    void shouldReturnTrue_WhenNoCommitmentExistsForExpense() {
        // given
        Long expenseId = 1L;
        when(commitmentService.existsByExpenseId(expenseId)).thenReturn(false);

        // when
        boolean result = validator.isValid(expenseId, null);

        // then
        assertThat(result).isTrue();
        verify(commitmentService).existsByExpenseId(expenseId);
    }

    @Test
    void shouldReturnFalse_WhenCommitmentExistsForExpense() {
        // given
        Long expenseId = 2L;
        when(commitmentService.existsByExpenseId(expenseId)).thenReturn(true);

        // when
        boolean result = validator.isValid(expenseId, null);

        // then
        assertThat(result).isFalse();
        verify(commitmentService).existsByExpenseId(expenseId);
    }

    @Test
    void shouldReturnFalse_WhenExpenseIdIsNull() {
        // when
        boolean result = validator.isValid(null, null);

        // then
        assertThat(result).isFalse();
        verifyNoInteractions(commitmentService);
    }
}