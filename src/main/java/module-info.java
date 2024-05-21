module utcn.ordermanagement {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires jbcrypt;

    opens utcn.ordermanagement to javafx.fxml;
    exports utcn.ordermanagement;
    exports utcn.ordermanagement.controllers;
    exports utcn.ordermanagement.ui;
    exports utcn.ordermanagement.services;
    exports utcn.ordermanagement.models;
    exports utcn.ordermanagement.data_access;
    exports utcn.ordermanagement.data_access.repository;
    opens utcn.ordermanagement.controllers to javafx.fxml;
    exports utcn.ordermanagement.data_access.annotations;
}