package ks.dev.ShoppingCartApp.service.cart;

import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.model.CartItem;
import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.repository.CartItemRepository;
import ks.dev.ShoppingCartApp.repository.CartRepository;
import ks.dev.ShoppingCartApp.service.product.IProductsService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;


import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {

    private final CartItemRepository cartItemRepository;
    private final IProductsService productsService;
    private final ICartService cartService;
    private final CartRepository cartRepository;


    @Override

    public void addItemToCart(Long cartId, Long productId, int quantity) {
       Cart cart = cartService.getCart(cartId);
       Product product = productsService.getProductById(productId);
       CartItem cartItem = cart.getItems()
               .stream()
               .filter(item-> item.getProduct().getId().equals(productId))
               .findFirst().orElse(new CartItem());
       if (cartItem.getId()== null){
           cartItem.setCart(cart);
           cartItem.setProduct(product);
           cartItem.setQuantity(quantity);
           cartItem.setUnitPrice(product.getPrice());
       }else {
           cartItem.setQuantity(cartItem.getQuantity()+quantity);
       }
       cartItem.setTotalPrice();
       cart.addItem(cartItem);
       cartItemRepository.save(cartItem);
       cartRepository.save(cart);
    }

    @Override

    public void removeItemFromCart(Long cartId, Long productId) {
       Cart cart = cartService.getCart(cartId);
       CartItem itemToRemove=getCartItem(cartId,productId);
       cart.removeItem(itemToRemove);
       cartRepository.save(cart);
    }


    @Override

    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Item not found in cart"));
    }

    @Override

    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
       Cart cart = cartService.getCart(cartId);
       cart.getItems()
               .stream()
               .filter(item-> item.getProduct().getId().equals(productId))
               .findFirst()
               .ifPresent(item->{
                   item.setQuantity(quantity);
                   item.setUnitPrice(item.getProduct().getPrice());
                   item.setTotalPrice();
               });
       BigDecimal totalAount = cart.getItems()
               .stream().map(CartItem::getTotalPrice)
               .reduce(BigDecimal.ZERO, BigDecimal::add);
       cart.setTotalAmount(totalAount);
       cartRepository.save(cart);
    }
}
