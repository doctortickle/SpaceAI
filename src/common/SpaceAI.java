/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
 static final double WIDTH = 1080, HEIGHT = 800;
    private boolean up, down, left, right, wKey, sKey, aKey, dKey;
    private Scene scene;
    private StackPane root;
    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private ImageView splashScreenBackplate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private Label teamAMineralCountLabel, teamBMineralCountLabel;
    private Slider speedSlider;
    private int teamAMineralCount, teamBMineralCount;
    private HBox mineralContainer, sliderContainer;
    private Insets mineralContainerPadding;  
    private GamePlayLoop gamePlayLoop;
    private CastingDirector castDirector;
    public static GameWorld gameWorld;
    
    @Override
    public void start(Stage primaryStage) throws ActionException {
        primaryStage.setTitle("SpaceAI");
        root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
        //createSceneEventHandling();
        //loadImageAssets();
        createCastingDirection();
        createGameWorld();
        createGameScreenNodes();
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
    private void createCastingDirection() {
        castDirector = new CastingDirector();
    }
    private void createGameWorld() {
        gameWorld = new GameWorld(this, castDirector);   
    }
    private void createGameScreenNodes() {
            mineralContainer = new HBox(50);
            mineralContainer.setAlignment(Pos.BOTTOM_CENTER);
            mineralContainerPadding = new Insets(0,0,0,0);
            mineralContainer.setPadding(mineralContainerPadding);
            teamAMineralCountLabel = new Label(Integer.toString(gameWorld.getMineralCount(Team.A)));
            teamAMineralCountLabel.setMinWidth(Control.USE_PREF_SIZE);
            teamBMineralCountLabel = new Label(Integer.toString(gameWorld.getMineralCount(Team.B)));
            teamBMineralCountLabel.setMinWidth(Control.USE_PREF_SIZE);
            mineralContainer.getChildren().addAll(teamAMineralCountLabel, teamBMineralCountLabel);
            
            sliderContainer = new HBox(50);
            sliderContainer.setAlignment(Pos.TOP_CENTER);
            speedSlider = new Slider(1, 9, 5);
            speedSlider.setShowTickMarks(true);
            speedSlider.setShowTickLabels(true);
            speedSlider.setMajorTickUnit(1);
            speedSlider.setBlockIncrement(1);
            speedSlider.setSnapToTicks(true);
            sliderContainer.getChildren().addAll(speedSlider);
            
            ChangeListener<Object> updateListener = (obs, oldValue, newValue) -> {
                int speedValue = (int) speedSlider.getValue();
                if(speedValue == 1) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_1);}
                if(speedValue == 2) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_2);}
                if(speedValue == 3) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_3);}
                if(speedValue == 4) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_4);}
                if(speedValue == 5) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_5);}
                if(speedValue == 6) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_6);}
                if(speedValue == 7) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_7);}
                if(speedValue == 8) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_8);}
                if(speedValue == 9) {gameWorld.setGameSpeed(GameConstants.FRAMES_PER_ROUND_9);}
            };
            speedSlider.valueProperty().addListener(updateListener);
    }
    private void addNodesToStackPane() {
        root.getChildren().add(mineralContainer);
        root.getChildren().add(sliderContainer);
    }
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this, gameWorld, castDirector);
        gamePlayLoop.start(); 
    }

    public void update() {
        removeGameActorNodes();
        addGameActorNodes();
        updateMineralCountLabels();
    } 
    private void addGameActorNodes() {
        for(Actor actor : castDirector.getToBeAdded()) {
            actor.getSpriteFrame().setTranslateX(actor.getLocation().getPixelX());
            actor.getSpriteFrame().setTranslateY(actor.getLocation().getPixelY());
            root.getChildren().add(actor.getSpriteFrame());
            if(actor instanceof Unit) {
                castDirector.addCurrentUnit((Unit)actor);
            }
            if(actor instanceof Weapon) {
                castDirector.addCurrentWeapon((Weapon)actor);
            }
            if(actor instanceof Environment) {
                castDirector.addCurrentEnvironment((Environment)actor);
            }
        }
        castDirector.clearToBeAdded();
    }
    private void removeGameActorNodes() {
        for(Actor actor : castDirector.getRemovedActors()) {
            root.getChildren().remove(actor.getSpriteFrame());
        }
        castDirector.resetRemovedActors();
    }    
    private void updateMineralCountLabels() {
        this.teamAMineralCount = gameWorld.getMineralCount(Team.A);
        teamAMineralCountLabel.setText(Integer.toString(teamAMineralCount));
        System.out.println("\nTeam A mineral count - " + teamAMineralCount);
        this.teamBMineralCount = gameWorld.getMineralCount(Team.B);
        teamBMineralCountLabel.setText(Integer.toString(teamBMineralCount));
        System.out.println("Team B mineral count - " + teamBMineralCount);
    }
}