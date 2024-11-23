package ks.dev.ShoppingCartApp.service.product;

import ks.dev.ShoppingCartApp.model.Product;

import java.util.List;

public interface IProductsService {

    Product addProduct(Product product);
    Product getProductById(Long id);
    void updateProduct(Product product , long productId);
    void deleteProduct(long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);




}
