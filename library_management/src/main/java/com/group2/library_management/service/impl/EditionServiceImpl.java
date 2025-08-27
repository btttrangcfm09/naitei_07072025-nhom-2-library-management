package com.group2.library_management.service.impl;

import org.springframework.stereotype.Service;
import com.group2.library_management.dto.response.EditionDetailResponse;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.dto.response.EditionResponse;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.enums.DeletionStatus;
import com.group2.library_management.exception.OperationFailedException;
import org.springframework.web.multipart.MultipartFile;

import com.group2.library_management.dto.response.EditionDetailResponse;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.dto.response.EditionResponse;
import com.group2.library_management.dto.response.EditionUpdateResponse;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.entity.Publisher;
import com.group2.library_management.exception.DuplicateIsbnException;
import com.group2.library_management.exception.ResourceNotFoundException;
import com.group2.library_management.repository.BookInstanceRepository;
import com.group2.library_management.repository.EditionRepository;
import com.group2.library_management.repository.specification.EditionSpecification;
import com.group2.library_management.service.*;

import jakarta.transaction.Transactional;
import com.group2.library_management.entity.Publisher;
import com.group2.library_management.entity.enums.BookStatus;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import com.group2.library_management.repository.PublisherRepository;
import com.group2.library_management.service.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.group2.library_management.dto.mapper.EditionMapper;
import com.group2.library_management.dto.request.UpdateEditionRequest;

@Service
@RequiredArgsConstructor
public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;
    private final EditionMapper editionMapper;

    private final BookInstanceRepository bookInstanceRepository;
    private final PublisherRepository publisherRepository;
    private final FileStorageService fileStorageService; // Service handle file storage
    
    private static final long MAX_FILE_SIZE_MB = 5;
    private static final long MAX_FILE_SIZE_BYTES = MAX_FILE_SIZE_MB * 1024 * 1024;
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png", "image/gif", "image/jpg", "image/webp");

    private final MessageSource messageSource;

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

    @Override
    public Page<EditionListResponse> searchEditionAndBookInstance(String keyword, String filterBy, BookStatus bookStatus, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        Specification<Edition> spec = EditionSpecification.distinct();

        if("edition".equals(filterBy)){
            spec = spec.and(EditionSpecification.searchEditionByKeyword(keyword));
        }
        else {
            spec = spec.and(EditionSpecification.searchByBookInstanceFields(keyword, bookStatus, fromDate, toDate));
        }

        Page<Edition> editionsPage = editionRepository.findAll(spec, pageable);

        return editionsPage.map(editionMapper::toDto);
    }

    @Override
    public EditionUpdateResponse findEditionForUpdate(Integer id) {
        Edition edition = editionRepository.findById(id)
            .orElseThrow(() -> {
                String message = messageSource.getMessage(
                    "error.edition.not_found", new Object[]{id}, LocaleContextHolder.getLocale()
                );
                return new ResourceNotFoundException(message);
            });
        return editionMapper.toUpdateResponse(edition);
    }

    @Override
    @Transactional
    public void updateEdition(Integer id, UpdateEditionRequest request, MultipartFile coverImageFile) {

        Edition edition = editionRepository.findById(id)
            .orElseThrow(() -> {
                String message = messageSource.getMessage(
                    "error.edition.not_found", new Object[]{id}, LocaleContextHolder.getLocale()
                );
                return new ResourceNotFoundException(message);
            });

        String newCoverImageDbPath = null;

        // Handle cover image file
        if (coverImageFile != null && !coverImageFile.isEmpty()) {
            validateCoverImage(coverImageFile);

            newCoverImageDbPath = fileStorageService.storeFile(coverImageFile, id);
            edition.setCoverImageUrl(newCoverImageDbPath);
        }

        // Get publisher
        Publisher publisher = publisherRepository.findById(request.getPublisherId())
            .orElseThrow(() -> {
                String message = messageSource.getMessage(
                    "error.publisher.not_found",
                    new Object[]{request.getPublisherId()},
                    LocaleContextHolder.getLocale()
                );
                return new ResourceNotFoundException(message);
            });

        // Use editionMapper to update edition fields
        editionMapper.updateFromRequest(request, edition);
        edition.setPublisher(publisher);

        // Save changes into database
        try {
            editionRepository.saveAndFlush(edition);
        } catch (DataIntegrityViolationException e) {
            if (newCoverImageDbPath != null) {
                String filenameToDelete = newCoverImageDbPath.substring(newCoverImageDbPath.lastIndexOf("/") + 1);
                fileStorageService.deleteFile(filenameToDelete);
            }
            String message = messageSource.getMessage(
                "error.edition.isbn.exists",
                null,
                LocaleContextHolder.getLocale()
            );
            throw new IllegalArgumentException(message);
        }
        catch (ObjectOptimisticLockingFailureException e) {
            if (newCoverImageDbPath != null) {
                fileStorageService.deleteFile(newCoverImageDbPath);
            }
            String message = messageSource.getMessage(
                "error.edition.optimistic_lock", null, LocaleContextHolder.getLocale()
            );
            throw new OptimisticLockingFailureException(message);
        }
    }

    private void validateCoverImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            String message = messageSource.getMessage("error.file.is_empty", null, LocaleContextHolder.getLocale());
            throw new IllegalArgumentException(message);
        }

        if (file.getSize() > MAX_FILE_SIZE_BYTES) {
            String message = messageSource.getMessage("error.file.invalid.size", new Object[]{MAX_FILE_SIZE_MB}, LocaleContextHolder.getLocale());
            throw new IllegalArgumentException(message);
        }

        if (file.getContentType() == null || !ALLOWED_IMAGE_TYPES.contains(file.getContentType())) {
            String message = messageSource.getMessage("error.file.invalid.type", null, LocaleContextHolder.getLocale());
            throw new IllegalArgumentException(message);
        }
    }
}
