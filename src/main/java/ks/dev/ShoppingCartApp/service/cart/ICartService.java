package ks.dev.ShoppingCartApp.service.cart;

import ks.dev.ShoppingCartApp.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Long initializerNewCart();
}
