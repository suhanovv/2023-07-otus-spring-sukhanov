package ru.otus.homework.services.comments.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateCommentDto {
    private final String id;

    private final String commentText;
}
