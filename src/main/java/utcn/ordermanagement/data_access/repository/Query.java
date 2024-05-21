package utcn.ordermanagement.data_access.repository;

import utcn.ordermanagement.connection.ConnectionFactory;
import utcn.ordermanagement.data_access.annotations.FK;
import utcn.ordermanagement.data_access.annotations.Table;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class acts as a query builder for database operations
 *
 * @implNote For a valid result use the class as a combination of the public methods
 * Example usage for retrieving the user with a specific username from the table that the model maps
 * User user = Query.select()
 *                  .from(User)
 *                  .where("username = ?", username)
 *                  .firstOrDefault(User)
 */
public class Query {
    private String sql;
    private Connection connection;
    private List<Object> parameters;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private Query(String sql) {
        this.sql = sql;
        this.connection = ConnectionFactory.getConnection();
        this.parameters = new ArrayList<>();
    }

    /**
     * Acts as the start of a select query
     *
     * @param attributes Representing the attributes to retrieve
     * @return a new Query object that will be used in the following methods
     */
    public static Query select(String... attributes) {
        StringBuilder sqlSelect = new StringBuilder("SELECT");

        if (attributes.length == 0) {
            sqlSelect.append(" * ");
        }
        else {
            for (int i = 0; i < attributes.length; i++) {
                sqlSelect.append(attributes[i]);
                sqlSelect.append( (i < attributes.length - 1) ? ", " : " " );
            }
        }

        return new Query(sqlSelect.toString());
    }

    /**
     * Acts as the start of a sql query
     *
     * @param sql Represents the full sql to be executed
     * @return A new Query object used in the following methods
     *
     * @implNote The sql parameter has to be a correct sql statement, following methods allowed are:
     *  toList(Class), firstOrDefault(class)
     */
    public static Query fromSQLRaw(String sql) {
        return new Query(sql);
    }

    /**
     * Represents the continuation of a select or delete query
     *
     * @param clazz Represents the Model class that maps the desired table in the database
     * @return The created Query object from before
     */
    public Query from(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        this.sql += "FROM " + tableName;
        return this;
    }

    /**
     * Lets the builder know to create a WHERE clause
     *
     * @param condition Represents the full WHERE condition
     * @param parameters A variadic array of objects, representing the values from the WHERE clause
     * @return The created Query object from before
     */
    public Query where(String condition, Object... parameters) {
        this.sql += " WHERE " + condition;
        this.parameters.addAll(Arrays.asList(parameters));
        return this;
    }

    /**
     * Acts as the start of an INSERT statement
     *
     * @param clazz A class representing the class that is mapped to a database table
     * @param columns A variadic array of String, representing the columns in which to insert
     * @return A new Query object used in a following method
     */
    public static Query insertInto(Class<?> clazz, String... columns) {
        String tableName = clazz.getAnnotation(Table.class).name();
        StringBuilder sqlInsert = new StringBuilder("INSERT INTO " + tableName);

        if (columns.length != 0) {
            sqlInsert.append("(");
            sqlInsert.append(String.join(",", columns));
            sqlInsert.append(")");
            sqlInsert.append(" VALUES (");
            for (int i = 0; i < columns.length; i++) {
                sqlInsert.append("?");
                if (i < columns.length - 1) {
                    sqlInsert.append(", ");
                }
            }
            sqlInsert.append(")");
        }

        return new Query(sqlInsert.toString());
    }

    /**
     * Acts as a continuation of an insertInto or set method
     *
     * @param values A variadic array of objects representing the desired values to be inserted/set
     * @return The instance created from before
     */
    public Query values(Object... values) {
        this.parameters.addAll(Arrays.asList(values));
        return this;
    }

    /**
     * Acts as the start of an update statement
     *
     * @param clazz A class that is mapped to a table in the database
     * @return A new Query instance
     */
    public static Query update(Class<?> clazz) {
        String tableName = clazz.getAnnotation(Table.class).name();
        String sqlUpdate = "UPDATE " + tableName + " ";

        return new Query(sqlUpdate);
    }

    /**
     * Acts as the continuation of update method
     *
     * @param attributes A variadic array of Strings, representing the columns in to be updated
     * @return The instance created before
     */
    public Query set(String... attributes) {
        if (attributes.length != 0) {
            StringBuilder sqlUpdate = new StringBuilder("SET ");
            for (int i = 0; i < attributes.length; i++) {
                sqlUpdate.append(attributes[i]);
                sqlUpdate.append("=?");
                if (i < attributes.length - 1)
                    sqlUpdate.append(", ");
            }

            this.sql += sqlUpdate.toString();
        }

        return this;
    }

