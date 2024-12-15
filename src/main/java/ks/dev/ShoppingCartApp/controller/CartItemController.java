package ks.dev.ShoppingCartApp.controller;

import io.jsonwebtoken.JwtException;
import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.model.User;
import ks.dev.ShoppingCartApp.response.ApiResponse;
import ks.dev.ShoppingCartApp.service.cart.ICartItemService;
import ks.dev.ShoppingCartApp.service.cart.ICartService;
import ks.dev.ShoppingCartApp.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

@PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam (required = false)Long cartId ,
                                                    @RequestParam Long productId ,
                                                     @RequestParam Integer quantity){

     try {
         User user = userService.getAuthenticatedUser();
         Cart cart = cartService.initializeNewCart(user);
         cartItemService.addItemToCart(cart.getId(), productId, quantity);

         return ResponseEntity.ok(new ApiResponse("Add Item Success", null));
     }catch (ResourceNotFoundException e){
         return  ResponseEntity.status(NOT_FOUND)
                 .body(new ApiResponse("Error: " + e.getMessage(), null));
     }catch (JwtException e){
         return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
     }

    }

    @DeleteMapping("cart/{cartId}/item/{itemId}/remove")
    public  ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId ,
                                                           @PathVariable Long itemId){

        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Remove Item Success", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null)) ;
        }
    }
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                           @PathVariable Long itemId,
                                                           @RequestParam Integer quantity){

    try{
        cartItemService.updateItemQuantity(cartId,itemId,quantity);
        return  ResponseEntity.ok(new ApiResponse("Update Item Success", null));
    }catch (ResourceNotFoundException e ){
        return  ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse(e.getMessage(),null));
    }

    }




}
