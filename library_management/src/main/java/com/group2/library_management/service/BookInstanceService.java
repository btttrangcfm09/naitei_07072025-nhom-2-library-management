package com.group2.library_management.service;

import java.util.List;

import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.exception.BookInstanceNotFoundException;
import com.group2.library_management.exception.CannotDeleteResourceException;

public interface BookInstanceService {
    public List<BookInstanceResponse> getBookInstancesByEditionId(Integer editionId);
    public void deleteBookInstanceByBookInstanceId(Integer bookInstanceId) throws BookInstanceNotFoundException, CannotDeleteResourceException;
}
