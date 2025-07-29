package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.enums.ExpenseType;
import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import com.sof.sof_financeiro.api.v1.model.ExpenseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class ExpenseMapperTest {

    @Autowired
    ExpenseMapper mapper;

    @Test
    void testExpenseDtoToExpense_withCommitments() {
        CommitmentDto commitment1 = new CommitmentDto();
        commitment1.setCommitmentNumber("CMT-001");
        commitment1.setCommitmentDate(LocalDate.of(2025, 7, 20));
        commitment1.setValue(new BigDecimal("1000.00"));
        commitment1.setNote("Primeiro compromisso");

        CommitmentDto commitment2 = new CommitmentDto();
        commitment2.setCommitmentNumber("CMT-002");
        commitment2.setCommitmentDate(LocalDate.of(2025, 7, 25));
        commitment2.setValue(new BigDecimal("2000.00"));
        commitment2.setNote("Segundo compromisso");

        ExpenseDto dto = new ExpenseDto();
        dto.setProtocolNumber("EXP-123");
        dto.setExpenseType(ExpenseType.OTHERS);
        dto.setProtocolDate(LocalDateTime.of(2025, 7, 1, 10, 0));
        dto.setExpireDate(LocalDate.of(2025, 8, 1));
        dto.setCreditor("Fornecedor Z");
        dto.setDescription("Despesa com compromissos");
        dto.setCommitments(List.of(commitment1, commitment2));

        Expense expense = mapper.expenseDtoToExpense(dto);

        assertNotNull(expense);
        assertEquals(2, expense.getCommitments().size());

        Commitment c1 = expense.getCommitments().get(0);
        Commitment c2 = expense.getCommitments().get(1);

        assertEquals("CMT-001", c1.getCommitmentNumber());
        assertEquals(new BigDecimal("1000.00"), c1.getValue());
        assertEquals("Primeiro compromisso", c1.getNote());

        assertEquals("CMT-002", c2.getCommitmentNumber());
        assertEquals(new BigDecimal("2000.00"), c2.getValue());
        assertEquals("Segundo compromisso", c2.getNote());
    }

    @Test
    void testExpenseToExpenseDto_withCommitments() {
        Commitment commitment1 = new Commitment();
        commitment1.setCommitmentNumber("CMT-100");
        commitment1.setCommitmentDate(LocalDate.of(2025, 6, 10));
        commitment1.setValue(new BigDecimal("3000.00"));
        commitment1.setNote("Pagamento inicial");

        Commitment commitment2 = new Commitment();
        commitment2.setCommitmentNumber("CMT-101");
        commitment2.setCommitmentDate(LocalDate.of(2025, 6, 15));
        commitment2.setValue(new BigDecimal("4500.00"));
        commitment2.setNote("Pagamento final");

        Expense expense = new Expense();
        expense.setProtocolNumber("EXP-999");
        expense.setExpenseType(ExpenseType.ROAD_PROJECT);
        expense.setProtocolDate(LocalDateTime.of(2025, 5, 5, 9, 30));
        expense.setExpireDate(LocalDate.of(2025, 7, 1));
        expense.setCreditor("Fornecedor Y");
        expense.setDescription("Despesa com dois pagamentos");
        expense.setCommitments(List.of(commitment1, commitment2));

        ExpenseDto dto = mapper.expenseToExpenseDto(expense);

        assertNotNull(dto);
        assertEquals(2, dto.getCommitments().size());

        CommitmentDto c1 = dto.getCommitments().get(0);
        CommitmentDto c2 = dto.getCommitments().get(1);

        assertEquals("CMT-100", c1.getCommitmentNumber());
        assertEquals(new BigDecimal("3000.00"), c1.getValue());
        assertEquals("Pagamento inicial", c1.getNote());

        assertEquals("CMT-101", c2.getCommitmentNumber());
        assertEquals(new BigDecimal("4500.00"), c2.getValue());
        assertEquals("Pagamento final", c2.getNote());
    }

}