package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.CrudRepository;
import utcn.ordermanagement.models.Product;

public class ProductRepository extends CrudRepository<Product, Long> implements IProductRepository {
}
