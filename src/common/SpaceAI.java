/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import static common.DecimalUtils.round;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
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
    private boolean startGame, editorMode;
    private Scene scene;
    private BorderPane root;
    private StackPane gameScreen;
    private Label teamAMineralCountLabel, teamBMineralCountLabel, teamAMineralCountName, teamBMineralCountName, sliderName,
            IDAttribute, typeAttribute, teamAttribute, healthAttribute, locationAttribute, IDLabel, typeLabel, teamLabel, healthLabel, locationLabel,
            mapNameLabel, exportErrorLabel;
    private Slider speedSlider;
    private int teamAMineralCount, teamBMineralCount;
    private VBox leftBox, attributeNameBox, changingAttributes, bottomContainer, topContainer, rightContainer; 
    private HBox mineralCountContainer, mineralNameContainer, sliderContainer, unitInfoContainer;
    private Insets unitInfoPadding, bottomContainerPadding, topContainerPadding;  
    private GamePlayLoop gamePlayLoop;
    private CastingDirector castDirector;
    private boolean pause;
    private String styleSheet;
    private GameWorld gameWorld;
    private HashMap<ImageView, Actor> spriteFrameMap;
    private Actor currentSelection;
    private Map map;
    private Button play, editor, export;
    private ChoiceBox mapSelector;
    private ObservableList<String> mapNames;
    private HashMap<String, File> mapDict;
    private File selectedMap;
    private int selectedSize;
    private TextField mapNameField;
    
    @Override
    public void start(Stage primaryStage) throws ActionException, IOException {
        setStage(primaryStage);
        getCSS();
        addNodesToBorderPane();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    // *********************************
    // ******* APPLICATION START *******
    // *********************************
    
    private void setStage(Stage primaryStage) {
        primaryStage.setTitle("SpaceAI");
        root = new BorderPane();
        root.setId("root");
        scene = new Scene(root, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        primaryStage.setMinWidth(GameConstants.WINDOW_WIDTH);
        primaryStage.setMaxWidth(GameConstants.WINDOW_WIDTH);
        primaryStage.setMinHeight(GameConstants.WINDOW_HEIGHT+((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2));
        primaryStage.setMaxHeight(GameConstants.WINDOW_HEIGHT+((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    private void getCSS() {
        styleSheet = "resources/styleSheet.css";
        scene.getStylesheets().add(styleSheet);
    }
    private void addNodesToBorderPane() throws IOException {
        createBottomNode();
        createTopNode();
        createLeftNode();
        createRightNode();
        createCenterNode();
    }
    private void createBottomNode() {
        bottomContainer = new VBox(5);
        bottomContainerPadding = new Insets(5);
        bottomContainer.setPadding(bottomContainerPadding);
        bottomContainer.setAlignment(Pos.CENTER);
        bottomContainer.setMinWidth(GameConstants.WINDOW_WIDTH);
        bottomContainer.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        root.setBottom(bottomContainer);
        BorderPane.setAlignment(bottomContainer, Pos.CENTER);
        root.getBottom().setId("bottom-node");
    }
    private void createTopNode() {
        topContainer = new VBox(5);
        topContainerPadding = new Insets(5);
        topContainer.setPadding(topContainerPadding);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setMinWidth(GameConstants.WINDOW_WIDTH);
        topContainer.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        root.setTop(topContainer);
        BorderPane.setAlignment(topContainer, Pos.CENTER);
        root.getBottom().setId("top-node");
    }
    private void createRightNode() {
        rightContainer = new VBox();
        rightContainer.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        rightContainer.setMaxWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        rightContainer.setMinHeight(GameConstants.CENTER_HEIGHT);
        rightContainer.setMaxHeight(GameConstants.CENTER_HEIGHT);
        rightContainer.setAlignment(Pos.CENTER);
        
        root.setRight(rightContainer);
        BorderPane.setAlignment(rightContainer, Pos.CENTER);
        root.getRight().setId("right-node");
    }
    private void createLeftNode() throws IOException {
        //PLAY BUTTON
        play = new Button("Start Game");
        play.setFocusTraversable(false);
        play.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(!startGame) {
                try {
                    startGame();
                } catch (IOException ex) {
                    Logger.getLogger(SpaceAI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //MAP SELECTOR
        mapSelector = new ChoiceBox();
        mapSelector.setFocusTraversable(false);
        importMapNames();
        mapSelector.setItems(mapNames);
        selectedMap = mapDict.get(mapNames.get(0));
        mapSelector.getSelectionModel().selectFirst();
        mapSelector.getSelectionModel().selectedIndexProperty()
            .addListener((ObservableValue<? extends Number> observableValue, Number value, Number new_value) -> {
                selectedMap = mapDict.get((String) mapSelector.getItems().get((Integer)new_value));
        });
        //EDITOR BUTTON
        editor = new Button("Map Editor");
        editor.setFocusTraversable(false);
        editor.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(!startGame) {
                editorMode = true;
                editorMode();
            }
        });
        
        leftBox = new VBox(5);
        leftBox.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        leftBox.getChildren().addAll(mapSelector, play, editor);
        leftBox.setAlignment(Pos.CENTER);
        
        root.setLeft(leftBox);
        BorderPane.setAlignment(leftBox, Pos.CENTER);
        root.getLeft().setId("left-node");
    }
    private void createCenterNode() {
        gameScreen = new StackPane();
        gameScreen.setMinSize(GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT);
        gameScreen.setMaxSize(GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT);
        root.setCenter(gameScreen);
        root.getCenter().setId("center-node");
    }
    private void importMapNames() throws FileNotFoundException, IOException {
        mapNames = FXCollections.observableArrayList();
        mapDict = new HashMap<>();
        File dir = new File("src/maps");
        File[] directoryListing = dir.listFiles();
        FileReader fr;
        BufferedReader br;
        if(directoryListing != null) {
            for(File child : directoryListing) {
                fr = new FileReader(child);
                br = new BufferedReader(fr);
                String line = br.readLine();
                String[] array = line.split(":");
                if("mapName".equals(array[0])) {
                    mapNames.add(array[1]);
                    mapDict.put(array[1], child);
                }
            }
            mapNames.sorted();
        }
        else {
            System.out.println("No maps to load.");
        }
    }
    
    // *********************************
    // ********** GAME START ***********
    // *********************************
    
    private void startGame() throws IOException {
        editorMode = false;
        createGameSceneEventHandling();
        createCastingDirection();
        createMap();
        createGameWorld();
        createGameBottomNode();
        createGameTopNode();
        createGameRightNode();
        createStartGameLoop();
        startGame = true; 
    }
    private void createGameSceneEventHandling() {
        spriteFrameMap = new HashMap<>();
        currentSelection = null;
        
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
    private void createMap() throws IOException {
        this.map = new Map(selectedMap);
    }
    private void createGameWorld() {
        gameWorld = new GameWorld(this, castDirector, map);
    }
    private void createGameBottomNode() {
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
        
        bottomContainer.getChildren().addAll(mineralCountContainer, mineralNameContainer);
    }
    private void createGameTopNode() {
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
        
        sliderContainer = new HBox();
        sliderContainer.setMaxWidth(GameConstants.CENTER_WIDTH/2);
        sliderContainer.setAlignment(Pos.CENTER);
        sliderContainer.getChildren().add(speedSlider);
        
        sliderName = new Label("Game Speed Adjustment"); 
        
        topContainer.getChildren().addAll(sliderContainer,sliderName);
    }
    private void createGameRightNode() {
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
        
        rightContainer.getChildren().addAll(unitInfoContainer);
    }
    private void createStartGameLoop() {
        gamePlayLoop = new GamePlayLoop(this, gameWorld, castDirector);
        gamePlayLoop.start(); 
    }
    
    // *********************************
    // ****** GAME ANIMATION ***********
    // *********************************
    
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
        locationLabel.setText(round(actor.getLocation().getX(),3)+ ", " + round(actor.getLocation().getY(),3));
    }
    GameWorld getGameWorld() {
        return gameWorld;
    } 
    
    // *********************************
    // ********* EDITOR MODE ***********
    // *********************************
    
    private void editorMode() {
        createEditorSceneEventHandling();
        createEditorLeftNode();
        createEditorTopNode();
    }
    private void createEditorSceneEventHandling() {
        
    }
    private void createEditorLeftNode() {
        play.setVisible(false);
        mapSelector.setVisible(false);
        editor.setVisible(false);
        //MAP NAME
        mapNameLabel = new Label("Map Name:");
        mapNameField = new TextField ();
        HBox mapNameHB = new HBox();
        mapNameHB.getChildren().addAll(mapNameLabel, mapNameField);
        mapNameHB.setSpacing(10);
        //SIZE SELECTION
        Label sizeSelectorLabel = new Label("Map Size:");
        selectedSize = 2;
        ChoiceBox sizeSelector = new ChoiceBox();
        sizeSelector.setFocusTraversable(false);
        sizeSelector.setItems(FXCollections.observableArrayList("Small", "Medium", "Large"));
        sizeSelector.getSelectionModel().select("Medium");
        sizeSelector.getSelectionModel().selectedIndexProperty()
            .addListener((ObservableValue<? extends Number> observableValue, Number value, Number new_value) -> {
                switch((String) sizeSelector.getItems().get((Integer)new_value)) {
                    case "Small" : selectedSize = 1; break;
                    case "Medium" : selectedSize = 2; break;
                    case "Large" : selectedSize = 3; break;
                };
        });
        HBox sizeSelectorHB = new HBox();
        sizeSelectorHB.getChildren().addAll(sizeSelectorLabel, sizeSelector);
        sizeSelectorHB.setSpacing(10);
        //EXPORT BUTTON
        export = new Button("Export Map");
        export.setFocusTraversable(false);
        export.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                exportMap();
            } catch (IOException ex) {
                Logger.getLogger(SpaceAI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //EXPORT ERROR MESSAGE
        exportErrorLabel = new Label("PLEASE ENTER MAP NAME");
        exportErrorLabel.setVisible(false);
        
        leftBox.getChildren().addAll(mapNameHB, sizeSelectorHB, export, exportErrorLabel);
        
    }
    private void createEditorTopNode() {
        
    }
    private void exportMap() throws IOException {
        String mapFileName = mapNameField.getText().replaceAll("\\s","");
        if(mapFileName.equals("")) {
            exportErrorLabel.setVisible(true);
        }
        else {
            exportErrorLabel.setVisible(false);
            BufferedWriter writer = null;
            File mapFile = new File("src/maps/" + mapFileName + ".txt");
            writer = new BufferedWriter(new FileWriter(mapFile));
            writer.write("mapName:"+ mapNameField.getText());
            writer.newLine();
            writer.write("sizeFactor:"+Integer.toString(selectedSize));
            writer.close();  
        }
    }
}