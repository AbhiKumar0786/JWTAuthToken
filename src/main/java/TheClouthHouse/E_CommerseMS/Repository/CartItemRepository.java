package TheClouthHouse.E_CommerseMS.Repository;


import TheClouthHouse.E_CommerseMS.Entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
