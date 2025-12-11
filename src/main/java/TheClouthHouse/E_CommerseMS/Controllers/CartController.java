package TheClouthHouse.E_CommerseMS.Controllers;


import TheClouthHouse.E_CommerseMS.Entities.ProductDetail;
import TheClouthHouse.E_CommerseMS.RequestDtos.AddProductToCartRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.RemoveProductFromCartRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.UpdateQuantityRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.ViewCartDetailsRequest;
import TheClouthHouse.E_CommerseMS.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;



    @PostMapping("/addProductToCart")
    public ResponseEntity addProductToCart(@RequestBody AddProductToCartRequest addProductToCartRequest) throws  Exception{

        try {
            String result = cartService.AddProductToCart(addProductToCartRequest);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }

    }



    @PostMapping("/removeProductFromCart")
    public ResponseEntity removeProductFromCart(@RequestBody RemoveProductFromCartRequest removeProductFromCartRequest) throws Exception{
        try {
            String result = cartService.removeProductFromCart(removeProductFromCartRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/ViewCartDetails")
    public ResponseEntity viewCartDetails(@RequestBody ViewCartDetailsRequest viewCartDetailsRequest) throws Exception{

        try {
            List<ProductDetail> cartItemList = cartService.viewCartDetails(viewCartDetailsRequest);
            return new ResponseEntity(cartItemList, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_GATEWAY);
        }

    }

    @PostMapping("/updateQuantity")
    public ResponseEntity updateQuantity(@RequestBody UpdateQuantityRequest updateQuantityRequest) throws Exception{

        try {
            String result = cartService.UpdateQuantity(updateQuantityRequest);
            return new ResponseEntity(result, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage() , HttpStatus.BAD_GATEWAY);
        }
    }


}
