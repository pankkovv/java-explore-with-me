package ru.practicum.main.categories.mapper;

import org.springframework.data.domain.Page;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CatMap {
    public static Category mapToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .events(categoryDto.getEvents())
                .build();
    }

    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static List<CategoryDto> mapToListCategoryDto(Page<Category> listCategory) {
        List<CategoryDto> listCategoryDto = new ArrayList<>();
        for (Category cat : listCategory) {
            listCategoryDto.add(mapToCategoryDto(cat));
        }
        return listCategoryDto;
    }
}
