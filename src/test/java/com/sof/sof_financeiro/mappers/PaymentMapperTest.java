package com.sof.sof_financeiro.mappers;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Payment;
import com.sof.sof_financeiro.api.v1.model.PaymentDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class PaymentMapperTest {

    @Autowired
    PaymentMapper mapper;

    @Test
    void testPaymentToPaymentDto_mapsOnlyCommitmentId() {
        Commitment commitment = new Commitment();
        commitment.setId(100L);
        commitment.setCommitmentNumber("CMT-001");

        Payment payment = new Payment();
        payment.setPaymentNumber("PAY-001");
        payment.setPaymentDate(LocalDate.of(2025, 7, 27));
        payment.setValue(new BigDecimal("123.45"));
        payment.setNote("Pagamento teste");
        payment.setCommitment(commitment);

        PaymentDto dto = mapper.paymentToPaymentDto(payment);

        assertNotNull(dto);
        assertEquals("PAY-001", dto.getPaymentNumber());
        assertEquals(LocalDate.of(2025, 7, 27), dto.getPaymentDate());
        assertEquals(new BigDecimal("123.45"), dto.getValue());
        assertEquals("Pagamento teste", dto.getNote());
        assertEquals(100L, dto.getCommitmentId());

    }

    @Test
    void testPaymentDtoToPayment_buildsCommitmentWithOnlyId() {
        PaymentDto dto = new PaymentDto();
        dto.setPaymentNumber("PAY-002");
        dto.setPaymentDate(LocalDate.of(2025, 7, 28));
        dto.setValue(new BigDecimal("999.99"));
        dto.setNote("Pagamento 2");
        dto.setCommitmentId(200L);

        Payment entity = mapper.paymentDtoToPayment(dto);

        assertNotNull(entity);
        assertEquals("PAY-002", entity.getPaymentNumber());
        assertEquals(LocalDate.of(2025, 7, 28), entity.getPaymentDate());
        assertEquals(new BigDecimal("999.99"), entity.getValue());
        assertEquals("Pagamento 2", entity.getNote());
        assertNotNull(entity.getCommitment());
        assertEquals(200L, entity.getCommitment().getId());
    }
}