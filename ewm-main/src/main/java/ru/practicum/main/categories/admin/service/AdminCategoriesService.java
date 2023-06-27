package ru.practicum.main.categories.admin.service;

import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;

public interface AdminCategoriesService {
    CategoryDto createCategories(CategoryDto categoryDto);

    void deleteCategories(int catId);

    CategoryDto changeCategories(int catId, CategoryDto categoryDto);
    Category findCategoriesById(int catId);
}
