package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.ICrudRepository;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.models.TopClient;

import java.sql.SQLException;
import java.util.List;

public interface IClientRepository extends ICrudRepository<Client, Long> {
    List<TopClient> findTopClients() throws SQLException;
}
