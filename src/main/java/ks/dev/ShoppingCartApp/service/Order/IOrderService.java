package ks.dev.ShoppingCartApp.service.Order;

import ks.dev.ShoppingCartApp.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    Order getOrder(Long orderId);

    List<Order> getUserOrders(Long userId);
}
