package utcn.ordermanagement.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;
import utcn.ordermanagement.data_access.annotations.FK;
import utcn.ordermanagement.data_access.repository.ICrudRepository;
import utcn.ordermanagement.data_access.annotations.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.List;

/**
 *  A utility class used to render a table in a view
 */
public class TableFactory {

    /**
     * A method used to create the table columns and set the value factory through reflection
     *
     * @param type The type on which to map the table
     * @param tableView The table in which to map the objects
     * @param editable A boolean true if you want to make the table editable or false instead
     * @param <Model> The generic type
     */
    public static <Model> void createTableColumns(Class<Model> type, TableView<Model> tableView, boolean editable) {
        Field[] fields = type.getDeclaredFields();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Id.class)) {
                TableColumn<Model, Object> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                column.setEditable(editable);

                if (field.isAnnotationPresent(FK.class)) {
                    column.setEditable(false);
                    column.setCellValueFactory(cellData -> {
                        Model rowData = cellData.getValue();
                        try {
                            Field clientField = rowData.getClass().getDeclaredField(field.getName());
                            clientField.setAccessible(true);
                            Object object = clientField.get(rowData);
                            return new SimpleObjectProperty<>(object != null ? object.toString() : "");
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                            return new SimpleObjectProperty<>("");
                        }
                    });
                }

                column.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Object>() {
                    @Override
                    public String toString(Object object) {
                        return object.toString();
                    }

                    @Override
                    public Object fromString(String string) {
                        return string;
                    }
                }));

                column.setOnEditCommit(event -> {
                    Model rowData = event.getRowValue();
                    try {
                        Field fieldToUpdate = rowData.getClass().getDeclaredField(field.getName());
                        fieldToUpdate.setAccessible(true);
                        Object newValue = event.getNewValue();
                        boolean empty = newValue == null || newValue.toString().trim().isEmpty();
                        if (field.getType().equals(Double.class) || field.getType().equals(double.class)) {
                            if (empty) {
                                fieldToUpdate.set(rowData, null);
                            } else {
                                fieldToUpdate.set(rowData, Double.parseDouble(newValue.toString()));
                            }
                        } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
                            if (empty) {
                                fieldToUpdate.set(rowData, null);
                            } else {
                                fieldToUpdate.set(rowData, Integer.parseInt(newValue.toString()));
                            }
                        } else {
                            fieldToUpdate.set(rowData, newValue);
                        }
                        tableView.refresh();
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

                tableView.getColumns().add(column);
            }
        }

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setEditable(true);
    }

    public static <Model> void addDeleteButtonColumn(TableView<Model> tableView, Callback<Integer, Void> onDelete) {
        TableColumn<Model, Void> deleteColumn = new TableColumn<>("Delete");

        deleteColumn.setCellFactory(param -> new TableCell<Model, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.getStyleClass().add("delete-button");
                deleteButton.setAlignment(Pos.CENTER);
                deleteButton.setOnAction(event -> {
                    Model rowData = getTableView().getItems().get(getIndex());
                    onDelete.call(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
                setAlignment(Pos.CENTER);
            }
        });

        tableView.getColumns().add(deleteColumn);
    }

    public static <Model> void addUpdateColumn(TableView<Model> tableView, Callback<Integer, Void> onUpdate) {
        TableColumn<Model, Void> updateColumn = new TableColumn<>("Update");

        updateColumn.setCellFactory(param -> new TableCell<Model, Void>() {
            private final Button updateButton = new Button("Update");

            {
                updateButton.getStyleClass().add("update-button");
                updateButton.setAlignment(Pos.CENTER);
                updateButton.setOnAction(event -> {
                    Model rowData = getTableView().getItems().get(getIndex());
                    onUpdate.call(getIndex());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
                setAlignment(Pos.CENTER);
            }
        });

        tableView.getColumns().add(updateColumn);
    }

    /**
     * The method to render the table
     * @param repository An instance of a ICrudRepository representing the repository that will use the find, update, delete methods
     * @param table The table in which to render
     * @param type The type of the model/entity to map
     * @param <Model> The generic type representing the model/entity
     * @param <ID> The generic type representing the identifier's type
     */
    public static <Model, ID> void render(ICrudRepository<Model, ID> repository,
                                          TableView<Model> table,
                                          Class<Model> type) {

        try {
            List<Model> models = repository.findAll();
            if (!models.isEmpty()) {
                TableFactory.createTableColumns(type, table, true);

                TableFactory.addDeleteButtonColumn(table, index -> {

                    Model model = table.getItems().get((int) index);

                    try {
                        Method getIdMethod = model.getClass().getMethod("getId");
                        repository.deleteById((ID) getIdMethod.invoke(model));
                        table.getItems().remove((int) index);
                        AlertPanel.alert("Successfully deleted", Alert.AlertType.CONFIRMATION);
                    } catch (SQLException e) {
                        AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                });

                TableFactory.addUpdateColumn(table, index -> {
                    Model model = table.getItems().get((int) index);
                    try {
                        repository.update(model);
                    } catch (SQLException e) {
                        AlertPanel.alert(e.getMessage(), Alert.AlertType.ERROR);
                    }
                    return null;
                });

                table.getItems().addAll(models);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
