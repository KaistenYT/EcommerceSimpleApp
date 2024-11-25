package ks.dev.ShoppingCartApp.controller;


import ks.dev.ShoppingCartApp.dto.ProductDto;
import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.response.ApiResponse;
import ks.dev.ShoppingCartApp.service.product.IProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductsService productsService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = productsService.getAllProducts();
        List<ProductDto> convertProducts = productsService.getConvertedProducts(products);
        return ResponseEntity.ok(new ApiResponse("success", convertProducts));
    }
    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {
        try {
            Product product = productsService.getProductById(productId);
            ProductDto productDto = productsService.convertToDto(product);
            return  ResponseEntity.ok(new ApiResponse("success", productDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
