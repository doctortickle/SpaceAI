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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import javafx.scene.effect.Lighting;
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
    private boolean startGame, pause, editorMode, delete, add;
    private Scene scene;
    private BorderPane root;
    private StackPane gameScreen;
    private Label teamAMineralCountLabel, teamBMineralCountLabel, teamAMineralCountName, teamBMineralCountName, sliderName,
            IDAttribute, typeAttribute, teamAttribute, healthAttribute, locationAttribute, IDLabel, typeLabel, teamLabel, healthLabel, locationLabel,
            mapNameLabel, exportErrorLabel, fuelLabel, mineralLabel, fuelAttribute, mineralAttribute;
    private Slider speedSlider;
    private int teamAMineralCount, teamBMineralCount;
    private VBox leftBox, attributeNameBox, changingAttributes, bottomBox, topBox, rightBox; 
    private HBox mineralCountContainer, mineralNameContainer, sliderContainer, unitInfoContainer, mapNameHB, sizeSelectorHB,
            editorActorSelectorHB, mineralSelectorHB;
    private Insets unitInfoPadding, bottomBoxPadding, topBoxPadding;  
    private GamePlayLoop gamePlayLoop;
    private CastingDirector castDirector;
    private String styleSheet;
    private GameWorld gameWorld;
    private Actor currentSelection;
    private Map map;
    private Button play, editor, export, addButton, deleteButton, mainMenuButton, clearButton;
    private ChoiceBox mapSelector;
    private ObservableList<String> mapNames;
    private HashMap<String, File> mapDict;
    private HashMap<ImageView, Actor> spriteFrameDict;
    private HashMap<ImageView, EditorActor> spriteFrameDictEditor;
    private File selectedMap;
    private int selectedSize, mineralCount;
    private TextField mapNameField;
    private EditorActor selectedEditorActor;
    private List<EditorActor> addedEditorActors;
    private ImageView titleScreenFrame;
    private Image titleScreen;
    
    @Override
    public void start(Stage primaryStage) throws ActionException, IOException {
        setStage(primaryStage);
        getCSS();
        addNodesToBorderPane();
        mainMenu();
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
    private void addNodesToBorderPane() {
        createBottomNode();
        createTopNode();
        createLeftNode();
        createRightNode();
        createCenterNode();
    }
    private void createBottomNode() {
        bottomBox = new VBox(5);
        bottomBoxPadding = new Insets(5);
        bottomBox.setPadding(bottomBoxPadding);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setMinWidth(GameConstants.WINDOW_WIDTH);
        bottomBox.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        root.setBottom(bottomBox);
        BorderPane.setAlignment(bottomBox, Pos.CENTER);
        root.getBottom().setId("bottom-node");
    }
    private void createTopNode() {
        topBox = new VBox(5);
        topBoxPadding = new Insets(5);
        topBox.setPadding(topBoxPadding);
        topBox.setAlignment(Pos.CENTER);
        topBox.setMinWidth(GameConstants.WINDOW_WIDTH);
        topBox.setMinHeight((GameConstants.WINDOW_HEIGHT-GameConstants.CENTER_HEIGHT)/2);
        root.setTop(topBox);
        BorderPane.setAlignment(topBox, Pos.CENTER);
        root.getBottom().setId("top-node");
    }
    private void createRightNode() {
        rightBox = new VBox();
        rightBox.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        rightBox.setMaxWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        rightBox.setMinHeight(GameConstants.CENTER_HEIGHT);
        rightBox.setMaxHeight(GameConstants.CENTER_HEIGHT);
        rightBox.setAlignment(Pos.CENTER);
        
        root.setRight(rightBox);
        BorderPane.setAlignment(rightBox, Pos.CENTER);
        root.getRight().setId("right-node");
    }
    private void createLeftNode() {
        leftBox = new VBox(5);
        leftBox.setMinWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        leftBox.setMaxWidth((GameConstants.WINDOW_WIDTH-GameConstants.CENTER_WIDTH)/2);
        leftBox.setMinHeight(GameConstants.CENTER_HEIGHT);
        leftBox.setMaxHeight(GameConstants.CENTER_HEIGHT);
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
    
    // *********************************
    // ********** MAIN MENU ************
    // *********************************
    
    private void mainMenu() throws IOException {
        initializeMainMenu();
        createMainTopNode();
        createMainBottomNode();
        createMainLeftNode();
        createMainRightNode();
        createTitleScreen();
    }
    private void initializeMainMenu() {
        editorMode = false;
        startGame = false;
    }
    private void createMainTopNode() {
        clearTopNode();
    }
    private void createMainBottomNode() {
        clearBottomNode();
    }
    private void createMainLeftNode() throws IOException {
        clearLeftNode();
        createPlayButton();
        createMapSelector();
        createEditorButton();
        leftBox.getChildren().addAll(mapSelector, play, editor);
    }
    private void createMainRightNode() {
        clearRightNode();
    }
    private void createTitleScreen() {
        clearGameScreen();
        titleScreen = new Image("resources/SpaceAITitleScreen.png", GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT, true, false, true);
        titleScreenFrame = new ImageView(titleScreen);
        gameScreen.getChildren().add(titleScreenFrame);
    }
    private void createPlayButton() {
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
    }
    private void createMapSelector() throws IOException {
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
    }
    private void createEditorButton() {
        editor = new Button("Map Editor");
        editor.setFocusTraversable(false);
        editor.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(!startGame) {
                editorMode();
            }
        });
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
        createGameLeftNode();
        createGameScreen();
        createStartGameLoop();
        startGame = true; 
    }
    private void createGameSceneEventHandling() {
        spriteFrameDict = new HashMap<>();
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
        gameScreen.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(e.getPickResult().getIntersectedNode() instanceof ImageView) {
                Actor actor = (Actor) spriteFrameDict.get((ImageView) e.getPickResult().getIntersectedNode());
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
    private void createGameTopNode() {
        clearTopNode();
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
        
        topBox.getChildren().addAll(sliderContainer,sliderName);
    }
    private void createGameBottomNode() {
        clearBottomNode();
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
        
        bottomBox.getChildren().addAll(mineralCountContainer, mineralNameContainer);
    }
    private void createGameLeftNode() {
        clearLeftNode();
        createMainMenuButton();
        leftBox.getChildren().addAll(mainMenuButton);
    }
    private void createGameRightNode() {
        clearRightNode();
        attributeNameBox = new VBox(5);
        changingAttributes = new VBox(5);
        
        IDAttribute = new Label("ID - ");
        typeAttribute = new Label("Type - ");
        teamAttribute = new Label("Team - ");
        healthAttribute = new Label("Health - ");
        fuelAttribute = new Label("Fuel - ");
        mineralAttribute = new Label("Minerals - ");
        locationAttribute = new Label("Location - ");
        attributeNameBox.getChildren().addAll(IDAttribute, typeAttribute, teamAttribute, healthAttribute, fuelAttribute, mineralAttribute, locationAttribute);
        
        IDLabel = new Label("None selected");
        typeLabel = new Label("None selected");
        teamLabel = new Label("None selected");
        healthLabel = new Label("None selected");
        fuelLabel = new Label("None selected");
        mineralLabel = new Label("None selected");
        locationLabel = new Label("None selected");
        changingAttributes.getChildren().addAll(IDLabel, typeLabel, teamLabel, healthLabel, fuelLabel, mineralLabel, locationLabel);
        
        unitInfoContainer = new HBox(5);
        unitInfoPadding = new Insets(5);
        unitInfoContainer.setPadding(unitInfoPadding);
        unitInfoContainer.setAlignment(Pos.CENTER);
        unitInfoContainer.getChildren().addAll(attributeNameBox, changingAttributes);
        
        rightBox.getChildren().addAll(unitInfoContainer);
    }
    private void createGameScreen() {
        clearGameScreen();
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
            if(actor instanceof Unit) {
                actor.getSpriteFrame().setFitHeight(((Unit) actor).getType().getBodyRadius()*map.getCoordinateToPixel()*2);
                actor.getSpriteFrame().setFitWidth(((Unit) actor).getType().getBodyRadius()*map.getCoordinateToPixel()*2);
                castDirector.addCurrentUnit((Unit)actor);
            }
            if(actor instanceof Weapon) {
                actor.getSpriteFrame().setFitHeight(((Weapon) actor).getType().getWeaponRadius()*map.getCoordinateToPixel()*2);
                actor.getSpriteFrame().setFitWidth(((Weapon) actor).getType().getWeaponRadius()*map.getCoordinateToPixel()*2);
                castDirector.addCurrentWeapon((Weapon)actor);
            }
            if(actor instanceof Environment) {
                actor.getSpriteFrame().setFitHeight(((Environment) actor).getType().getBodyRadius()*map.getCoordinateToPixel()*2);
                actor.getSpriteFrame().setFitWidth(((Environment) actor).getType().getBodyRadius()*map.getCoordinateToPixel()*2);
                castDirector.addCurrentEnvironment((Environment)actor);
            }
            spriteFrameDict.put(actor.getSpriteFrame(), actor);
            gameScreen.getChildren().add(actor.getSpriteFrame());
        }
        castDirector.clearToBeAdded();
    }
    private void removeGameActorNodes() {
        for(Actor actor : castDirector.getRemovedActors()) {
            spriteFrameDict.remove(actor.getSpriteFrame(), actor);
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
            fuelLabel.setText("None selected");
            mineralLabel.setText("None selected");
            locationLabel.setText("None selected");
        }     
    }
    private void unitInfoLabelUpdates(Actor actor) {
        IDLabel.setText(Integer.toString(actor.getID()));
        if(actor instanceof Unit) {
            typeLabel.setText((((Unit) actor).getType()).name());
            healthLabel.setText(Integer.toString(actor.getHealth()));
            fuelLabel.setText(Integer.toString(((Unit) actor).getFuel()));
            mineralLabel.setText("Not applicable");
        }
        else if(actor instanceof Environment) {
            typeLabel.setText((((Environment) actor).getType()).name());
            healthLabel.setText(Integer.toString(actor.getHealth())); 
            fuelLabel.setText("Not applicable");
            mineralLabel.setText(Integer.toString(((Environment) actor).getMineralCount()));
        }
        else if(actor instanceof Weapon) {
            typeLabel.setText((((Weapon) actor).getType()).name());
            healthLabel.setText("Not applicable");
            fuelLabel.setText("Not applicable");
            mineralLabel.setText("Not applicable");
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
        initializeEditorMode();
        createEditorTopNode();
        createEditorBottomNode();
        createEditorLeftNode();
        createEditorRightNode();
        createEditorTopNode();
        createEditorGameScreen();
        createEditorSceneEventHandling();
    }
    private void initializeEditorMode() {
        addedEditorActors = new ArrayList<>();
        spriteFrameDictEditor = new HashMap<>();
        editorMode = true;
        add = true;
        delete = false;
    }
    private void createEditorTopNode() {
        clearTopNode();
    }
    private void createEditorBottomNode() {
        clearBottomNode();
    }
    private void createEditorLeftNode() {
        clearLeftNode();
        createMainMenuButton();
        createMapNameEntry();
        createSizeSelection();
        createAddButton();
        createDeleteButton();
        createClearButton();
        createEditorActorSelection();
        createInitialMineralCountSelection();
        createExportButton();
        createExportErrorLabel();
        leftBox.getChildren().addAll(mainMenuButton, mapNameHB, sizeSelectorHB, 
                addButton, deleteButton, clearButton, editorActorSelectorHB, 
                mineralSelectorHB, export, exportErrorLabel); 
    }
    private void createEditorRightNode() {
        clearRightNode();
    }
    private void createEditorGameScreen() {
        clearGameScreen();
    }
    private void createEditorSceneEventHandling() {
        gameScreen.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            if(!(e.getPickResult().getIntersectedNode() instanceof ImageView) && add) {
                selectedEditorActor.getSpriteFrame().setTranslateX(convertXCoordinate(e.getX()));
                selectedEditorActor.getSpriteFrame().setTranslateY(convertYCoordinate(e.getY()));
                if(assertOnScreen() && assertNoCollision() && assertHomeStationNumbers() && assertNoMirrorEditorActorCollision()) {
                    addEditorActor();
                }
            }
            if(e.getPickResult().getIntersectedNode() instanceof ImageView && delete) {
                EditorActor editorActor = (EditorActor) spriteFrameDictEditor.get((ImageView) e.getPickResult().getIntersectedNode());
                EditorActor mirrorEditorActor = editorActor.getMirroredEditorActor();
                removeEditorActor(editorActor);
                removeEditorActor(mirrorEditorActor);
            }
        });
    }
    private void createMapNameEntry() {
        mapNameLabel = new Label("Map Name:");
        mapNameField = new TextField ();
        mapNameHB = new HBox();
        mapNameHB.getChildren().addAll(mapNameLabel, mapNameField);
        mapNameHB.setSpacing(10);
    }
    private void createSizeSelection() {
        Label sizeSelectorLabel = new Label("Map Size:");
        selectedSize = 2;
        ChoiceBox sizeSelector = new ChoiceBox();
        sizeSelector.setFocusTraversable(false);
        sizeSelector.setItems(FXCollections.observableArrayList("Small", "Medium", "Large"));
        sizeSelector.getSelectionModel().select("Medium");
        sizeSelector.getSelectionModel().selectedIndexProperty()
            .addListener((ObservableValue<? extends Number> observableValue, Number value, Number new_value) -> {
                switch((String) sizeSelector.getItems().get((Integer)new_value)) {
                    case "Large" : selectedSize = 1; break;
                    case "Medium" : selectedSize = 2; break;
                    case "Small" : selectedSize = 3; break;
                }
                adjustEditorActorSizes();
        });
        sizeSelectorHB = new HBox();
        sizeSelectorHB.getChildren().addAll(sizeSelectorLabel, sizeSelector);
        sizeSelectorHB.setSpacing(10);
    }
    private void createAddButton() {
        addButton = new Button("Add Actors");
        addButton.setFocusTraversable(false);
        addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            add = true;
            delete = false;
        });
    }
    private void createDeleteButton() {
        deleteButton = new Button("Delete Actors");
        deleteButton.setFocusTraversable(false);
        deleteButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            add = false;
            delete = true;
        });
    }
    private void createClearButton() {
        clearButton = new Button("Clear Actors");
        clearButton.setFocusTraversable(false);
        clearButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            addedEditorActors.clear();
            spriteFrameDictEditor.clear();
            clearGameScreen();
        });
    }
    private void createEditorActorSelection() {
        Label editorActorSelectorLabel = new Label("Actor Selection:");
        selectedEditorActor = new EditorActor(UnitType.HOME_STATION, false);
        ChoiceBox editorActorSelector = new ChoiceBox();
        editorActorSelector.setFocusTraversable(false);
        editorActorSelector.setItems(FXCollections.observableArrayList("Home Station (A)", "Small Asteroid", "Large Asteroid",
                "Small Meteor", "Large Meteor", "Small Planet", "Large Planet"));
        editorActorSelector.getSelectionModel().selectFirst();
        editorActorSelector.getSelectionModel().selectedIndexProperty()
            .addListener((ObservableValue<? extends Number> observableValue, Number value, Number new_value) -> {
                switch((String) editorActorSelector.getItems().get((Integer)new_value)) {
                    case "Home Station (A)" : selectedEditorActor.setType(UnitType.HOME_STATION); break;
                    case "Small Asteroid" : selectedEditorActor.setType(EnvironmentType.SMALL_ASTEROID); break;
                    case "Large Asteroid" : selectedEditorActor.setType(EnvironmentType.LARGE_ASTEROID); break;
                    case "Small Meteor" : selectedEditorActor.setType(EnvironmentType.SMALL_METEOR); break;
                    case "Large Meteor" : selectedEditorActor.setType(EnvironmentType.LARGE_METEOR); break;
                    case "Small Planet" : selectedEditorActor.setType(EnvironmentType.SMALL_PLANET); break;
                    case "Large Planet" : selectedEditorActor.setType(EnvironmentType.LARGE_PLANET); break;
                }
        });
        editorActorSelectorHB = new HBox();
        editorActorSelectorHB.getChildren().addAll(editorActorSelectorLabel, editorActorSelector);
        editorActorSelectorHB.setSpacing(10);
    }
    private void createInitialMineralCountSelection() {
        Label mineralSelectorLabel = new Label("Mineral Count:");
        mineralCount = 1000;
        ChoiceBox mineralSelector = new ChoiceBox();
        mineralSelector.setFocusTraversable(false);
        mineralSelector.setItems(FXCollections.observableArrayList("500", "1000", "1500", "2000", "2500", "3000", "3500", "4000", "4500", "5000"));
        mineralSelector.getSelectionModel().select("1000");
        mineralSelector.getSelectionModel().selectedIndexProperty()
            .addListener((ObservableValue<? extends Number> observableValue, Number value, Number new_value) -> {
                switch((String) mineralSelector.getItems().get((Integer)new_value)) {
                    case "500" : mineralCount = 500; break;
                    case "1000" : mineralCount = 1000; break;
                    case "1500" : mineralCount = 1500; break;
                    case "2000" : mineralCount = 2000; break;
                    case "2500" : mineralCount = 2500; break;
                    case "3000" : mineralCount = 3000; break;
                    case "3500" : mineralCount = 3500; break;
                    case "4000" : mineralCount = 4000; break;
                    case "4500" : mineralCount = 4500; break;
                    case "5000" : mineralCount = 5000; break;
                }
        });
        mineralSelectorHB = new HBox();
        mineralSelectorHB.getChildren().addAll(mineralSelectorLabel, mineralSelector);
        mineralSelectorHB.setSpacing(10);
    }
    private void createExportButton() {
        export = new Button("Export Map");
        export.setFocusTraversable(false);
        export.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                exportMap();
            } catch (IOException ex) {
                Logger.getLogger(SpaceAI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    private void createExportErrorLabel() {
        exportErrorLabel = new Label("PLEASE ENTER MAP NAME");
        exportErrorLabel.setVisible(false);
    }
    private void addEditorActor() {
        EditorActor mirrorEditorActor = mirrorEditorActor();
        selectedEditorActor.getSpriteFrame().setFitWidth(selectedEditorActor.getRadius()*(selectedSize/1)*2);
        selectedEditorActor.getSpriteFrame().setFitHeight(selectedEditorActor.getRadius()*(selectedSize/1)*2);
        mirrorEditorActor.getSpriteFrame().setFitWidth(selectedEditorActor.getRadius()*(selectedSize/1)*2);
        mirrorEditorActor.getSpriteFrame().setFitHeight(selectedEditorActor.getRadius()*(selectedSize/1)*2);
        gameScreen.getChildren().addAll(selectedEditorActor.getSpriteFrame(), mirrorEditorActor.getSpriteFrame());
        addedEditorActors.add(selectedEditorActor);
        addedEditorActors.add(mirrorEditorActor);
        spriteFrameDictEditor.put(selectedEditorActor.getSpriteFrame(), selectedEditorActor);
        spriteFrameDictEditor.put(mirrorEditorActor.getSpriteFrame(), mirrorEditorActor);
        selectedEditorActor.setMirroredEditorActor(mirrorEditorActor);
        mirrorEditorActor.setMirroredEditorActor(selectedEditorActor);
        selectedEditorActor = new EditorActor(selectedEditorActor.getType(), false);
    }
    private void removeEditorActor(EditorActor editorActor) {
        addedEditorActors.remove(editorActor);
        spriteFrameDictEditor.remove(editorActor.getSpriteFrame());
        gameScreen.getChildren().remove(editorActor.getSpriteFrame());
    }
    private double convertXCoordinate(double coordinate) {
            return coordinate - GameConstants.CENTER_WIDTH/2;
    }
    private double convertYCoordinate(double coordinate) {
            return coordinate - GameConstants.CENTER_HEIGHT/2;
    }
    private boolean assertOnScreen() {
        if(selectedEditorActor.getSpriteFrame().getTranslateY() >= GameConstants.CENTER_HEIGHT/2 - selectedEditorActor.getRadius()*(selectedSize/1)) { return false; }
        if(selectedEditorActor.getSpriteFrame().getTranslateY() <= -GameConstants.CENTER_HEIGHT/2 + selectedEditorActor.getRadius()*(selectedSize/1)) { return false; }
        if(selectedEditorActor.getSpriteFrame().getTranslateX() >= GameConstants.CENTER_WIDTH/2 - selectedEditorActor.getRadius()*(selectedSize/1)) { return false; }
        if(selectedEditorActor.getSpriteFrame().getTranslateX() <= -GameConstants.CENTER_WIDTH/2 + selectedEditorActor.getRadius()*(selectedSize/1)) { return false; }
        return true;
    }
    private boolean assertNoCollision() {
        if(addedEditorActors.size() > 0) {
            for(EditorActor editActor : addedEditorActors) {
                double dx = selectedEditorActor.getSpriteFrame().getTranslateX() - editActor.getSpriteFrame().getTranslateX();
                double dy = selectedEditorActor.getSpriteFrame().getTranslateY() - editActor.getSpriteFrame().getTranslateY();
                if(round(Math.sqrt(dx * dx + dy * dy),14) < selectedEditorActor.getRadius()*(selectedSize/1) + editActor.getRadius()*(selectedSize/1)) {
                    return false;
                }                   
            }
        }
        return true;
    }
    private boolean assertHomeStationNumbers() {
        if(selectedEditorActor.getType() == UnitType.HOME_STATION) {
            if(addedEditorActors.size() > 0) {
                for(EditorActor editActor : addedEditorActors) {
                    if(editActor.getType() == UnitType.HOME_STATION) {
                        System.out.println("Only one Homestation per team allowed. Please remove previously place Home Station.");
                        return false;
                    }
                }
            }   
        }
        return true;
    }
    private boolean assertNoMirrorEditorActorCollision() {
        double mirrorX = -selectedEditorActor.getSpriteFrame().getTranslateX();
        double mirrorY = -selectedEditorActor.getSpriteFrame().getTranslateY();
        return assertNoCollision(mirrorX, mirrorY);
    }
    private boolean assertNoCollision(double mirrorX, double mirrorY) {
        double dx = selectedEditorActor.getSpriteFrame().getTranslateX() - mirrorX;
        double dy = selectedEditorActor.getSpriteFrame().getTranslateY() - mirrorY;
        if(round(Math.sqrt(dx * dx + dy * dy),14) < (selectedEditorActor.getRadius()*(selectedSize/1))*2) {
            return false;
        }                   
        return true;
    }
    private EditorActor mirrorEditorActor() {
        double mirrorX = -selectedEditorActor.getSpriteFrame().getTranslateX();
        double mirrorY = -selectedEditorActor.getSpriteFrame().getTranslateY();
        EditorActor mirrorEditorActor = new EditorActor(selectedEditorActor.getType(), true);
        mirrorEditorActor.getSpriteFrame().setTranslateX(mirrorX);
        mirrorEditorActor.getSpriteFrame().setTranslateY(mirrorY);
               
        return mirrorEditorActor;
    }
    private void adjustEditorActorSizes() {
        addedEditorActors.stream().map((editorActor) -> {
            editorActor.getSpriteFrame().setFitWidth(editorActor.getRadius()*(selectedSize/1)*2);
            return editorActor;
        }).forEachOrdered((editorActor) -> {
            editorActor.getSpriteFrame().setFitHeight(editorActor.getRadius()*(selectedSize/1)*2);
        });
    }
    private void exportMap() throws IOException {
        String mapFileName = mapNameField.getText().replaceAll("\\s","");
        if(mapFileName.equals("")) {
            exportErrorLabel.setVisible(true);
        }
        else if (validateMap()) {
            exportErrorLabel.setVisible(false);
            BufferedWriter writer = null;
            File mapFile = new File("src/maps/" + mapFileName + ".txt");
            writer = new BufferedWriter(new FileWriter(mapFile));
            writer.write("mapName:"+ mapNameField.getText());
            writer.newLine();
            writer.write("sizeFactor:"+Integer.toString(selectedSize));
            writer.newLine();
            for(EditorActor editorActor : addedEditorActors) {
                if(editorActor.getType() instanceof EnvironmentType) {
                    writer.write("environment:"+editorActor.getType()+"/"+editorActor.getSpriteFrame().getTranslateX()*(1d/selectedSize)+"/"+
                            (-editorActor.getSpriteFrame().getTranslateY()*(1d/selectedSize)));
                }
                else if(editorActor.getType() == UnitType.HOME_STATION) {
                    if(!editorActor.isMirror()) {
                        writer.write("homeStationA:"+editorActor.getSpriteFrame().getTranslateX()*(1d/selectedSize)+"/"+
                            (-editorActor.getSpriteFrame().getTranslateY()*(1d/selectedSize)));
                    }
                    else {
                        writer.write("homeStationB:"+editorActor.getSpriteFrame().getTranslateX()*(1d/selectedSize)+"/"+
                            (-editorActor.getSpriteFrame().getTranslateY()*(1d/selectedSize)));
                    }
                }
                writer.newLine();
            }
            writer.write("initialMineralCount:"+Integer.toString(mineralCount));
            writer.close();  
        }
        else {
            System.out.println("Map must have home stations.");
        }
    }
    private boolean validateMap() {
        boolean validated = false;
        if(addedEditorActors.size() > 0) {
            for(EditorActor editorActor : addedEditorActors) {
                if(editorActor.getType() == UnitType.HOME_STATION) {
                    validated = true;
                }
            }
        }
        return validated;
    }
 
    // *********************************
    // *********** UTILITY *************
    // *********************************
    
    private void clearTopNode() {
        topBox.getChildren().clear();
    }
    private void clearBottomNode() {
        bottomBox.getChildren().clear();
    }
    private void clearRightNode() {
        rightBox.getChildren().clear();
    }
    private void clearLeftNode() {
        leftBox.getChildren().clear();
    }
    private void clearGameScreen() {
        if(gamePlayLoop != null) {
            gamePlayLoop.stop();
        }
        gameScreen.getChildren().clear();
    }
    private void createMainMenuButton() {
        mainMenuButton = new Button("Main Menu");
        mainMenuButton.setFocusTraversable(false);
        mainMenuButton.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                mainMenu();
                
            } catch (IOException ex) {
                Logger.getLogger(SpaceAI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
}