/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dylan Russell
 */
public class SpaceAI extends Application {
 static final double WIDTH = 640, HEIGHT = 400;
    private boolean up, down, left, right, wKey, sKey, aKey, dKey;
    private Scene scene;
    private StackPane root;
    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private ImageView splashScreenBackplate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private HBox buttonContainer;
    private Insets buttonContainerPadding;  
    private GamePlayLoop gamePlayLoop;
    public static Unit testFighter;
    private CastingDirector castDirector;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SpaceAI");
        root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        createSceneEventHandling();
        loadImageAssets();
        createGameActors();
        addGameActorNodes();
        createCastingDirection();
        createSplashScreenNodes();
        addNodesToStackPane();
        createStartGameLoop();       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void createSceneEventHandling() {
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP: up = true; break;
                case DOWN: down = true; break;
                case LEFT: left = true; break;
                case RIGHT: right = true; break;
                case W: wKey = true; break;
                case S: sKey = true; break;
                case A: aKey = true; break;
                case D: dKey = true; break;
            }
        });
        scene.setOnKeyReleased((KeyEvent event) -> {
            switch (event.getCode()) {
                case UP: up = false; break;
                case DOWN: down = false; break;
                case LEFT: left = false; break;
                case RIGHT: right = false; break;
                case W: wKey = false; break;
                case S: sKey = false; break;
                case A: aKey = false; break;
                case D: dKey = false; break;
            }
        });
    }
    
    private void loadImageAssets() {

        splashScreen = new Image("/invincibagelsplash.png", 640, 400, true, false, true);
        instructionLayer = new Image("/invincibagelinstruct.png", 640, 400, true, false, true);
        legalLayer = new Image("/invincibagelcreds.png", 640, 400, true, false, true);
        scoresLayer = new Image("/invincibagelscores.png", 640, 400, true, false, true);      
    }
    
    private void createGameActors() {
        testFighter = new Unit(UnitType.FIGHTER, 1, 0, 0, Team.A);
    }
    
    private void addGameActorNodes() {
        root.getChildren().add(testFighter.spriteFrame);
    }
    
    private void createCastingDirection() {
        castDirector = new CastingDirector();
        castDirector.addCurrentCast(testFighter);
    }
    
    private void createSplashScreenNodes() {
        
        buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_LEFT);
        buttonContainerPadding = new Insets(0,0,10,16);
        buttonContainer.setPadding(buttonContainerPadding);
        
        gameButton = new Button();
        gameButton.setText("PLAY GAME");
        gameButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(false);
            splashScreenTextArea.setVisible(false);
        });
        helpButton = new Button();
        helpButton.setText("INSTRUCTIONS");
        helpButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(instructionLayer);
        });
        scoreButton = new Button();
        scoreButton.setText("HIGH SCORES");
        scoreButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(scoresLayer);
        });
        legalButton = new Button();
        legalButton.setText("LEGAL & CREDITS");
        legalButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(legalLayer);
        });
        
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);
        splashScreenBackplate = new ImageView();
        splashScreenBackplate.setImage(splashScreen);
        splashScreenTextArea = new ImageView();
        splashScreenTextArea.setImage(instructionLayer);
    }
    
    private void addNodesToStackPane() {
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.getChildren().add(buttonContainer);
    }
    
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start(); 
    }

}