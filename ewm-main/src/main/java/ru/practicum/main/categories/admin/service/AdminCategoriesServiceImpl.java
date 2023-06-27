package ru.practicum.main.categories.admin.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.main.categories.dto.CategoryDto;
import ru.practicum.main.categories.model.Category;
import ru.practicum.main.categories.repository.CategoriesRepository;

import static ru.practicum.main.categories.mapper.CatMap.mapToCategory;
import static ru.practicum.main.categories.mapper.CatMap.mapToCategoryDto;

@Service
@Transactional
@NoArgsConstructor
@Slf4j
public class AdminCategoriesServiceImpl implements AdminCategoriesService {
    @Autowired
    private CategoriesRepository repository;

    @Override
    public CategoryDto createCategories(CategoryDto categoryDto) {
        return mapToCategoryDto(repository.save(mapToCategory(categoryDto)));
    }

    @Override
    public void deleteCategories(int catId) {
        repository.deleteById(catId);
    }

    @Override
    public CategoryDto changeCategories(int catId, CategoryDto categoryDto) {
        Category oldCategory = repository.findById(catId).orElseThrow();
        if (categoryDto.getName() != null) {
            oldCategory.setName(categoryDto.getName());
        }
        return mapToCategoryDto(repository.save(oldCategory));
    }

    @Override
    public Category findCategoriesById(int catId){
        return repository.findById(catId).orElseThrow();
    }
}
