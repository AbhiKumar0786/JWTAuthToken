package TheClouthHouse.E_CommerseMS.Repository;


import TheClouthHouse.E_CommerseMS.Entities.Order;
import TheClouthHouse.E_CommerseMS.Entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    OrderItem findByOrder(Order order);
}
