package ks.dev.ShoppingCartApp.repository;

import ks.dev.ShoppingCartApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User , Long> {

    boolean existsByEmail(String email);
    User findByEmail(String email);
}
