module com.ism {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok; 
    requires java.sql; 
    requires transitive jakarta.persistence; 
    requires transitive javafx.graphics; 
    requires org.hibernate.orm.core;
    requires javafx.base;
    
    opens com.ism to javafx.fxml;
    opens com.ism.controllers to javafx.fxml;
    // opens com.ism.data.entities to jakarta.persistence, org.hibernate.orm.core;

    opens com.ism.data.entities to org.hibernate.orm.core;

    exports com.ism;
    exports com.ism.data.entities;
    exports com.ism.controllers; 
    exports com.ism.data.repository.interfaces;
    exports com.ism.data.repository.jpa;
    exports com.ism.data.enums;
    exports com.ism.data.services.interfaces;
    exports com.ism.data.services.list;
    exports com.ism.databse;
    exports com.ism.core.Factory;
    exports com.ism.core.Repository;
    exports com.ism.core.Services;
    exports com.ism.core.dataSource;
    exports com.ism.core.view;
}


