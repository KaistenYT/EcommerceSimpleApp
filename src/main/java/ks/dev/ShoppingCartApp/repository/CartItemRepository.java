package ks.dev.ShoppingCartApp.repository;

import ks.dev.ShoppingCartApp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem , Long> {

    void deleteAllByCartId(Long id);
}
