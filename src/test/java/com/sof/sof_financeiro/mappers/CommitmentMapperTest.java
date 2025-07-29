package com.sof.sof_financeiro.mappers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Expense;
import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.enums.ExpenseType;
import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import org.hamcrest.CoreMatchers;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class CommitmentMapperTest {

    @Autowired
    CommitmentMapper mapper;

    ObjectMapper objectMapper = objectMapper = new ObjectMapper()
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            .registerModules(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
            .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false)
            .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

    @Test
    void testCommitmentDtoToCommitment() {
        CommitmentDto dto = new CommitmentDto();
        dto.setCommitmentNumber("CMT-001");
        dto.setCommitmentDate(LocalDate.of(2025, 7, 20));
        dto.setNote("Compromisso de teste");
        dto.setValue(new BigDecimal("1234.56"));

        Commitment entity = mapper.commitmentDtoToCommitment(dto);

        assertNotNull(entity);
        assertEquals("CMT-001", entity.getCommitmentNumber());
        assertEquals(LocalDate.of(2025, 7, 20), entity.getCommitmentDate());
        assertEquals("Compromisso de teste", entity.getNote());
        assertEquals(new BigDecimal("1234.56"), entity.getValue());
    }

    @Test
    void testCommitmentToCommitmentDto() {
        Commitment entity = new Commitment();
        entity.setCommitmentNumber("CMT-002");
        entity.setCommitmentDate(LocalDate.of(2025, 8, 15));
        entity.setNote("Outro compromisso de teste");
        entity.setValue(new BigDecimal("7890.12"));

        CommitmentDto dto = mapper.commitmentToCommitmentDto(entity);

        assertNotNull(dto);
        assertEquals("CMT-002", dto.getCommitmentNumber());
        assertEquals(LocalDate.of(2025, 8, 15), dto.getCommitmentDate());
        assertEquals("Outro compromisso de teste", dto.getNote());
        assertEquals(new BigDecimal("7890.12"), dto.getValue());
    }

    @Test
    void testCommitmentToCommitmentDto_withExpenseAndPayments() throws JsonProcessingException {
        // Criando a entidade Commitment
        Commitment commitment = new Commitment();
        commitment.setId(10L);
        commitment.setCommitmentNumber("CMT-LOOP");
        commitment.setCommitmentDate(LocalDate.of(2025, 7, 25));
        commitment.setNote("Teste de loop");
        commitment.setValue(new BigDecimal("5000.00"));

        // Criando Expense e vinculando ao Commitment
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setProtocolNumber("EXP-LOOP");
        expense.setExpenseType(ExpenseType.OTHERS);
        expense.setProtocolDate(LocalDateTime.of(2025, 7, 1, 12, 0));
        expense.setExpireDate(LocalDate.of(2025, 8, 1));
        expense.setCreditor("Fornecedor Loop");
        expense.setDescription("Despesa com referência circular");
        expense.setCommitments(List.of(commitment));

        commitment.setExpense(expense);

        // Criando Payments e vinculando ao Commitment
        Payment payment1 = new Payment();
        payment1.setId(100L);
        payment1.setPaymentNumber("PAY-001");
        payment1.setPaymentDate(LocalDate.of(2025, 7, 26));
        payment1.setValue(new BigDecimal("2500.00"));
        payment1.setNote("Parcela 1");
        payment1.setCommitment(commitment);

        Payment payment2 = new Payment();
        payment2.setId(101L);
        payment2.setPaymentNumber("PAY-002");
        payment2.setPaymentDate(LocalDate.of(2025, 7, 27));
        payment2.setValue(new BigDecimal("2500.00"));
        payment2.setNote("Parcela 2");
        payment2.setCommitment(commitment);

        commitment.setPayments(List.of(payment1, payment2));

        // Faz o mapeamento
        CommitmentDto dto = mapper.commitmentToCommitmentDto(commitment);

        // Asserts
        assertNotNull(dto);
        assertEquals("CMT-LOOP", dto.getCommitmentNumber());
        assertEquals(LocalDate.of(2025, 7, 25), dto.getCommitmentDate());
        assertEquals("Teste de loop", dto.getNote());
        assertEquals(new BigDecimal("5000.00"), dto.getValue());

        // Verifica se o Expense foi mapeado (sem commitments)
        assertNotNull(dto.getExpense());
        assertThat(dto.getExpense().getCommitments(), CoreMatchers.is(IsEmptyCollection.empty())); // loop evitado

        // Verifica se os payments foram mapeados
        assertNotNull(dto.getPayments());
        assertEquals(2, dto.getPayments().size());

        PaymentDto p1 = dto.getPayments().get(0);
        PaymentDto p2 = dto.getPayments().get(1);

        assertEquals("PAY-001", p1.getPaymentNumber());
        assertEquals(new BigDecimal("2500.00"), p1.getValue());
        assertEquals("Parcela 1", p1.getNote());
        assertEquals(10L, p1.getCommitmentId()); // só ID

        assertEquals("PAY-002", p2.getPaymentNumber());
        assertEquals(new BigDecimal("2500.00"), p2.getValue());
        assertEquals("Parcela 2", p2.getNote());
        assertEquals(10L, p2.getCommitmentId());

        System.out.printf(objectMapper.writeValueAsString(dto));

    }
}