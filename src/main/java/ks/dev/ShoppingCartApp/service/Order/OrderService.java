package ks.dev.ShoppingCartApp.service.Order;

import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Cart;
import ks.dev.ShoppingCartApp.model.Order;
import ks.dev.ShoppingCartApp.model.OrderItem;
import ks.dev.ShoppingCartApp.model.Product;
import ks.dev.ShoppingCartApp.repository.OrderRepository;
import ks.dev.ShoppingCartApp.repository.ProductRepository;
import ks.dev.ShoppingCartApp.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService  implements  IOrderService{
    private  final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;
    private final ModelMapper modelMapper;


    @Override
    public Order placeOrder(Long userId) {

        return null;
    }
    private  List<OrderItem> createOrderItems(Order order, Cart cart){
        return   cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory()-cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());

        }).toList();
    }

    @Override
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    private BigDecimal calculateTotalPriceAmount(List <OrderItem> orderItemList){
        return  orderItemList.stream()
                .map(item->item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO , BigDecimal::add);

    }
}
