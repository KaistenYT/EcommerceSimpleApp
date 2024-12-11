package ks.dev.ShoppingCartApp.service.cart;

import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.repository.CartItemRepository;
import ks.dev.ShoppingCartApp.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    @Transactional(readOnly = true)
    public Cart getCart(Long id) {
        return cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));
    }


    @Override
    @Transactional
    public void clearCart(Long id) {
      Cart cart = getCart(id);
      cartItemRepository.deleteAllByCartId(id);
      cart.getItems().clear();
      cartRepository.deleteById(id);
    }



    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    @Override
    @Transactional
    public Long initializerNewCart() {
        Cart newCart = new Cart();
        cartRepository.save(newCart);
        return newCart.getId();
    }
}
