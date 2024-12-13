package ks.dev.ShoppingCartApp.service.Order;

import ks.dev.ShoppingCartApp.model.Order;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

}
