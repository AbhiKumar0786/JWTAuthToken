package TheClouthHouse.E_CommerseMS.Controllers;


import TheClouthHouse.E_CommerseMS.CustomeException.InvalidProductIDException;
import TheClouthHouse.E_CommerseMS.Entities.Product;
import TheClouthHouse.E_CommerseMS.Entities.ProductView;
import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.GetByPriceRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.UpdateProductRequest;
import TheClouthHouse.E_CommerseMS.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity addProduct(@RequestBody AddProductRequest addProductRequest){
         String result  = productService.addProduct(addProductRequest);

         return new ResponseEntity(result , HttpStatus.OK);
    }



    @PostMapping("/updateProductPrice")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity UpdateProduct(@RequestBody UpdateProductRequest updateProductRequest) throws Exception{
        try {
            String result = productService.UpdateProduct(updateProductRequest);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (InvalidProductIDException e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/ViewAllProducts")
    public ResponseEntity ViewAllProducts(){

        List<ProductView> allProduct  = productService.ViewAllProducts();
        return new ResponseEntity(allProduct , HttpStatus.OK);
    }


    @GetMapping("/getByPrice")
    public  ResponseEntity getByPrice(@RequestBody GetByPriceRequest getByPriceRequest){

        List<ProductView> productList = productService.getByPrice(getByPriceRequest);
        return new ResponseEntity<>(productList , HttpStatus.OK);
    }
}
