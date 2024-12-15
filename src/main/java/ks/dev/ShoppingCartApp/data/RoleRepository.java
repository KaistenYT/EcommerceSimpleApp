package ks.dev.ShoppingCartApp.data;

import ks.dev.ShoppingCartApp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository  extends JpaRepository<Role , Long> {

    Optional<Role> findByName(String role);
}
