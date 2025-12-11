package TheClouthHouse.E_CommerseMS.RequestDtos;


import TheClouthHouse.E_CommerseMS.Entities.CartItem;
import lombok.Data;

import java.util.List;

@Data
public class PlaceOrderRequest {
    private List<Integer> cartItemIdList;
    private Long userId;
}
