package TheClouthHouse.E_CommerseMS.Services;


import TheClouthHouse.E_CommerseMS.Entities.Product;
import TheClouthHouse.E_CommerseMS.Repository.ProductRepository;
import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addProduct(AddProductRequest addProductRequest){

        Product product  = Product.builder()
                .productName(addProductRequest.getName())
                .price(addProductRequest.getPrice())
                .build();

        product  = productRepository.save(product);

        return "Product has been added to the DB with productId "+product.getId();
    }
}
