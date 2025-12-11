package TheClouthHouse.E_CommerseMS.RequestDtos;


import lombok.Data;

@Data
public class AddProductRequest {

    private String name;

    private Double price;

    private Integer stock;
}
