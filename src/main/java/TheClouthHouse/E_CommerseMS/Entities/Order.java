package TheClouthHouse.E_CommerseMS.Entities;


import TheClouthHouse.E_CommerseMS.Enum.OrderStatus;
import TheClouthHouse.E_CommerseMS.Enum.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private String shippingAddress;

    private LocalDateTime createdAt;


}
