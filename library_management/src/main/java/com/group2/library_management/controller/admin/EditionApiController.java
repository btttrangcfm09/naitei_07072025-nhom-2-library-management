package com.group2.library_management.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.library_management.dto.response.EditionDetailResponse;
import com.group2.library_management.dto.response.EditionResponse;
import com.group2.library_management.service.EditionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class EditionApiController {
    
    private final EditionService editionService;

    @GetMapping("/books/{bookId}/editions")
    public ResponseEntity<List<EditionResponse>> getEditionsByBookId(@PathVariable Integer bookId) {
        List<EditionResponse> editions = editionService.getEditionsByBookId(bookId);
        return ResponseEntity.ok(editions);
    }

    @GetMapping("/editions/{id}")
    public ResponseEntity<EditionDetailResponse> getEditionDetail(@PathVariable Integer id) {
        EditionDetailResponse editionDetail = editionService.getEditionDetailById(id);
        return ResponseEntity.ok(editionDetail);
    }
}
