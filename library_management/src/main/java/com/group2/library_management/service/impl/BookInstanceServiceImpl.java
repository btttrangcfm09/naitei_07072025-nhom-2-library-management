package com.group2.library_management.service.impl;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.group2.library_management.dto.mapper.BookInstanceMapper;
import com.group2.library_management.dto.response.BookInstanceDetailResponse;
import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.repository.BookInstanceRepository;
import com.group2.library_management.repository.BorrowingDetailRepository;
import com.group2.library_management.service.*;

import org.springframework.transaction.annotation.Transactional; 

import com.group2.library_management.entity.*;
import com.group2.library_management.entity.enums.BookStatus;
import com.group2.library_management.exception.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookInstanceServiceImpl implements BookInstanceService {
    private final BookInstanceRepository bookInstanceRepository;
    private final BookInstanceMapper bookInstanceMapper;
    private final EnumDisplayService enumDisplayService;
    private final MessageSource messageSource;
    private final BorrowingDetailRepository borrowingDetailRepository;

    public String getMessage(String barcode, String status) {
        Locale locale = LocaleContextHolder.getLocale();
        Object[] args = { barcode, status };
        return messageSource.getMessage("bookinstance.exception.can.delete", args, locale);
    }

    @Override
    public List<BookInstanceResponse> getBookInstancesByEditionId(Integer editionId) {
        return bookInstanceRepository
                .findByEditionIdOrderByAcquiredDateDesc(editionId)
                .stream()
                .map(bookInstanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(noRollbackFor = CannotDeleteResourceException.class)
    public void deleteBookInstanceByBookInstanceId(Integer bookInstanceId) throws BookInstanceNotFoundException, CannotDeleteResourceException {
        BookInstance bookInstance = bookInstanceRepository.findById(bookInstanceId)
                .orElseThrow(() -> new BookInstanceNotFoundException(bookInstanceId));
        if (checkStatusBookInstance(bookInstance.getStatus())) {
            String message = getMessage(
                    bookInstance.getBarcode(),
                    enumDisplayService.getBookStatusDisplayName(bookInstance.getStatus()));
            // inactive 
            updateBookInstanceStatus(bookInstance,BookStatus.ARCHIVED);
            throw new CannotDeleteResourceException(message);
        }

        if (!borrowingDetailRepository.existsByBookInstanceId(bookInstanceId)) {
            permanentlyDeleteById(bookInstanceId);
            return;
        }

        bookInstanceRepository.delete(bookInstance);
    }

    private static final Set<BookStatus> NON_DELETABLE_STATUSES = Set.of(BookStatus.BORROWED, BookStatus.RESERVED, BookStatus.REPAIRING, BookStatus.ARCHIVED);

    private Boolean checkStatusBookInstance(BookStatus status) {
        return NON_DELETABLE_STATUSES.contains(status);
    }

    private void permanentlyDeleteById(Integer bookInstanceId){
        bookInstanceRepository.permanentlyDeleteById(bookInstanceId);
    }

    // Change to inactive(ARCHIVED in database)
    private void updateBookInstanceStatus(BookInstance bookInstance, BookStatus newStatus){
        bookInstance.setStatus(newStatus);
        // save in db
        bookInstanceRepository.save(bookInstance);
    }

    @Override
    public BookInstanceDetailResponse getBookInstanceDetail(Integer bookInstanceId) {

        return bookInstanceRepository
            .findById(bookInstanceId)
            .map(bookInstanceMapper::mapToBookInstanceDetailResponse)
            .orElse(null);
            
    }
}
