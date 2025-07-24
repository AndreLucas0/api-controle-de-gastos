package com.andre.controle_de_gastos_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andre.controle_de_gastos_api.model.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID>{
    
}
