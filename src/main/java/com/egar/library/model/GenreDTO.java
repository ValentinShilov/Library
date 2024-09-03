package com.egar.library.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GenreDTO {

    private Long id;

    @Size(max = 255)
    private String name;

}
