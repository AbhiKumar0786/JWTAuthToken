package TheClouthHouse.E_CommerseMS.Entities;


import TheClouthHouse.E_CommerseMS.Enum.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistoryDetail {

    private String ProductName;
    private Integer Quantity;
    private Double TotalPrice;
    private LocalDateTime OrderDateAndTime;
    private OrderStatus orderStatus;

}
