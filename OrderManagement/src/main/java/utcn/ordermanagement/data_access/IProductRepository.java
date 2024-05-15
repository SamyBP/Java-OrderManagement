package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.ICrudRepository;
import utcn.ordermanagement.models.Product;

public interface IProductRepository extends ICrudRepository<Product, Long> {
}
