package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findTopByOrderByIdDesc();

    Boolean existsPaymentByCommitment_Id(Long id);

    @Query("SELECT COALESCE(SUM(p.value), 0) FROM Payment p WHERE p.commitment IN :commitments")
    BigDecimal sumPaymentsByCommitments(List<Commitment> commitments);

    @Query("SELECT COALESCE(SUM(p.value), 0) FROM Payment p WHERE p.commitment.id a = :id")
    BigDecimal sumPaymentsByCommitment(@Param(value = "id") Long commitmentId);

    Page<Payment> findPaymentsByCommitment_Id(Long id, PageRequest pageRequest);
}
