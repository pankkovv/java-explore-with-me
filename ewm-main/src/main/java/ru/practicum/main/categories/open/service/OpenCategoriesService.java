package ru.practicum.main.categories.open.service;

import ru.practicum.main.categories.dto.CategoryDto;

import java.util.List;

public interface OpenCategoriesService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoriesById(int catId);
}
