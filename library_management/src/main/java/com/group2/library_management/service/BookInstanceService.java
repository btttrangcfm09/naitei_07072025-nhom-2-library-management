package com.group2.library_management.service;

import java.util.List;

import com.group2.library_management.dto.response.BookInstanceResponse;

public interface BookInstanceService {
    public List<BookInstanceResponse> getBookInstancesByEditionId(Integer editionId);
}
