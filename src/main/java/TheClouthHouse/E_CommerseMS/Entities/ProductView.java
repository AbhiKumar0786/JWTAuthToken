package TheClouthHouse.E_CommerseMS.Entities;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductView {
    private Integer ProductID;
    private String ProductName;
    private Double Price;
}
