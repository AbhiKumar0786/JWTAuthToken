package TheClouthHouse.E_CommerseMS.Entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer noOfProducts;

    private Double totalPrice;

    @JsonIgnore
    @JoinColumn
    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL)
    private List<CartItem> cartItemList = new ArrayList<>();
}
