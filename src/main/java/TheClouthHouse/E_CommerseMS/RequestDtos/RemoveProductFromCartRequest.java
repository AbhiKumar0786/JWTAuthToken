package TheClouthHouse.E_CommerseMS.RequestDtos;


import lombok.Data;

@Data

public class RemoveProductFromCartRequest {

    private Integer cartItemId;
    private Integer productId;
}
