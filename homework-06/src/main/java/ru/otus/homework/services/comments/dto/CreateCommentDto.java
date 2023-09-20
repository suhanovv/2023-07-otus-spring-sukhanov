package ru.otus.homework.services.comments.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class CreateCommentDto {
    private final String commentText;

    private final long bookId;
}
