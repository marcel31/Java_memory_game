package com.example.registrationform;
import com.example.registrationform.AlertHelper;
import com.example.registrationform.model.*;
import com.jdbc.utilities.ConnectDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    private UserJson userJson = new JsonUserCRUD();

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        Window owner = submitButton.getScene().getWindow();

        // Validar el usuario desde el archivo JSON
        User user = userJson.getUserByUsername(emailField.getText());

        Connection con = null;
        try {
            con = ConnectDB.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "SELECT COUNT(*),username, id AS num_users_validos\n" +
                "FROM `memory_game`.usuarios\n" +
                "WHERE email = '"+emailField.getText()+"' AND password = '"+passwordField.getText()+"';\n";
        try(
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);)
        {
            if (rs.next()) {

                if (rs.getInt(1) > 0) {
                    // Login exitoso
                    try {
                        FXMLLoader  loader = new FXMLLoader(getClass().getResource("game.fxml"));
                        Parent root = loader.load();
                        GameController gameController = loader.getController();
                        gameController.recordUserInfo(rs.getString(2),emailField.getText(),passwordField.getText(),rs.getInt(3));

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Memory");
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.print(user);
                    // Credenciales incorrectas
                    AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Login Error!", "Invalid email or password.");
                }
            }
        }catch(SQLException ex){
            throw new RuntimeException("Error reading records table PRODUCT", ex);
        }




    }

    public void handleRegisterButtonAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("registration-form.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Registration Form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
