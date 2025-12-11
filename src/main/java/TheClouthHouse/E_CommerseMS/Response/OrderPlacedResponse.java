package TheClouthHouse.E_CommerseMS.Response;


import TheClouthHouse.E_CommerseMS.Entities.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedResponse {
    private String orderId;                // Unique order identifier
    private String message;                // Success message like "Order placed successfully"

    private List<ProductDetail> productDetailList;  // List of all ordered products

    private Double TotalAmount;            // Final amount customer has to pay

    private String paymentStatus;          // PAID / PENDING / COD etc.
    private String orderStatus;            // PLACED / CONFIRMED / SHIPPED etc.

    private LocalDateTime placedAt;               // Human-readable order time
}


