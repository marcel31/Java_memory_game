module com.example.registrationform {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires jdbc;
    requires java.sql;


    opens com.example.registrationform to javafx.fxml;
    exports com.example.registrationform;
}