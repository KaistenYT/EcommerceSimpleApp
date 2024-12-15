package ks.dev.ShoppingCartApp.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import ks.dev.ShoppingCartApp.dto.OrderDto;
import ks.dev.ShoppingCartApp.exceptions.ResourceNotFoundException;
import ks.dev.ShoppingCartApp.model.Order;
import ks.dev.ShoppingCartApp.response.ApiResponse;
import ks.dev.ShoppingCartApp.service.Order.IOrderService;
import ks.dev.ShoppingCartApp.service.payment.PaymentInfo;
import ks.dev.ShoppingCartApp.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/Orders")
public class OrderController {
    private final IOrderService orderService;
    private final PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId){
        try {
            Order order = orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Success!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
           OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item Order Success!", order));
        } catch (ResourceNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }

    }
    @GetMapping("/user/{userId}/order")
    public ResponseEntity<ApiResponse>getUserOrders(@PathVariable Long userId){
        try {
            List<OrderDto> order = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("Success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error Occurred!", e.getMessage()));
        }

    }
    @PostMapping("/payment/create-payment-intent")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentInfo paymentInfo)throws StripeException{
        try {
            System.out.println("The body :" +paymentInfo);
            PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentInfo);
            String paymentString = paymentIntent.toJson();
            System.out.println("The payment string :" + paymentString);
            return ResponseEntity.ok(paymentString);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }





}
