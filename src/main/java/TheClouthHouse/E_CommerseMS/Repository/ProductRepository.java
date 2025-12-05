package TheClouthHouse.E_CommerseMS.Repository;

import TheClouthHouse.E_CommerseMS.Entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product , Integer> {
}
