package TheClouthHouse.E_CommerseMS.RequestDtos;


import lombok.Data;

@Data
public class UpdateQuantityRequest {
    private Integer cartItemId;
    private Integer newQuantity;
    private Integer productId;
}
