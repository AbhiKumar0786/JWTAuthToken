package TheClouthHouse.E_CommerseMS.RequestDtos;


import lombok.Data;

@Data
public class AddProductToCartRequest {

    private Integer productId;
    private Long userId;
    private Integer quantity;
}
