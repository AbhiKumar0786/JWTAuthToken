package TheClouthHouse.E_CommerseMS.RequestDtos;


import lombok.Data;

@Data
public class UpdateProductRequest {

    private Integer productId;
    private Double newprice;
}
