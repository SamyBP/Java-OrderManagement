package utcn.ordermanagement.data_access.repository;

import java.sql.SQLException;

/**
 * Interface representing a generic CRUD repository for database operations.
 *
 * @param <Model> The type of the model/entity managed by the repository.
 * @param <ID>    The type of the identifier of the model/entity.
 */
public interface ICrudRepository<Model, ID> extends IRepository<Model, ID>{

    /**
     * Updates the provided model/entity in the repository.
     *
     * @param model The model/entity to update.
     * @return The updated model/entity.
     * @throws SQLException if a database access error occurs.
     */
    Model update(Model model) throws SQLException;

    /**
     * Deletes a model/entity by its identifier from the repository.
     *
     * @param id The identifier of the model/entity to delete.
     * @return true if the deletion was successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    boolean deleteById(ID id) throws SQLException;
}
