package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.ICrudRepository;
import utcn.ordermanagement.models.Order;

public interface IOrderRepository extends ICrudRepository<Order, Long> {
}
