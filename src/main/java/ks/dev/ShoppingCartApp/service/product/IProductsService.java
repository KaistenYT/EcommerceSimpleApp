package ks.dev.ShoppingCartApp.service.product;

import ks.dev.ShoppingCartApp.dto.ProductDto;
import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.request.AddProductRequest;
import ks.dev.ShoppingCartApp.request.ProductUpdateRequest;

import java.util.List;

public interface IProductsService {

    Product addProduct(AddProductRequest producto);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest product , long productId);
    void deleteProduct(long productId);

    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand, String name);
    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);


}
