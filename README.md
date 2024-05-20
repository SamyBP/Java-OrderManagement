# Warehouse Management Application

This application is tailored for warehouse managers, facilitating essential tasks like client management, order placement, and stock tracking. Managers can efficiently add clients, place orders, and monitor stock levels, streamlining warehouse operations. Built with JavaFX for a user-friendly interface and featuring a custom ORM, it simplifies data access and manipulation.

Upon launching the app, users are greeted with a user-friendly landing scene offering the options to either register or log in. Once logged in, users are directed to the dashboard, providing insightful data on products and clients.

### Dashboard Overview
- **Product Stock Chart**: Visualizes product stock levels, offering at-a-glance insights into inventory status.
- **Top Clients**: Highlights the three best clients based on the total price of their orders, aiding managers in identifying key customers.

### Navigation Sidebar
A convenient sidebar allows seamless navigation to essential sections:
- **Clients Scene**: Manage client information, including adding new clients and updating existing records.
- **Product Scene**: View and manage product details, such as stock levels and product specifications.
- **Order Scene**: Place and track orders, ensuring efficient order management and fulfillment.
- **Bill Scene**: View bills emitted from orders; no editing or deleting functionality to maintain data integrity.

## Quickstart

### Setup
1. **Database Setup**:
   - Run the `dump.sql` script provided with the application to set up the database schema and initial data.
   
2. **Configuration**:
   - Complete the `application.properties` file with your database credentials. This file is essential for the application to connect to your database.

    
Follow these steps to set up the Warehouse Management Application and begin managing clients, orders, and stock effectively.

## ORM 
### Overview

It is desinged using generics and leveraging reflection, providing two useful interfaces:
- **IRepository**: used for create and read operations
- **ICrudRepository**: extends IRepository, used for basic CRUD operations

Additionaly, it uses a custom query builder to enhance database interaction and streamline data retrieval and manipulation. 

### Usage
1. **Defining a model**:
   - Create a class that represents your model
```java
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


    public Order() {
        // No-args constructor required for ORM functionality
    }
    
    // Other constructors
    
    // Getters and Setters required for ORM functionality
}
```
1. **Defining the repository**:
   - Create an interface
```java
public interface IOrderRepository extends ICrudRepository<Order, Long> {
    // define CRUD operations specific for Order
    List<Order> findAllForSpecifiedClient(Client client);
}
```
   - Create a class that implements that interface and extends CrudRepository or Repository, based on the entity's database permisions:
```java
public class OrderRepository extends CrudRepository<Order, Long> implements IOrderRepository {
    // implement the CRUD operations specific for Order, you can use the query builder
    @Override
    public List<Order> findAllForSpecifiedClient(Client client) {
        return Query.select()
                    .from(Order.class)
                    .where("client = ?", client.getId())
                    .toList(Order.class);
    }
}
```
    


