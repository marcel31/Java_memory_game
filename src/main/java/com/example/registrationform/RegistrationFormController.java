package com.example.registrationform;
import com.example.registrationform.model.JsonUserCRUD;
import com.example.registrationform.model.RegistrationFormModel;
import com.example.registrationform.model.User;
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

public class RegistrationFormController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button submitButton;

    // RegistrationFormModel contain the class that helps store the
    private RegistrationFormModel formModel = new RegistrationFormModel();
    private JsonUserCRUD jsonUserCRUD = new JsonUserCRUD();

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        // owner I assume that is the window that contains the scene
        Window owner = submitButton.getScene().getWindow();

        String errorMessage = validateFormFields(formModel);
        // If Validate Form Fields Has no errors then...
        if (errorMessage == null)
        {

            Connection con = null;
            try {
                con = ConnectDB.getInstance();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            String query ="INSERT INTO `memory_game`.usuarios (username, email, password) VALUES\n" +
                        "('" +nameField.getText()+"','"+emailField.getText()+"','"+passwordField.getText()+"');";

            try(
                    Statement stmt = con.createStatement();)
            {
                    stmt.executeUpdate(query);
            }catch(SQLException ex){
                System.out.println("Message: " + ex.getMessage());
            }

            query = "SELECT COUNT(*),username, id AS num_users_validos\n" +
                    "FROM `memory_game`.usuarios\n" +
                    "WHERE email = '"+emailField.getText()+"' AND password = '"+passwordField.getText()+"';\n";
            try(
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);)
            {
                if (rs.next()) {
                        // Login exitoso
                        try {
                            FXMLLoader  loader = new FXMLLoader(getClass().getResource("game.fxml"));
                            Parent root = loader.load();
                            GameController gameController = loader.getController();
                            gameController.recordUserInfo(rs.getString(2),emailField.getText(),passwordField.getText(),rs.getInt(3));

                            // And clean all fields
                            nameField.clear();
                            emailField.clear();
                            passwordField.clear();

                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.setTitle("Memory");
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                }
            }catch(SQLException ex){
            throw new RuntimeException("Error reading records table PRODUCT", ex);
            }
        }
        else{
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Form Error!",errorMessage);
        }

    }


    private String validateFormFields(RegistrationFormModel form) {
        String errorMessage;
        if((errorMessage = RegistrationFormModel.checkNameField(nameField.getText()))!= null)return errorMessage;
        if((errorMessage = RegistrationFormModel.checkEmailField(emailField.getText()))!= null)return errorMessage;
        if((errorMessage = RegistrationFormModel.checkPasswordlField(passwordField.getText()))!= null)return errorMessage;
        return null;
    }


    public void handleUserInformation(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));

            // Establecemos la segunda vista como la vista actual
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleLoginButtonAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));

            // Establecemos la segunda vista como la vista actual
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
