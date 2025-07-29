package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findTopByOrderByIdDesc();

    Boolean existsPaymentByCommitment_Id(Long id);
}
