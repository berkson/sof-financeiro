package com.sof.sof_financeiro.repository;

import com.sof.sof_financeiro.domain.Commitment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitmentRepository extends JpaRepository<Commitment, Long> {
}
