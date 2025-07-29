package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.api.v1.model.PaymentDto;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public interface PaymentService extends BaseService<PaymentDto, Long> {
    boolean existsByCommitmentId(Long id);
}
