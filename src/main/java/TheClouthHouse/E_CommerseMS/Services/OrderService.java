package TheClouthHouse.E_CommerseMS.Services;


import TheClouthHouse.E_CommerseMS.Entities.*;
import TheClouthHouse.E_CommerseMS.Enum.OrderStatus;
import TheClouthHouse.E_CommerseMS.Enum.PaymentMethod;
import TheClouthHouse.E_CommerseMS.Enum.PaymentStatus;
import TheClouthHouse.E_CommerseMS.Repository.CartItemRepository;
import TheClouthHouse.E_CommerseMS.Repository.OrderItemRepository;
import TheClouthHouse.E_CommerseMS.Repository.OrderRepository;
import TheClouthHouse.E_CommerseMS.Repository.UserRepository;
import TheClouthHouse.E_CommerseMS.RequestDtos.OrderHistoryRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.PlaceOrderRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.UpdateQuantityRequest;
import TheClouthHouse.E_CommerseMS.Response.OrderPlacedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    public OrderPlacedResponse placeOrder(PlaceOrderRequest placeOrderRequest) throws Exception{

        //Getting Entities from Repository
        Optional<User> optionalUser  = userRepository.findById(placeOrderRequest.getUserId());

        List<CartItem> toBeOrderList  = new ArrayList<>();
        for(Integer id : placeOrderRequest.getCartItemIdList() ){
            Optional<CartItem> optionalCartItem  = cartItemRepository.findById(id);
            if(!optionalCartItem.isEmpty()){
                toBeOrderList.add(optionalCartItem.get());
            }
            else{
                throw new Exception("Please Enter Valid CartItemID!!");
            }
        }

        //Validate
        if(optionalUser.isEmpty()){
            throw new Exception("Please Enter Valid userID!!");
        }

        //Get
        User user  = optionalUser.get();

        //Making the order entity
        Order order = Order.builder()
                .orderStatus(OrderStatus.PENDING)
                .user(user)
                .paymentMethod(PaymentMethod.UPI)
                .shippingAddress("Mo. Deeppur Hargaon Distric:Sitapur , PinCode:261121")
                .createdAt(LocalDateTime.now())
                .build();

        order  = orderRepository.save(order);


        //Making the OrderItems for placing order.
        List<OrderItem> orderItemList  = new ArrayList<>();
        List<ProductDetail> productDetailList  = new ArrayList<>();
        Double TotalPrice  = 0.0;
        for (CartItem cartItem : toBeOrderList) {

            Product product = cartItem.getProduct();  // get product

            // Reduce product stock safely
            productService.reduceStock(product.getId(), cartItem.getQuantity());

            // Create OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .price(cartItem.getPrice())
                    .productItem(cartItem.getProduct())
                    .quantity(cartItem.getQuantity())
                    .build();

            orderItem = orderItemRepository.save(orderItem);
            orderItemList.add(orderItem);

            //Add product detail for response
            ProductDetail productDetail = ProductDetail.builder()
                    .productName(product.getProductName())
                    .price(cartItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .build();

            productDetailList.add(productDetail);

            // Update total price
            TotalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();

            // Remove ordered product from cart
            UpdateQuantityRequest updateQuantityRequest = new UpdateQuantityRequest();
            updateQuantityRequest.setNewQuantity(0);
            updateQuantityRequest.setProductId(product.getId());
            updateQuantityRequest.setCartItemId(cartItem.getId());

            cartService.UpdateQuantity(updateQuantityRequest);
        }


        //Adding OrderItemList to the Order.


        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setOrderItemList(orderItemList);
        order.setTotalAmount(TotalPrice);

        //updating user as well.
        user.getOrderList().add(order);
        userRepository.save(user);

        //Saving Order to repository;
        orderRepository.save(order);

        //Making Response Entity
        OrderPlacedResponse orderPlacedResponse = OrderPlacedResponse.builder()
                .productDetailList(productDetailList)
                .TotalAmount(TotalPrice)
                .placedAt(order.getCreatedAt())
                .orderStatus(order.getOrderStatus().toString())
                .orderId(order.getId().toString())
                .paymentStatus(PaymentStatus.PAID.toString())
                .message("Order placed successfully")
                .build();

        return orderPlacedResponse;


    }

    public OrderHistoryDetail getOrderHistoryDetail(OrderHistoryRequest orderHistoryRequest) throws Exception{

        Optional<Order> optionalOrder  = orderRepository.findById(orderHistoryRequest.getOrderId());

        if(optionalOrder.isEmpty()){
            throw new Exception("Please Enter Valid OrderId!!");
        }

        Order order  = optionalOrder.get();
        OrderItem orderItem = orderItemRepository.findByOrder(order);

        OrderHistoryDetail orderHistoryDetail  = OrderHistoryDetail.builder()
                .ProductName(orderItem.getProductItem().getProductName())
                .orderStatus(order.getOrderStatus())
                .OrderDateAndTime(order.getCreatedAt())
                .Quantity(orderItem.getQuantity())
                .TotalPrice(order.getTotalAmount())
                .build();

        orderRepository.save(order);
        orderItemRepository.save(orderItem);

        return orderHistoryDetail;
    }
}
