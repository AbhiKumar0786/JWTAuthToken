package TheClouthHouse.E_CommerseMS.Entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cartItem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Cart cart;

    @ManyToOne
    @JoinColumn
    private Product product;

    private Integer quantity;

    private double price;

}
