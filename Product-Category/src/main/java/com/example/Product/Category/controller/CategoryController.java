package com.example.Product.Category.controller;

import com.example.Product.Category.dto.CategoryDTO;
import com.example.Product.Category.dto.ProductDTO;
import com.example.Product.Category.entity.Category;
import com.example.Product.Category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategory = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);

    }
    //  @GetMapping
    //  public List<CategoryDTO> getAllCategories(){
    //     return categoryService.getAllCategories();
    //  }
    @GetMapping
    public List<CategoryDTO> getAllCategories(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return categoryService.getAllCategories(page, size);
    }

    // get category by id
    @GetMapping("/{id}")
    public CategoryDTO getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteCategory(@PathVariable Long id){
        return categoryService.deleteCategory(id);
    }
    //put categories
    @PutMapping("/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return categoryService.updateCategory(id, categoryDTO);
    }
}
