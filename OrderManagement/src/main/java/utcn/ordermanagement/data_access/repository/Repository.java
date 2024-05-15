package utcn.ordermanagement.data_access.repository;

import utcn.ordermanagement.data_access.annotations.FK;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

public class Repository<Model, ID> implements IRepository<Model, ID> {
    public final Class<Model> type;

    @SuppressWarnings("unchecked")
    public Repository() {
        this.type = (Class<Model>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public Model save(Model model) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        String[] columns = new String[fields.length];
        Object[] values = new Object[fields.length];
        int index = 0;

        for (Field field : fields) {

            field.setAccessible(true);
            columns[index] = field.getName();

            if (field.isAnnotationPresent(FK.class)) {
                try {
                    Object object = field.get(model);
                    Method getIdMethod = object.getClass().getMethod("getId");
                    values[index] = getIdMethod.invoke(object);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    values[index] = field.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }

            index++;
        }

        Query query = Query
                .insertInto(type, columns)
                .values(values);

        return (query.executeUpdate() == 0) ? null : model;
    }

    @Override
    public Model findById(ID id) throws SQLException {
        return Query
                .select()
                .from(type)
                .where("id = ?", id)
                .firstOrDefault(type);
    }

    @Override
    public List<Model> findAll() throws SQLException {
        return Query
                .select()
                .from(type)
                .toList(type);
    }
}
