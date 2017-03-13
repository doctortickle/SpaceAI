/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.HashMap;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Dylan Russell
 */
public class SpaceAI extends Application {
    private boolean up, down, left, right, wKey, sKey, aKey, dKey;
    private Scene scene;
    private BorderPane root;
    private StackPane gameScreen;
    private Image splashScreen, instructionLayer, legalLayer, scoresLayer;
    private ImageView splashScreenBackplate, splashScreenTextArea;
    private Button gameButton, helpButton, scoreButton, legalButton;
    private Label teamAMineralCountLabel, teamBMineralCountLabel, teamAMineralCountName, teamBMineralCountName, sliderName,
            IDAttribute, typeAttribute, teamAttribute, healthAttribute, locationAttribute, IDLabel, typeLabel, teamLabel, healthLabel, locationLabel;
    private Slider speedSlider;
    private int teamAMineralCount, teamBMineralCount;
    private VBox mineralContainer, slideAndLabelContainer, leftBox, attributeNameBox, changingAttributes; 
    private HBox mineralCountContainer, mineralNameContainer, rightBox, sliderContainer, sliderNameContainer, unitInfoContainer;
    private Insets mineralContainerPadding,slideAndLabelContainerPadding, unitInfoPadding;  
    private GamePlayLoop gamePlayLoop;
    private CastingDirector castDirector;
    private boolean pause;
    private String styleSheet;
    private GameWorld gameWorld;
    private HashMap<ImageView, Actor> spriteFrameMap = new HashMap<> ();
    private Actor currentSelection = null;
    
