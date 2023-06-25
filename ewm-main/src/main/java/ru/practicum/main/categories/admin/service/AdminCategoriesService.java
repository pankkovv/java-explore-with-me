package ru.practicum.main.categories.admin.service;

import ru.practicum.main.categories.dto.CategoryDto;

public interface AdminCategoriesService {
    CategoryDto createCategories( CategoryDto categoryDto);
    void deleteCategories(int catId);
    CategoryDto changeCategories(int catId, CategoryDto categoryDto);
}
