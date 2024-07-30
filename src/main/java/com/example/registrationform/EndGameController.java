package com.example.registrationform;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGameController {

    private long tiempo= 0;
    private int puntuacionTotal = 0;
    @FXML
    private Label tiempoTotal;

    @FXML
    private Label puntuacion;
    public void Set(long ntiempo, int npuntuacion)
    {
        tiempoTotal.setText(String.valueOf(ntiempo));
        puntuacion.setText(String.valueOf(npuntuacion));
    }
    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        // owner I assume that is the window that contains the scene

        // Evento que se ejecuta cuando se hace clic en el botón
        try {
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));

            // Establecemos la segunda vista como la vista actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("PLAY");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    protected void handleSubmitButtonExit(ActionEvent event) {
        // owner I assume that is the window that contains the scene

        // Evento que se ejecuta cuando se hace clic en el botón
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));

            // Establecemos la segunda vista como la vista actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Log IN");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
