package utcn.ordermanagement.data_access.repository;

import java.sql.SQLException;
import java.util.List;
/**
 * Interface representing a generic repository for create and read operations.
 *
 * @param <Model> The type of the model/entity managed by the repository.
 * @param <ID>    The type of the identifier of the model/entity.
 */
public interface IRepository<Model, ID> {
    /**
     * Saves the provided model into it's corresponding table
     *
     * @param model the model/entity to be inserted
     * @return the newly inserted model
     * @throws SQLException if an error occurs while inserting into to database
     */
    Model save(Model model) throws SQLException;

    /**
     * Retrieves a Model object from the database
     *
     * @param id the identifier of the model to find
     * @return the found model, or null if not fount
     * @throws SQLException if an error occurs while retrieving the model
     */
    Model findById(ID id) throws SQLException;

    /**
     * Retrieves all models from the corresponding table
     *
     * @return A list of the entries
     * @throws SQLException if an error occurs while retrieving the models
     */
    List<Model> findAll() throws SQLException;
}
