package com.group2.library_management.service.impl;

import org.springframework.stereotype.Service;
import com.group2.library_management.dto.response.EditionListResponse;
import com.group2.library_management.entity.Edition;
import com.group2.library_management.repository.EditionRepository;
import com.group2.library_management.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.group2.library_management.dto.mapper.EditionMapper;

@Service
@RequiredArgsConstructor
public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;
    private final EditionMapper editionMapper;
    @Override
    public Page<EditionListResponse> getAllEditions(Pageable pageable) {
        Page<Edition> editionsPage = editionRepository.findAll(pageable);
        return editionsPage.map(editionMapper::toDto);
    }
}
