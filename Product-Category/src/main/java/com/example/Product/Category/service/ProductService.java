package com.example.Product.Category.service;

import com.example.Product.Category.Repository.CategoryRepository;
import com.example.Product.Category.Repository.ProductRepository;
import com.example.Product.Category.dto.ProductDTO;
import com.example.Product.Category.entity.Category;
import com.example.Product.Category.entity.Product;
import com.example.Product.Category.mapper.CategoryMapper;
import com.example.Product.Category.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO){

        Category category= categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()-> {
                    return new RuntimeException();
                });

        // DTO -> entity
        Product product = ProductMapper.toProductEntity(productDTO, category);
        product = productRepository.save(product);
        // Entity -> DTO
        return ProductMapper.toProductDTO(product);

    }
    // Get All Products
    //  public List<ProductDTO> getAllProducts(){
    //     return productRepository.findAll().stream().map(ProductMapper::toProductDTO).toList();
    // }
    public List<ProductDTO> getAllProducts(int page, int size) {
        Pageable p=PageRequest.of(page, size);
        Page<Product> cate =productRepository.findAll(p);
        List<Product> allProduct=cate.getContent();
        return allProduct.stream().map(ProductMapper::toProductDTO).toList();
    }

    // Get Product by id
    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        return ProductMapper.toProductDTO(product);
    }
    // Update Product
    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found!"));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found!"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCategory(category);
        productRepository.save(product);
        return ProductMapper.toProductDTO(product);
    }

    public String deleteProduct(Long id){
        productRepository.deleteById(id);
        return "Product "+ id + " has been deleted!";
    }

}
