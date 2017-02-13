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
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import world.GameWorld;

/**
 *
 * @author Dylan Russell
 */
public class SpaceAI extends Application {
 static final double WIDTH = 1080, HEIGHT = 800;
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
    public static GameWorld gameWorld;
    
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
        
    }
    
    private void loadImageAssets() {
    
    }
    
    private void createGameActors() {
        testFighter = new Unit(UnitType.FIGHTER, 1, 0, 0, Team.A);
    }
    
    private void addGameActorNodes() {
        root.getChildren().add(testFighter.getSpriteFrame());
    }
    
    private void createCastingDirection() {
        castDirector = new CastingDirector();
        castDirector.addCurrentCast(testFighter);
    }
    
    private void createSplashScreenNodes() {
        
    }
    
    private void addNodesToStackPane() {

    }
    
    private void createStartGameLoop() {
        gameWorld = new GameWorld();
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start(); 
    }

}