    @Override
    public void start(Stage primaryStage) throws ActionException {
        setStage(primaryStage);
        getCSS();
        createSceneEventHandling();
        createCastingDirection();
        createGameWorld();
        addNodesToBorderPane();
        createStartGameLoop();       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    private void setStage(Stage primaryStage) {
        primaryStage.setTitle("SpaceAI");
        root = new BorderPane();
        root.setId("root");
        scene = new Scene(root, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        primaryStage.setMinWidth(GameConstants.WINDOW_WIDTH);
        primaryStage.setMinHeight(GameConstants.WINDOW_HEIGHT+((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2));
        primaryStage.setMaxHeight(GameConstants.WINDOW_HEIGHT+((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void getCSS() {
        styleSheet = "/styleSheet.css";
        scene.getStylesheets().add(styleSheet);
    }
    private void createSceneEventHandling() {
        scene.setOnKeyPressed((KeyEvent event) -> {
            if(event.getCode() == KeyCode.SPACE) {
                pause = !pause;
                if(pause) {
                    gamePlayLoop.stop();
                }
                if(!pause) {
                    gamePlayLoop.start();
                }
            }
        });
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getPickResult().getIntersectedNode() instanceof ImageView) {
                Actor actor = (Actor) spriteFrameMap.get((ImageView) e.getPickResult().getIntersectedNode());
                if(pause) {
                    unitInfoLabelUpdates(actor);
                }
                currentSelection = actor;
            }
        });
    }
    private void createCastingDirection() {
        castDirector = new CastingDirector();
    }
    private void createGameWorld() {
        gameWorld = new GameWorld(this, castDirector);
    }
    private void createBottomNode() {
        mineralCountContainer = new HBox(150);
        mineralCountContainer.setAlignment(Pos.CENTER);
        teamAMineralCountLabel = new Label(Integer.toString(gameWorld.getMineralCount(Team.A)));
        teamAMineralCountLabel.setMinWidth(Control.USE_PREF_SIZE);
        teamBMineralCountLabel = new Label(Integer.toString(gameWorld.getMineralCount(Team.B)));
        teamBMineralCountLabel.setMinWidth(Control.USE_PREF_SIZE);
        mineralCountContainer.getChildren().addAll(teamAMineralCountLabel, teamBMineralCountLabel);
        
        mineralNameContainer = new HBox(50);
        mineralNameContainer.setAlignment(Pos.CENTER);
        teamAMineralCountName = new Label("Team A Mineral Count");
        teamAMineralCountName.setMinWidth(Control.USE_PREF_SIZE);
        teamBMineralCountName = new Label("Team B Mineral Count");
        teamBMineralCountName.setMinWidth(Control.USE_PREF_SIZE);
        mineralNameContainer.getChildren().addAll(teamAMineralCountName, teamBMineralCountName);
        
        mineralContainer = new VBox(5);
        mineralContainerPadding = new Insets(5);
        mineralContainer.setPadding(mineralContainerPadding);
        mineralContainer.setMinWidth(GameConstants.WINDOW_WIDTH);
        mineralContainer.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        mineralContainer.getChildren().addAll(mineralCountContainer, mineralNameContainer);
        
        root.setBottom(mineralContainer);
        BorderPane.setAlignment(mineralContainer, Pos.CENTER);
        root.getBottom().setId("bottom-node");
    }
    private void createTopNode() {
        sliderContainer = new HBox();
        sliderContainer.setMaxWidth(GameConstants.CENTER_WIDTH/2);
        sliderContainer.setAlignment(Pos.CENTER);
        speedSlider = new Slider(1, 9, 5);
        speedSlider.setShowTickMarks(true);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(1);
        speedSlider.setBlockIncrement(1);
        speedSlider.setSnapToTicks(true);
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
        sliderContainer.getChildren().add(speedSlider);

        sliderName = new Label("Game Speed Adjustment");
        
        slideAndLabelContainer = new VBox(5);
        slideAndLabelContainerPadding = new Insets(5);
        slideAndLabelContainer.setPadding(slideAndLabelContainerPadding);
        slideAndLabelContainer.setAlignment(Pos.CENTER);
        slideAndLabelContainer.setMinWidth(GameConstants.WINDOW_WIDTH);
        slideAndLabelContainer.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        slideAndLabelContainer.getChildren().addAll(sliderContainer,sliderName);
        
        root.setTop(slideAndLabelContainer);
        BorderPane.setAlignment(slideAndLabelContainer, Pos.CENTER);
        root.getTop().setId("top-node");
    }
    private void createLeftNode() {
        leftBox = new VBox();
        leftBox.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        
        root.setLeft(leftBox);
        BorderPane.setAlignment(leftBox, Pos.CENTER);
        root.getLeft().setId("left-node");
    }
    private void createRightNode() {
        
        attributeNameBox = new VBox(5);
        changingAttributes = new VBox(5);
        
        IDAttribute = new Label("ID - ");
        typeAttribute = new Label("Type - ");
        teamAttribute = new Label("Team - ");
        healthAttribute = new Label("Health - ");
        locationAttribute = new Label("Location - ");
        attributeNameBox.getChildren().addAll(IDAttribute, typeAttribute, teamAttribute, healthAttribute, locationAttribute);
        
        IDLabel = new Label("None selected");
        typeLabel = new Label("None selected");
        teamLabel = new Label("None selected");
        healthLabel = new Label("None selected");
        locationLabel = new Label("None selected");
        changingAttributes.getChildren().addAll(IDLabel, typeLabel, teamLabel, healthLabel, locationLabel);
        
        unitInfoContainer = new HBox(5);
        unitInfoPadding = new Insets(5);
        unitInfoContainer.setPadding(unitInfoPadding);
        unitInfoContainer.setAlignment(Pos.CENTER);
        unitInfoContainer.getChildren().addAll(attributeNameBox, changingAttributes);
        
        rightBox = new HBox();
        rightBox.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        rightBox.getChildren().addAll(unitInfoContainer);
        
        root.setRight(rightBox);
        BorderPane.setAlignment(rightBox, Pos.CENTER);
        root.getRight().setId("right-node");
    }
    private void createCenterNode() {
        gameScreen = new StackPane();
        gameScreen.setMinSize(GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT);
        gameScreen.setMaxSize(GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT);
        root.setCenter(gameScreen);
        root.getCenter().setId("center-node");
    }
    private void addNodesToBorderPane() {
        createBottomNode();
        createTopNode();
        createLeftNode();
        createRightNode();
        createCenterNode();
    }
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this, gameWorld, castDirector);
        gamePlayLoop.start(); 
    }

    public void update() {
        removeGameActorNodes();
        addGameActorNodes();
        updateMineralCountLabels();
        updateCurrentSelectionLabels();
    } 
    private void addGameActorNodes() {
        for(Actor actor : castDirector.getToBeAdded()) {
            actor.getSpriteFrame().setTranslateX(actor.getLocation().getPixelX());
            actor.getSpriteFrame().setTranslateY(actor.getLocation().getPixelY());
            spriteFrameMap.put(actor.getSpriteFrame(), actor);
            gameScreen.getChildren().add(actor.getSpriteFrame());
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
            spriteFrameMap.remove(actor.getSpriteFrame(), actor);
            gameScreen.getChildren().remove(actor.getSpriteFrame());
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
    private void updateCurrentSelectionLabels() {
        if(currentSelection != null && currentSelection.getHealth() > 0) {
            unitInfoLabelUpdates(currentSelection);
        }
        else {
            IDLabel.setText("None selected");
            typeLabel.setText("None selected");
            teamLabel.setText("None selected");
            healthLabel.setText("None selected");
            locationLabel.setText("None selected");
        }     
    }
    private void unitInfoLabelUpdates(Actor actor) {
        IDLabel.setText(Integer.toString(actor.getID()));
        if(actor instanceof Unit) {
            typeLabel.setText((((Unit) actor).getType()).name());
            healthLabel.setText(Integer.toString(actor.getHealth())); 
        }
        else if(actor instanceof Environment) {
            typeLabel.setText((((Environment) actor).getType()).name());
            healthLabel.setText(Integer.toString(actor.getHealth())); 
        }
        else if(actor instanceof Weapon) {
            typeLabel.setText((((Weapon) actor).getType()).name());
            healthLabel.setText("Not applicable"); 
        }
        teamLabel.setText(actor.getTeam().name()); 
        locationLabel.setText(actor.getLocation().getX()+ ", " + actor.getLocation().getY());
    }
    public GameWorld getGameWorld() {
        return gameWorld;
    }
    
}