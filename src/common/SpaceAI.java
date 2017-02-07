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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Dylan Russell
 */
public class SpaceAI extends Application {
    
    static final double WIDTH = (2*640), HEIGHT = (2*400);
    Scene scene;
    StackPane root;
    Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    ImageView splashScreenBackplate, splashScreenTextArea;
    Button gameButton, helpButton, scoreButton, legalButton;
    static HBox buttonContainer;
    Insets buttonContainerPadding;  
    GamePlayLoop gamePlayLoop;
    
    @Override
    public void start(Stage primaryStage) {
        //primaryStage.setFullScreen(true);
        createSplashScreenNodes();
        addNodesToStackPane();
        primaryStage.setTitle("SpaceAI");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        gameButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(false);
        });
                
        helpButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(instructionLayer);
        });
        
        scoreButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(scoresLayer);
        });
        
        legalButton.setOnAction((ActionEvent) -> {
            splashScreenBackplate.setVisible(true);
            splashScreenTextArea.setVisible(true);
            splashScreenTextArea.setImage(legalLayer);
        });
        
        gamePlayLoop = new GamePlayLoop();
        gamePlayLoop.start();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void createSplashScreenNodes() {
        root = new StackPane();
        scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        buttonContainer = new HBox(12);
        buttonContainer.setAlignment(Pos.BOTTOM_LEFT);
        buttonContainerPadding = new Insets(0,0,10,16);
        buttonContainer.setPadding(buttonContainerPadding);
        gameButton = new Button();
        gameButton.setText("PLAY GAME");
        helpButton = new Button();
        helpButton.setText("INSTRUCTIONS");
        scoreButton = new Button();
        scoreButton.setText("HIGH SCORES");
        legalButton = new Button();
        legalButton.setText("LEGAL & CREDITS");
        buttonContainer.getChildren().addAll(gameButton, helpButton, scoreButton, legalButton);
        splashScreen = new Image("/SpaceAITitleScreen.png", 1280, 800, true, false, true);
        splashScreenBackplate = new ImageView();
        splashScreenBackplate.setImage(splashScreen);
        instructionLayer = new Image("/invincibagelinstruct.png", (2*640), (2*400), true, false, true);
        splashScreenTextArea = new ImageView();
        splashScreenTextArea.setImage(instructionLayer);
        legalLayer = new Image("/invincibagelcreds.png", (2*640), (2*400), true, false, true);
        scoresLayer = new Image("/invincibagelscores.png", (2*640), (2*400), true, false, true);
    }
    
    private void addNodesToStackPane() {
        root.getChildren().add(splashScreenBackplate);
        root.getChildren().add(splashScreenTextArea);
        root.getChildren().add(buttonContainer);
    }
      
    /*@Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     *
    public static void main(String[] args) {
        //launch(args);
        ShipInfo fighter = new ShipInfo(1, Team.A, ShipType.FIGHTER, new MapLocation(0,0),ShipType.FIGHTER.maxHealth,1,1);
        System.out.println(fighter.type);
    }*/
    
}
