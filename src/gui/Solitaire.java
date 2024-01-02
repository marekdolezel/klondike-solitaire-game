package gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Solitaire extends Application {

    private int gameCounter = 0;
    private VBox mainContainer;
    private MenuBar menuBar;
    private GridPane gamesGrid;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        mainContainer = new VBox();
        mainContainer.setStyle("-fx-background-color: green;");

        gamesGrid = new GridPane();
        gamesGrid.setHgap(10); // Horizontal spacing
        gamesGrid.setVgap(10); // Vertical spacing

        // Adjust row constraints for the second row
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(100); // Move second row down by approx. 50 pixels
        gamesGrid.getRowConstraints().addAll(row1, row2);


        menuBar = new MenuBar();
        setupMenuBar();

        mainContainer.getChildren().addAll(menuBar, gamesGrid);

        createNewGame(); // Create the first game by default

        setupStage(primaryStage);
    }

    private void setupMenuBar() {
        Menu menuNew = new Menu("New");
        MenuItem itemNewTiled = new MenuItem("New Tiled Game");
        itemNewTiled.setOnAction(this::handleNewTiledGame);
        menuNew.getItems().add(itemNewTiled);

        menuBar.getMenus().add(menuNew);
    }

    private void handleNewTiledGame(ActionEvent event) {
        if (gameCounter < 4) {
            createNewGame();
        } else {
            System.out.println("Cannot generate more than four games");
        }
    }

    private void createNewGame() {
        SolitaireGameView gameView = new SolitaireGameView("green");
        int colIndex = gameCounter % 2;
        int rowIndex = gameCounter / 2;
        gamesGrid.add(gameView, colIndex, rowIndex);
        gameCounter++;
        addGameMenu(gameCounter);
    }

    private void addGameMenu(int gameNumber) {
        Menu menuGame = new Menu("Game" + gameNumber);
        Menu itemFile = new Menu("File");
        MenuItem itemLoad = new MenuItem("Load Game");
        MenuItem itemSave = new MenuItem("Save Game");
        itemFile.getItems().addAll(itemLoad, itemSave);

        Menu itemGame = new Menu("Game");
        MenuItem itemUndo = new MenuItem("Undo");
        MenuItem itemHelp = new MenuItem("Move help");
        MenuItem itemNew = new MenuItem("New Game");
        MenuItem itemExit = new MenuItem("Exit Game");
        itemGame.getItems().addAll(itemUndo, itemHelp, itemNew, itemExit);

        menuGame.getItems().addAll(itemFile, itemGame);

        menuBar.getMenus().add(menuGame);
    }


    private void setupStage(Stage primaryStage) {
        Screen screen = Screen.getPrimary();
        primaryStage.setScene(new Scene(mainContainer, screen.getVisualBounds().getWidth(), screen.getVisualBounds().getHeight()));
        primaryStage.show();
    }
}
