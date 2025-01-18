package com.dailycodework.dreamshops.service.product;

import com.dailycodework.dreamshops.DTO.ProductDto;
import com.dailycodework.dreamshops.model.Product;
import com.dailycodework.dreamshops.requests.AddProductRequest;
import com.dailycodework.dreamshops.requests.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);

    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProductById(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);

    Product updateProduct(ProductUpdateRequest request, Long productId);

    //List<ProductDto> getCovertedProducts(List<Product> products);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
