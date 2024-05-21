package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.CrudRepository;
import utcn.ordermanagement.data_access.repository.Query;
import utcn.ordermanagement.models.Client;
import utcn.ordermanagement.models.TopClient;

import java.sql.SQLException;
import java.util.List;

public class ClientRepository extends CrudRepository<Client, Long> implements IClientRepository {
    @Override
    public List<TopClient> findTopClients() throws SQLException {
        String sql = "SELECT client.name AS name, SUM(orders.price) AS price " +
                "FROM client " +
                "JOIN orders ON client.id = orders.client " +
                "GROUP BY client.id " +
                "ORDER BY price DESC " +
                "LIMIT 3;";
        return Query.fromSQLRaw(sql).toList(TopClient.class);
    }
}
