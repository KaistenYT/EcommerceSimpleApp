package ks.dev.ShoppingCartApp.service.cart;

import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.model.User;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);





    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
