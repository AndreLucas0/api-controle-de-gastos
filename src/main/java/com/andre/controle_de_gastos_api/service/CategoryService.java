package com.andre.controle_de_gastos_api.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andre.controle_de_gastos_api.controller.DTO.CreateCategoryDTO;
import com.andre.controle_de_gastos_api.model.Category;
import com.andre.controle_de_gastos_api.repository.CategoryRepository;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public UUID createCategory (CreateCategoryDTO createCategoryDTO) {

        //DTO -> Entity
        Category category = new Category();
        category.setName(createCategoryDTO.name());

        var categorySaved = categoryRepository.save(category);

        return categorySaved.getId();
    }

    public List<Category> listCategories() {
        return categoryRepository.findAll();
    }
}
