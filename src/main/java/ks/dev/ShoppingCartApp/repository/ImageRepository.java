package ks.dev.ShoppingCartApp.repository;

import ks.dev.ShoppingCartApp.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository <Image , Long >{
}
