package com.group2.library_management.dto.response;

import lombok.Builder;

@Builder
public record BookInstanceEditResponse(
        Integer id,
        String editionTitle,
        String coverImageUrl,
        String isbn) {

}
