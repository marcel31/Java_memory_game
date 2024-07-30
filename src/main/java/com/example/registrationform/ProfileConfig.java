package com.example.registrationform;

import com.jdbc.utilities.ConnectDB;
import com.mysql.jdbc.PreparedStatement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProfileConfig {
    @FXML
    private TextField name_field;

    @FXML
    private TextField email_field;

    @FXML
    private TextField password_field;

    private int idUser;


    @FXML
    public void recordUserInfo(String name, String email, String password, int id){
        name_field.setText(name);
        email_field.setText(email);
        password_field.setText(password);

    }
    @FXML
    protected void handleExitButton(ActionEvent event) {

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("game.fxml"));
            Parent root = loader.load();
            GameController gameController = loader.getController();
            gameController.recordUserInfo(name_field.getText(),email_field.getText(),password_field.getText(),idUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Memory");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void handleSave() {
        // Guardar los nuevos valores de los campos en la entrada
        Connection connection = null;
        try {
            connection = ConnectDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "UPDATE `memory_game`.`usuarios` SET `username`='"+name_field.getText()+"',`email`='"+email_field.getText()+"',`password`='"+password_field.getText()+"' WHERE `id`='"+idUser+"';";

        try {
            String sql = "UPDATE `memory_game`.usuarios SET username = ?, email = ?, password = ? WHERE email = ? AND password = ?";

            PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);
            statement.setString(1, name_field.getText());
            statement.setString(2, email_field.getText());
            statement.setString(3, password_field.getText());
            statement.setString(4, email_field.getText());
            statement.setString(5, password_field.getText());
            // Ejecutar la consulta
            int rowAffected = statement.executeUpdate();
            System.out.println("Row affected " + rowAffected);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
