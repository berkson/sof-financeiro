package com.sof.sof_financeiro.services;

import com.sof.sof_financeiro.domain.Commitment;
import com.sof.sof_financeiro.mappers.CommitmentMapper;
import com.sof.sof_financeiro.api.v1.model.CommitmentDto;
import com.sof.sof_financeiro.repository.CommitmentRepository;
import com.sof.sof_financeiro.util.NumberGeneratorUtil;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created By : Berkson Ximenes
 * Date : 28/07/2025
 **/
@Service
public class CommitmentServiceImpl implements CommitmentService {
    private final CommitmentMapper commitmentMapper;
    private final CommitmentRepository commitmentRepository;
    private final SetExpenseStatusService setExpenseStatusService;

    public CommitmentServiceImpl(CommitmentMapper commitmentMapper, CommitmentRepository commitmentRepository,
                                 SetExpenseStatusService setExpenseStatusService) {
        this.commitmentMapper = commitmentMapper;
        this.commitmentRepository = commitmentRepository;
        this.setExpenseStatusService = setExpenseStatusService;
    }

    @Override
    public CommitmentDto save(CommitmentDto entity) {
        Commitment commitment = commitmentMapper.commitmentDtoToCommitment(entity);
        if (commitment.getId() == null) {
            Commitment lastCommitment = commitmentRepository.findTopByOrderByIdDesc();
            String lastNumber = lastCommitment != null ? lastCommitment.getCommitmentNumber() : "";
            commitment.setCommitmentNumber(NumberGeneratorUtil.getNextNumber("NE", lastNumber));
        }
        Commitment savedCommit = commitmentRepository.save(commitment);
        setExpenseStatusService.checkAndSetStatus(commitment.getExpense().getId());
        return commitmentMapper.commitmentToCommitmentDto(savedCommit);
    }

    @Override
    public Optional<CommitmentDto> getById(Long id) {
        return commitmentRepository.findById(id)
                .map(commitmentMapper::commitmentToCommitmentDto);
    }

    @Override
    public void delete(Long id) {
        commitmentRepository.deleteById(id);
    }

    @Override
    public boolean existsByExpenseId(Long id) {
        return commitmentRepository.existsCommitmentByExpense_Id(id);
    }
}
