package com.egar.library.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BookDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    private Long author;

    private Long genre;

}
