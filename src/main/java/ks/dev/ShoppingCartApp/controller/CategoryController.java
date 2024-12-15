package ks.dev.ShoppingCartApp.controller;

import ks.dev.ShoppingCartApp.exceptions.AlreadyExistsException;
import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Category;
import ks.dev.ShoppingCartApp.response.ApiResponse;
import ks.dev.ShoppingCartApp.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.hibernate.grammars.hql.HqlParser.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found!", categories));
        }catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("error: ", INTERNAL_SERVER_ERROR));
        }
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
try{
    Category theCategory = categoryService.addCategory(name);
    return ResponseEntity.ok(new ApiResponse("Success", theCategory));

}catch (AlreadyExistsException e){
    return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
}
    }
    @GetMapping("/category/{id}/category")
    public  ResponseEntity<ApiResponse > getCategoryById(@PathVariable long id){
        try{
            Category theCategory = categoryService.getCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", theCategory));

        }catch (ResourceNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }
   @GetMapping("/category/{name}/category-name")
   public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category theCategory = categoryService.getCategoryByName(name);
            return  ResponseEntity.ok(new ApiResponse("found", theCategory));
        }catch (ResourceNotFoundException e){
            return  ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null ));
        }
   }
   @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategoryById(@PathVariable long id){
        try{
            categoryService.deleteCategoryById(id);
            return  ResponseEntity.ok(new ApiResponse("Found", null));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
   }
   @PutMapping("/category/{id}/update")
    public  ResponseEntity<ApiResponse> updateCategory(@PathVariable long id , @RequestBody Category category){
        try {
            Category updatedCategory= categoryService.updateCategory(category , id);
            return  ResponseEntity.ok(new ApiResponse("Update success!", updatedCategory));
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
   }
}
