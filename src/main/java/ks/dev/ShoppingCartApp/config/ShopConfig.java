package ks.dev.ShoppingCartApp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShopConfig {
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
