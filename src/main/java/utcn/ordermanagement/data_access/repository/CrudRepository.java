package utcn.ordermanagement.data_access.repository;

import utcn.ordermanagement.data_access.annotations.FK;
import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.ReferencedBy;
import utcn.ordermanagement.data_access.annotations.Table;

import java.lang.reflect.Field;
import java.sql.SQLException;


public class CrudRepository<Model, ID> extends Repository<Model, ID> implements ICrudRepository<Model, ID> {

    @Override
    public Model update(Model model) throws SQLException {
        Field[] fields = type.getDeclaredFields();
        int numberOfFields = fields.length;

        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(FK.class)) {
                numberOfFields--;
            }
        }

        String[] columns = new String[numberOfFields];
        Object[] values = new Object[numberOfFields];
        String id = null;
        Object idValue = new Object();
        int index = 0;

        for (Field field : fields) {
            field.setAccessible(true);

            if (field.isAnnotationPresent(Id.class)) {
                id = field.getName();
                try {
                    idValue = field.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            else if (!field.isAnnotationPresent(FK.class)) {
                columns[index] = field.getName();
                try {
                    values[index] = field.get(model);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                index++;
            }
        }

        Query query = Query
                .update(type)
                .set(columns)
                .values(values)
                .where(id + " = ?", idValue);

        return (query.executeUpdate() == 0) ? null : model;
    }

    @Override
    public boolean deleteById(ID id) throws SQLException {

        if (type.isAnnotationPresent(ReferencedBy.class)) {
            Query.delete()
                    .from(type.getAnnotation(ReferencedBy.class).table())
                    .where(type.getAnnotation(Table.class).name() + " = ?", id)
                    .executeUpdate();
        }

        return Query.delete()
                .from(type)
                .where("id = ?", id)
                .executeUpdate() != 0;
    }
}
