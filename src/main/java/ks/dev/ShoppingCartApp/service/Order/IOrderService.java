package ks.dev.ShoppingCartApp.service.Order;

import ks.dev.ShoppingCartApp.dto.OrderDto;
import ks.dev.ShoppingCartApp.model.Order;

import java.util.List;

public interface IOrderService {

    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);

    List<OrderDto> getUserOrders(Long userId);
}
