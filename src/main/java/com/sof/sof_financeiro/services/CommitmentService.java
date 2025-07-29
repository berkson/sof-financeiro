package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.api.v1.model.CommitmentDto;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/

public interface CommitmentService extends BaseService<CommitmentDto, Long> {
    boolean existsByExpenseId(Long id);
}
