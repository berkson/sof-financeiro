package com.sof.sof_financeiro.validations.validators;

import com.sof.sof_financeiro.services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Created By : Berkson Ximenes
 * Date : 29/07/2025
 **/

public class CommitmentCanBeExcludedValidatorTest {

    private CommitmentCanBeExcludedValidator validator;

    @Mock
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new CommitmentCanBeExcludedValidator();
        validator.setPaymentService(paymentService);
    }

    @Test
    void shouldReturnTrue_WhenNoCommitmentExistsForExpense() {
        // given
        Long expenseId = 1L;
        when(paymentService.existsByCommitmentId(expenseId)).thenReturn(false);

        // when
        boolean result = validator.isValid(expenseId, null);

        // then
        assertThat(result).isTrue();
        verify(paymentService).existsByCommitmentId(expenseId);
    }

    @Test
    void shouldReturnFalse_WhenCommitmentExistsForExpense() {
        // given
        Long expenseId = 2L;
        when(paymentService.existsByCommitmentId(expenseId)).thenReturn(true);

        // when
        boolean result = validator.isValid(expenseId, null);

        // then
        assertThat(result).isFalse();
        verify(paymentService).existsByCommitmentId(expenseId);
    }

    @Test
    void shouldReturnFalse_WhenExpenseIdIsNull() {
        // when
        boolean result = validator.isValid(null, null);

        // then
        assertThat(result).isFalse();
        verifyNoInteractions(paymentService);
    }
}