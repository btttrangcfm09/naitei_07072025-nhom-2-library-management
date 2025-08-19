package com.group2.library_management.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.group2.library_management.dto.mapper.BookInstanceMapper;
import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.repository.BookInstanceRepository;
import com.group2.library_management.service.*;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookInstanceServiceImpl implements BookInstanceService{
    private final BookInstanceRepository bookInstanceRepository;
    private final BookInstanceMapper bookInstanceMapper;
    
    @Override
    public List<BookInstanceResponse> getBookInstancesByEditionId(Integer editionId){
        return bookInstanceRepository
               .findByEditionIdOrderByAcquiredDateDesc(editionId)
               .stream()
               .map(bookInstanceMapper::toDto)
               .collect(Collectors.toList());
    }
}
