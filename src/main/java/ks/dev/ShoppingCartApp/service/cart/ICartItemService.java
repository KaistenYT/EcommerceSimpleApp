package ks.dev.ShoppingCartApp.service.cart;


import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.model.CartItem;
import org.springframework.transaction.annotation.Transactional;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId, Long productId);


    CartItem getCartItem(Long cartId, Long productId);

    void updateItemQuantity(Long cartId, Long productId, int quantity);





}