    /**
     * Acts as te start of a DELETE statement
     * @return A new Query instance
     */
    public static Query delete() {
        return new Query("DELETE ");
    }

    /**
     * Acts as the end for a insertInto, update, delete query
     *
     * @return the number of rows affected
     * @throws SQLException if an error occurs when executing the sql statement
     */
    public int executeUpdate() throws SQLException {
        this.statement = connection.prepareStatement(this.sql);
        setParameters(statement);
        int ret = statement.executeUpdate();
        ConnectionFactory.close(statement);
        return ret;
    }

    private ResultSet executeQuery() throws SQLException {
        this.statement = connection.prepareStatement(this.sql);
        setParameters(statement);
        return statement.executeQuery();
    }


    /**
     * Acts as an end of a Query
     *
     * @param clazz A class used to map the result
     * @return A list containing the result of the query
     * @param <Model> The generic type
     * @throws SQLException if an error occurs while executing the query
     */
    public <Model> List<Model> toList(Class<Model> clazz) throws SQLException {
        this.resultSet = executeQuery();

        try {
            List<Model> resultList = new ArrayList<>();

            while (resultSet.next()) {
                Model model = mapToModel(clazz);
                resultList.add(model);
            }

            ConnectionFactory.close(connection);
            ConnectionFactory.close(resultSet);
            return resultList;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Acts as end of a Query
     *
     * @param clazz A Class used to map the result
     * @return An Object of that class type containing the result
     * @param <Model> The generic type
     * @throws SQLException if an error occurs while executing the query
     */
    public <Model> Model firstOrDefault(Class<Model> clazz) throws SQLException {
        this.resultSet = executeQuery();

        if (resultSet.next()) {
            try {
                Model model = mapToModel(clazz);

                ConnectionFactory.close(connection);
                ConnectionFactory.close(resultSet);
                return model;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    private Object getReferenceValue(Field field) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        Class<?> referenceType = field.getType();
        String referenceName = referenceType.getAnnotation(Table.class).name();
        Field[] referenceFields = referenceType.getDeclaredFields();;
        Object referenceValue = referenceType.getConstructor().newInstance();
        String getReferenceSQL = "SELECT * FROM " + referenceName + " WHERE id = ?";
        PreparedStatement getReferenceStatement = connection.prepareStatement(getReferenceSQL);
        getReferenceStatement.setObject(1, resultSet.getObject(field.getName()));
        ResultSet referenceResultSet = getReferenceStatement.executeQuery();

        if (referenceResultSet.next()) {
            for (Field referenceField : referenceFields) {
                referenceField.setAccessible(true);
                referenceField.set(referenceValue, referenceResultSet.getObject(referenceField.getName()));
            }
        }

        return referenceValue;
    }

    private Class<?>[] getParameterTypes(RecordComponent[] components) {
        Class<?>[] parameterTypes = new Class<?>[components.length];
        for (int i = 0; i < components.length; i++) {
            parameterTypes[i] = components[i].getType();
        }
        return parameterTypes;
    }
    private <Model> Model instantiateRecord(Class<Model> clazz, List<Object> values) {
        RecordComponent[] components = clazz.getRecordComponents();
        Object[] args = new Object[components.length];
        for (int i = 0; i < components.length; i++) {
            args[i] = values.get(i);
        }
        try {
            return clazz.getDeclaredConstructor(getParameterTypes(components)).newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
    private <Model> Model mapToModel(Class<Model> clazz) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (clazz.isRecord()) {
            List<Object> values = new ArrayList<>();
            RecordComponent[] recordComponents = clazz.getRecordComponents();

            for (var component : recordComponents) {
                try {
                    Field field = clazz.getDeclaredField(component.getName());
                    field.setAccessible(true);
                    values.add(resultSet.getObject(field.getName()));
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(e);
                }
            }

            return instantiateRecord(clazz, values);

        } else {
            Field[] fields = clazz.getDeclaredFields();
            Model model = clazz.getConstructor().newInstance();
            for (Field field : fields) {
                if (field.isAnnotationPresent(FK.class)) {
                    Object referenceValue = getReferenceValue(field);

                    field.setAccessible(true);
                    field.set(model, referenceValue);

                } else {
                    field.setAccessible(true);
                    field.set(model, resultSet.getObject(field.getName()));
                }
            }
            return model;
        }
    }

    private void setParameters(PreparedStatement statement) throws SQLException {
        if (this.parameters != null) {
            int index = 0;

            for (Object parameter : this.parameters) {
                statement.setObject(++index, parameter);
            }
        }
    }
}
