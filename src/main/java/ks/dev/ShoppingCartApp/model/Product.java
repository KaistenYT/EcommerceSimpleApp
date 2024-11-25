package ks.dev.ShoppingCartApp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Image>  images;

    public Product(String brand, String name, BigDecimal price, int inventory, String description, Category category) {
        this.brand = brand;
        this.name = name;
        this.price = price;
        this.inventory = inventory;
        this.description = description;
        this.category = category;

    }
}