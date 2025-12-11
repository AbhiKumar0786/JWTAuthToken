package TheClouthHouse.E_CommerseMS.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orderItem")
@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @JoinColumn
    @ManyToOne
    private Order order;

    @JoinColumn
    @ManyToOne
    private Product productItem;

    private Integer quantity;

    private Double price;



}
