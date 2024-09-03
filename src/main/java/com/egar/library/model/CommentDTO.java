package com.egar.library.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommentDTO {

    private Long id;

    @Size(max = 255)
    private String text;

    @Size(max = 255)
    private String evaluation;

    private Long userId;

    private Long bookId;

}
