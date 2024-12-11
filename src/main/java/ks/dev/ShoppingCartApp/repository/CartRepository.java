package ks.dev.ShoppingCartApp.repository;

import ks.dev.ShoppingCartApp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository  extends JpaRepository <Cart , Long> {
}
