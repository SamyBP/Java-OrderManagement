package utcn.ordermanagement.models;

import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.ReferencedBy;
import utcn.ordermanagement.data_access.annotations.Table;

@Table(name = "product")
@ReferencedBy(table = Order.class)
public class Product {
    @Id
    private Long id;
    private int quantity;
    private String name;
    private double price;

    @Override
    public String toString() {
        return name;
    }

    public Product() {
    }

    public Product(int quantity, String name, double price) {
        this.quantity = quantity;
        this.name = name;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
