package com.group2.library_management.service.impl;

import org.springframework.stereotype.Service;

import com.group2.library_management.dto.response.EditionDetailResponse;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.dto.response.EditionResponse;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.enums.DeletionStatus;
import com.group2.library_management.exception.OperationFailedException;
import com.group2.library_management.exception.ResourceNotFoundException;
import com.group2.library_management.repository.BookInstanceRepository;
import com.group2.library_management.repository.EditionRepository;
import com.group2.library_management.service.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.group2.library_management.dto.mapper.EditionMapper;

@Service
@RequiredArgsConstructor
public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;
    private final EditionMapper editionMapper;

    private final BookInstanceRepository bookInstanceRepository;

    @Override
    public Page<EditionListResponse> getAllEditions(Pageable pageable) {
        Page<Edition> editionsPage = editionRepository.findAll(pageable);
        return editionsPage.map(editionMapper::toDto);
    }

    @Override
    public List<EditionResponse> getEditionsByBookId(Integer bookId) {
        // Get editions by book ID
        List<Edition> editions = editionRepository.findByBookIdOrderByPublicationDateDesc(bookId);

        // Use stream to process
        return editions.stream()
                .map(edition -> {
                    // Determine deletion status
                    DeletionStatus status = determineDeletionStatus(edition.getId());
                    // Call editionMapper.toResponseDto
                    return editionMapper.toResponseDto(edition, status);
                })
                .collect(Collectors.toList());
    }

    // Delete edition by ID
    @Override
    @Transactional
    public void deleteEdition(Integer id) {

        if (!editionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Edition not found with id: " + id);
        }

        DeletionStatus status = determineDeletionStatus(id);

        switch (status) {
            case CAN_HARD_DELETE:
                // Hard delete form database
                editionRepository.hardDeleteById(id);
                break;
            case CAN_SOFT_DELETE:
                // Soft delete
                editionRepository.deleteById(id);
                break;
            case CANNOT_DELETE:
                throw new OperationFailedException("admin.editions.message.cannot_delete");
        }
    }

    // Method to determine deletion status
    private DeletionStatus determineDeletionStatus(Integer editionId) {
        boolean anyInstanceExists = bookInstanceRepository.existsByEditionId(editionId);
        
        if (!anyInstanceExists) {
            // 0 copies exist -> can hard delete
            return DeletionStatus.CAN_HARD_DELETE;
        }

        boolean activeInstanceExists = bookInstanceRepository.existsByEditionIdAndDeleteAtIsNull(editionId);
        
        if (!activeInstanceExists) {
            // Have copies but all are soft deleted -> soft delete
            return DeletionStatus.CAN_SOFT_DELETE;
        }
        return DeletionStatus.CANNOT_DELETE;
    }

    @Override
    public EditionDetailResponse getEditionDetailById(Integer id) {
        return editionRepository.findById(id)
                .map(editionMapper::toDetailDto)
                .orElse(null);
    }
}
