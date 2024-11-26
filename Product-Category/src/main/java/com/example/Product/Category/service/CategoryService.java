package com.example.Product.Category.service;

import com.example.Product.Category.Repository.CategoryRepository;
import com.example.Product.Category.dto.CategoryDTO;
import com.example.Product.Category.dto.ProductDTO;
import com.example.Product.Category.entity.Category;
import com.example.Product.Category.entity.Product;
import com.example.Product.Category.mapper.CategoryMapper;
import com.example.Product.Category.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    // create category
    public CategoryDTO createCategory(CategoryDTO categoryDTO){


        Category category = CategoryMapper.toCategoryEntity(categoryDTO);
        category = categoryRepository.save(category);
        return CategoryMapper.toCategoryDTO(category);
    }
    // get all categories
    // public List<CategoryDTO> getAllCategories() {
    //   return categoryRepository.findAll().stream().map(CategoryMapper::toCategoryDTO).toList();
    //  }

    public List<CategoryDTO> getAllCategories(int page, int size) {
        Pageable p=PageRequest.of(page, size);
        Page<Category> cate =categoryRepository.findAll(p);
        List<Category> allcategory=cate.getContent();
        //return categoryRepository.findAll(PageRequest.of(page, size));
        return allcategory.stream().map(CategoryMapper::toCategoryDTO).toList();
    }

    // get category by id
    public CategoryDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(()-> {
            return new RuntimeException();
        });
        return CategoryMapper.toCategoryDTO(category);
    }
    // delete category
    public String deleteCategory(Long id){
        categoryRepository.deleteById(id);
        return "Category "+ id + " has been deleted!";
    }
    //put category
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));


        category.setName(categoryDTO.getName());



        categoryRepository.save(category);
        return CategoryMapper.toCategoryDTO(category);
    }


}
