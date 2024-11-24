package ks.dev.ShoppingCartApp.service.product;

import ks.dev.ShoppingCartApp.exceptions.ProductNotFoundException;
import ks.dev.ShoppingCartApp.model.Category;
import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.repository.CategoryRepository;
import ks.dev.ShoppingCartApp.repository.ProductRepository;
import ks.dev.ShoppingCartApp.request.AddProductRequest;
import ks.dev.ShoppingCartApp.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductsService implements IProductsService{

    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
    Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
            .orElseGet(()->{
                Category newCategory = new Category(request.getCategory().getName());
                return categoryRepository.save(newCategory);
            });
    request.setCategory(category);
    return productRepository.save(createProduct(request, category));


    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Producto Not Found"));
    }
private Product createProduct(AddProductRequest request, Category category){
     return  new Product(
             request.getName(),
             request.getBrand(),
             request.getPrice(),
             request.getInventory(),
             request.getDescription(),
             category
     );

}


    @Override
    public void deleteProduct(long productId) {
        productRepository.findById(productId).ifPresentOrElse(
                productRepository::delete,
                () -> { throw new ProductNotFoundException("Product not found!"); }
        );
    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, long productId) {
        return productRepository.findById(productId)
                .map(existingProduct -> updateExistingProduct(existingProduct, request))
                .map(productRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product Not Found!"));

    }

    private  Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return  existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category , brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand , String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
