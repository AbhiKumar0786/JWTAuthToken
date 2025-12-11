package TheClouthHouse.E_CommerseMS.Controllers;


import TheClouthHouse.E_CommerseMS.Entities.OrderHistoryDetail;
import TheClouthHouse.E_CommerseMS.RequestDtos.OrderHistoryRequest;
import TheClouthHouse.E_CommerseMS.RequestDtos.PlaceOrderRequest;
import TheClouthHouse.E_CommerseMS.Response.OrderPlacedResponse;
import TheClouthHouse.E_CommerseMS.Services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/placeOrder")
    public ResponseEntity placeOrder(@RequestBody PlaceOrderRequest placeOrderRequest) throws Exception{
        try {
            OrderPlacedResponse orderPlacedResponse = orderService.placeOrder(placeOrderRequest);
            return new ResponseEntity(orderPlacedResponse, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/getOrderHistory")
    public ResponseEntity getOrderHistory(@RequestBody OrderHistoryRequest orderHistoryRequest) throws Exception{

        try {
            OrderHistoryDetail orderHistoryDetail = orderService.getOrderHistoryDetail(orderHistoryRequest);
            return new ResponseEntity<>(orderHistoryDetail, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_GATEWAY);
        }

    }
}
