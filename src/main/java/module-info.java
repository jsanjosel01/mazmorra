module com.julia {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.julia to javafx.fxml;
    opens com.julia.controllers to javafx.fxml;
    exports com.julia;
}


