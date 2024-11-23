package ks.dev.ShoppingCartApp.repository;

import ks.dev.ShoppingCartApp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product , Long> {
}
