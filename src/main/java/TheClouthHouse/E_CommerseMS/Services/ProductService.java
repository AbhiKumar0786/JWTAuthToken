package TheClouthHouse.E_CommerseMS.Services;


import TheClouthHouse.E_CommerseMS.Entities.Product;
import TheClouthHouse.E_CommerseMS.CustomeException.InvalidProductIDException;
import TheClouthHouse.E_CommerseMS.Entities.ProductView;
import TheClouthHouse.E_CommerseMS.Repository.ProductRepository;
import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.GetByPriceRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.UpdateProductRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public String addProduct(AddProductRequest addProductRequest){

        Product product  = Product.builder()
                .productName(addProductRequest.getName())
                .stock(addProductRequest.getStock())
                .price(addProductRequest.getPrice())
                .build();

        product  = productRepository.save(product);

        return "Product has been added to the DB with productId "+product.getId();
    }


    public String UpdateProduct(UpdateProductRequest updateProductRequest) throws Exception{
        Optional<Product> optionalProduct = productRepository.findById(updateProductRequest.getProductId());
        if(optionalProduct.isEmpty())
        {
              throw new InvalidProductIDException("Please Enter Correct Product ID!!");
        }
        
        Product product = optionalProduct.get();
        product.setPrice(updateProductRequest.getNewprice());

        productRepository.save(product);
        
        return "Product price has been updated Successfully!!";
    }

    public List<ProductView> ViewAllProducts(){
        List<Product> allProduct  = productRepository.findAll();
        List<ProductView> productViewList  = new ArrayList<>();

        for(Product p : allProduct){
            ProductView productView  = ProductView.builder()
                    .ProductID(p.getId())
                    .ProductName(p.getProductName())
                    .Price(p.getPrice())
                    .build();

            productViewList.add(productView);
        }
        return productViewList;
    }


    public List<ProductView> getByPrice(GetByPriceRequest getByPriceRequest){
        List<Product> productList = productRepository.findAll();

        List<ProductView> result  = new ArrayList<>();

        for(Product p : productList){
            if(p.getPrice()<= getByPriceRequest.getPrice())
            {
                ProductView productView  = ProductView.builder()
                        .ProductID(p.getId())
                        .ProductName(p.getProductName())
                        .Price(p.getPrice())
                        .build();

                result.add(productView);
            }
        }

        return result;
    }

    @Transactional
    public void reduceStock(Integer productId, int qty) {

        for (int retry = 0; retry < 3; retry++) {

            try {
                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                if (product.getStock() < qty) {
                    throw new RuntimeException("Not enough stock");
                }

                product.setStock(product.getStock() - qty);

                productRepository.save(product);  // triggers optimistic locking

                return;

            } catch (ObjectOptimisticLockingFailureException e) {
                System.out.println("Retry due to concurrent update... Attempt = " + (retry + 1));
            }
        }

        throw new RuntimeException("Too many users buying the same product! Try again.");
    }

}
