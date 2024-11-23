package ks.dev.ShoppingCartApp.service.product;

import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.repository.ProductRepository;

import java.util.List;

public class ProductsService implements IProductsService{

    private ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) {
        return null;
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(()-> );
    }

    @Override
    public void updateProduct(Product product, long productId) {

    }

    @Override
    public void deleteProduct(long productId) {

    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return List.of();
    }

    @Override
    public List<Product> getProductsByBrandAndName(String category, String name) {
        return List.of();
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return 0;
    }
}
