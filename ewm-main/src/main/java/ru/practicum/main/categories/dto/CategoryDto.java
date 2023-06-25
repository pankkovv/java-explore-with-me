package ru.practicum.main.categories.dto;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CategoryDto {
    private int id;
    @NotBlank
    @Length(min = 1, max = 50)
    private String name;
}
