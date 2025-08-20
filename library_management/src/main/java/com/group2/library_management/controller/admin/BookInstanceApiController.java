package com.group2.library_management.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group2.library_management.dto.response.BookInstanceResponse;
import com.group2.library_management.service.BookInstanceService;

import lombok.RequiredArgsConstructor;

@RestController("AdminBookInstanceApiController")
@RequestMapping("/admin/bookinstances")
@RequiredArgsConstructor
public class BookInstanceApiController {
    private final BookInstanceService bookInstanceService;

    @GetMapping("/edition/{editionId}")
    public ResponseEntity<List<BookInstanceResponse>> getBookInstancesByEditionId(@PathVariable Integer editionId){
        List<BookInstanceResponse> bookInstances = bookInstanceService.getBookInstancesByEditionId(editionId);
        return ResponseEntity.ok(bookInstances);
    }
}
