package com.group2.library_management.service;

import java.util.List;

import com.group2.library_management.dto.request.bookinstance.UpdateBookInstanceRequest;
import com.group2.library_management.dto.response.BookInstanceDetailResponse;
import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.entity.BookInstance;
import com.group2.library_management.exception.BookInstanceNotFoundException;
import com.group2.library_management.exception.CannotDeleteResourceException;
import com.group2.library_management.exception.ConcurrencyException;
import com.group2.library_management.exception.ResourceNotFoundException;

public interface BookInstanceService {
    public List<BookInstanceResponse> getBookInstancesByEditionId(Integer editionId);
    public void deleteBookInstanceByBookInstanceId(Integer bookInstanceId) throws BookInstanceNotFoundException, CannotDeleteResourceException;
    public BookInstanceDetailResponse getBookInstanceDetail(Integer bookInstanceId);
    public BookInstance getBookInstanceById(Integer bookInstanceId) throws ResourceNotFoundException;
    public void updateBookInstance(Integer id, UpdateBookInstanceRequest request) throws IllegalArgumentException, ResourceNotFoundException, ConcurrencyException;
}
