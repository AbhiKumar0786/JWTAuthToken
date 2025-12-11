package TheClouthHouse.E_CommerseMS.Entities;


import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail {
    private String productName;
    private Integer quantity;
    private Double price;
}
