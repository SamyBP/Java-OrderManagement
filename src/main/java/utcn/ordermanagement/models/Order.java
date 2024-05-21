package utcn.ordermanagement.models;

import utcn.ordermanagement.data_access.annotations.FK;
import utcn.ordermanagement.data_access.annotations.Id;
import utcn.ordermanagement.data_access.annotations.Table;

@Table(name = "orders")
public class Order {
    @Id
    private Long id;
    private int quantity;
    private double price;
    @FK
    private Client client;
    @FK
    private Product product;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", client=" + client +
                ", product=" + product +
                '}';
    }

    public Order() {
    }

    public Order(int quantity, double price, Client client, Product product) {
        this.quantity = quantity;
        this.price = price;
        this.client = client;
        this.product = product;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
