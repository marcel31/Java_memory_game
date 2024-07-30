package com.example.registrationform;

import com.example.memory.model.Card;
import com.example.memory.model.Table;
import com.example.registrationform.model.Chrono;
import com.example.registrationform.model.Game;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class GameController {
    public Pane pane;
    public Pane luigi;
    @FXML
    private Button button;

    @FXML
    private Button configButton;

    @FXML
    GridPane grid;

    @FXML
    private Label tiempoTotal;

    @FXML
    private Label puntuacion;

    private long tiempoTotalIntermedio;
    private Chrono chrono = new Chrono();

    private String name  = "";
    private String email = "";
    private String password = "";
    private int id = 0;

    private Game game = null;
    private Font savedFont;
    private Table table = null;
    private String Card1id = null;
    private int round = 0;
    private int puntuacionTotal;
    private boolean alreadyClick = false;
    @FXML
    public void initialize() {


        try {
            if (grid != null)
            {
                setupBoard();
            }
            if ( puntuacion != null)
            {
                puntuacion.setText(String.valueOf(puntuacionTotal));
            }
           if( tiempoTotal != null)
           {
               tiempoTotal.setText(String.valueOf(tiempoTotalIntermedio));
           }

            chrono.iniciar();
        } catch (Exception e) {
            // Handle initialization error
            e.printStackTrace();
        }
    }

    public void recordUserInfo(String name, String email, String password, int id){
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    @FXML
    private void goToConfigSetting(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("config-profile-from.fxml"));
            Parent root = loader.load();
            ProfileConfig profileConfig = loader.getController();
            profileConfig.recordUserInfo(name,email,password,id);


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Memory");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void setupBoard() throws Exception {

        Image imageBg = new Image("TableBackground.jpeg",true);
        Image luigiCasino = new Image("Luigi.gif",true);
        Image card = new Image("CardDs.png",true);

        BackgroundImage bgCard= new BackgroundImage(card,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        BackgroundImage bgImage= new BackgroundImage(imageBg,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));


        //empezar primer board

        int numColumns = grid.getColumnCount();
        int numRows = grid.getRowCount();

        BackgroundImage bgLuigi= new BackgroundImage(luigiCasino,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        luigi.setBackground(new Background(bgLuigi));

        table = new Table(numColumns * numRows);
        pane.setBackground(new Background(bgImage));

        int c = 0;

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                String labelId = "lbl" + i + j + c;
                Label label = (Label) grid.lookup("#" + labelId);
                label.setPrefSize(100,150);
                label.setText(table.cards.get(c).toString());
                label.setBackground(new Background(bgCard));

                c++;


                label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    int arrid = Integer.parseInt(label.getId().substring(5,6));


                    @Override
                    public void handle(MouseEvent event) {

                        if (!alreadyClick) {

                            table.SelectCard(arrid);
                            updateGrid();

                            PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                            pause.setOnFinished(e -> {

                                table.TheyMatch();
                                updateGrid();
                                checkForWinCondition();
                                alreadyClick = false;
                            });
                            if (table.getSecondIndexSelected() != -1){
                                alreadyClick = true;
                                pause.play();
                            }

                        }

                    }
                });
            }
        }
    }

    @FXML
    private void updateGrid(){
        int numColumns = this.grid.getColumnCount();
        int numRows = this.grid.getRowCount();

        int c = 0;

        Image card = new Image("CardDs.png",true);
        BackgroundImage bgCard= new BackgroundImage(card,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));


        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                String labelId = "lbl" + i + j + c;
                Card selectedCard= table.cards.get(c);
                System.out.printf(selectedCard.toString());

                Label label = (Label) grid.lookup("#" + labelId);
                label.setText(selectedCard.toString());
                if (selectedCard.isFliped()){
                    label.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
                    label.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE,CornerRadii.EMPTY, Insets.EMPTY)));
                }else {
                    label.setBackground(new Background(bgCard));
                }

                if (selectedCard.isPaired()){
                    label.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY,CornerRadii.EMPTY, Insets.EMPTY)));

                }
                c++;
            }
        }

    }


    private void createLabelatSpot(int row, int column, int id, String labelId)
    {
        Image card = new Image("CardDs.png",true);
        BackgroundImage bgCard= new BackgroundImage(card,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        Label label = new Label("New Label");
        label.setId(labelId);
        grid.add(label, row, column);
        label.setText(table.cards.get(id).toString());
        Label refObject = (Label) grid.lookup("#lbl000");
        label.setAlignment(Pos.CENTER);
        label.setFont(savedFont);
        label.setPrefSize(100,150);

        label.setBackground(new Background(bgCard));
        label.setOnMouseClicked(new EventHandler<MouseEvent>() {
            int arrid = Integer.parseInt(label.getId().substring(5));


            @Override
            public void handle(MouseEvent event) {
                if (!alreadyClick) {

                    table.SelectCard(arrid);
                    updateGrid();

                    PauseTransition pause = new PauseTransition(Duration.seconds(0.8));
                    pause.setOnFinished(e -> {

                        table.TheyMatch();
                        updateGrid();
                        checkForWinCondition();
                        alreadyClick = false;
                    });
                    if (table.getSecondIndexSelected() != -1){
                        alreadyClick = true;
                        pause.play();
                    }

                }

            }
        });
    }


    @FXML
    private void checkForWinCondition(){

        if (table.didYouWin()){
            chrono.detener();
            long tiempoTranscurrido = chrono.obtenerTiempoTranscurrido();
            System.out.println("Tiempo ronda: "+tiempoTranscurrido);
            tiempoTotalIntermedio += tiempoTranscurrido;
            if (round <= 4 )
            {
                puntuacionTotal += 3000/tiempoTranscurrido;
            } else if ( round > 4 && round <= 9)
            {
                puntuacionTotal += 6000/tiempoTranscurrido;
            } else if (round > 9 && round <= 14) {
                puntuacionTotal += 9000/tiempoTranscurrido;
            } else{
                puntuacionTotal += 12000/tiempoTranscurrido;
            }
            puntuacion.setText( String.valueOf(puntuacionTotal));
            System.out.println("Tiempo Total: "+tiempoTotalIntermedio);
            //ronda acabada
            if (round == 14)
            {
                try {
                    Stage stage = (Stage) puntuacion.getScene().getWindow();
                    // Load the FXML file for the new scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("end-game.fxml"));
                    Parent root = loader.load();
                    EndGameController toEnd =  loader.getController();
                    toEnd.Set(tiempoTotalIntermedio, puntuacionTotal);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                round++;
                aumentarTablero();

                Image card = new Image("CardDs.png",true);
                BackgroundImage bgCard= new BackgroundImage(card,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                        new BackgroundSize(1.0, 1.0, true, true, false, false));


                int numColumns = grid.getColumnCount();
                int numRows = grid.getRowCount();
                int c = 0;
                table.SetUpTable(numColumns*numRows);
                for (int i = 0; i < numColumns; i++) {
                    for (int j = 0; j < numRows; j++) {
                        String labelId = "lbl" + i + j + c;
                        var objectWithId = grid.lookup("#" + labelId);
                        if (objectWithId instanceof Label)
                        {

                            Label label = (Label) grid.lookup("#" + labelId);
                            label.setText(" ");
                            label.setPrefSize(100,150);

                            label.setBackground(new Background(bgCard));
                        } else {
                            createLabelatSpot(i,j,c,labelId);
                        }
                        c++;

                    }
                }
                //nuevo tablero creado
                chrono.reiniciar();


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

    }
    private void aumentarTablero()
    {
        switch (round)
        {
            case 5:
                Label refObject = (Label) grid.lookup("#lbl000");
                savedFont = refObject.getFont();
                grid.getChildren().clear();
                // COMO AÑADIR FILAS Y COULMNAS
                int numcolum = grid.getColumnCount();
                int numRows = grid.getRowCount();

                RowConstraints rowConstraints = grid.getRowConstraints().get(0);
                rowConstraints.setPercentHeight(100.0 / (numRows + 1));// Percentage of the available height4
                ColumnConstraints columnConstraints = grid.getColumnConstraints().get(0);
                columnConstraints.setPercentWidth(100.0/(numcolum+1));
                grid.getRowConstraints().add(rowConstraints);
                grid.getColumnConstraints().add(columnConstraints);

                numcolum = grid.getColumnCount();
                columnConstraints = grid.getColumnConstraints().get(0);
                columnConstraints.setPercentWidth(100.0/(numcolum+1));
                grid.getColumnConstraints().add(columnConstraints);
                break;
            case 10:
                refObject = (Label) grid.lookup("#lbl000");
                savedFont = refObject.getFont();
                grid.getChildren().clear();
                // añadir 1 Fila
                numRows = grid.getRowCount();
                rowConstraints = grid.getRowConstraints().get(0);
                rowConstraints.setPercentHeight(100.0 / (numRows + 1));
                grid.getRowConstraints().add(rowConstraints);
                //añadir Fila 2
                numRows = grid.getRowCount();
                rowConstraints = grid.getRowConstraints().get(0);
                rowConstraints.setPercentHeight(100.0 / (numRows + 1));
                grid.getRowConstraints().add(rowConstraints);
                break;
            case 15:
                refObject = (Label) grid.lookup("#lbl000");
                savedFont = refObject.getFont();
                grid.getChildren().clear();
                // añadir 1 Fila
                numRows = grid.getRowCount();
                rowConstraints = grid.getRowConstraints().get(0);
                rowConstraints.setPercentHeight(100.0 / (numRows + 1));
                grid.getRowConstraints().add(rowConstraints);
                //añadir 1 columna
                numcolum = grid.getColumnCount();
                columnConstraints = grid.getColumnConstraints().get(0);
                columnConstraints.setPercentWidth(100.0/(numcolum+1));
                grid.getColumnConstraints().add(columnConstraints);
                break;
            default:
                break;
        }

    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        // owner I assume that is the window that contains the scene

        // Evento que se ejecuta cuando se hace clic en el botón
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login-form.fxml"));

            // Establecemos la segunda vista como la vista actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Login form");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private GridPane findGridPane(Scene scene) {
        if (scene.getRoot() instanceof GridPane) {
            return (GridPane) scene.getRoot();
        } else {
            return traverse(scene.getRoot());
        }
    }
    private GridPane traverse(javafx.scene.Node node) {
        if (node instanceof GridPane) {
            return (GridPane) node;
        }

        if (node instanceof javafx.scene.Parent) {
            for (javafx.scene.Node child : ((javafx.scene.Parent) node).getChildrenUnmodifiable()) {
                GridPane result = traverse(child);
                if (result != null) {
                    return result;
                }
            }
        }

        return null;
    }

}
