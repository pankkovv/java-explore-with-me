package ru.practicum.main.categories.admin.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.main.categories.admin.service.AdminCategoriesService;
import ru.practicum.main.categories.dto.CategoryDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminCategoriesController {
    @Autowired
    private AdminCategoriesService service;

    @PostMapping(path = "/categories")
    public CategoryDto createCategories(@RequestBody @Valid CategoryDto categoryDto) {
        return service.createCategories(categoryDto);
    }

    @DeleteMapping(path = "/categories/{catId}")
    public void deleteCategories(@PathVariable(name = "catId") int catId) {
        service.deleteCategories(catId);
    }

    @PatchMapping(path = "/categories/{catId}")
    public CategoryDto changeCategories(@PathVariable(name = "catId") int catId,
                                        @RequestBody @Valid CategoryDto categoryDto) {
        return service.changeCategories(catId, categoryDto);
    }
}
