package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.CrudRepository;
import utcn.ordermanagement.models.Order;

public class OrderRepository extends CrudRepository<Order, Long> implements IOrderRepository {
}
