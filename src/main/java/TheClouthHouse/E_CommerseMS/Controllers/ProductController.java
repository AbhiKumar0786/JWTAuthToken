package TheClouthHouse.E_CommerseMS.Controllers;


import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductRequest;
import TheClouthHouse.E_CommerseMS.Services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
}
