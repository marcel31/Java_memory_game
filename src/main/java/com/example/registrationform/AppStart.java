package com.example.registrationform;

import com.jdbc.utilities.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AppStart extends Application {
    static boolean clossedConnection = false;

    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AppStart.class.getResource("registration-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);
        primaryStage.setTitle("Registration form");

        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        boolean timeToQuit = false;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             Connection con = ConnectDB.getInstance();){
            createDB(con);
            createTable(con);
            do {
                launch();
            } while (!clossedConnection);

        } catch (IOException e) {
            System.out.println("Error " + e.getClass().getName() + " , quiting.");
            System.out.println("Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error closing resource " + e.getClass().getName());
            System.out.println("Message: " + e.getMessage());
        }finally{
            try {
                ConnectDB.closeConnection();
            } catch (SQLException e) {
                System.out.println("Error closing resource " + e.getClass().getName());
            }
        }


    }

    private static void createDB(Connection con) throws Exception {
        String sql = "CREATE DATABASE IF NOT EXISTS `memory_game`;\n";
        try(
                Statement stmt = con.createStatement();)
        {
            stmt.executeUpdate(sql);
        }catch(SQLException ex){
            throw new Exception("Error on DB creation:" + ex.getMessage(), ex);
        }
    }

    private static void createTable(Connection con) throws Exception {
        String query ="CREATE TABLE IF NOT EXISTS `memory_game`.usuarios (\n" +
                "    id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                "    username VARCHAR(50) NOT NULL,\n" +
                "    email VARCHAR(100) NOT NULL,\n" +
                "    password VARCHAR(100) NOT NULL\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_spanish_ci;";
        try(
                Statement stmt = con.createStatement();)
        {
            stmt.executeUpdate(query);
        }catch(SQLException ex){
            throw new Exception("Error on table USER creation:" + ex.getMessage(), ex);
        }
    }
}