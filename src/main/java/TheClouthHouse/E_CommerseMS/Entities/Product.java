package TheClouthHouse.E_CommerseMS.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    private Integer stock;

    private Double price;

    // THIS SOLVES CONCURRENCY
    @Version
    private Integer version;

    @OneToMany(mappedBy = "product" , cascade = CascadeType.ALL)
    private List<CartItem> cartItemList = new ArrayList<>();

    @OneToMany(mappedBy = "productItem" , cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList  = new ArrayList<>();
}


