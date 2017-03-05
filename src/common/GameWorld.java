/*
 * Copyright (C) 2017 dr4ur
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package common;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dylan Russell
 * 
 * This class makes certain calculations pertaining to the game world.
 */
public class GameWorld {
    
    static int gameRound;
    private int uniqueID;
    private final SpaceAI spaceAI;
    private final CastingDirector castDirector;
    private int teamAMineralCount, teamBMineralCount;
    private Location teamAHomeStation, teamBHomeStation;
    private int gameSpeed;
    private QuadTree quad;
    private List<Actor> allActors;
    private GhostCircle ghostCircle;
    private GameWinner gameWinner;

/**
 * Instantiates the GameWorld class and sets the starting value of class variables.
 * @param spaceAI        an instance of the SpaceAI class that facilitates game animation.
 * @param castDirector   an instance of the CastingDirector class that communicates current unit count and status  
 */
    public GameWorld(SpaceAI spaceAI, CastingDirector castDirector) {
        this.spaceAI = spaceAI;
        this.castDirector = castDirector;
        this.gameSpeed = GameConstants.FRAMES_PER_ROUND_5;
        this.quad = new QuadTree(0,GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT, GameConstants.TOP_LEFT_X_PIXEL, GameConstants.TOP_LEFT_Y_PIXEL);
        this.allActors = new ArrayList<>();
        this.ghostCircle = null;
        this.gameWinner = new GameWinner();
    }

/**
 * Begins and updates game rounds and unit losses.    
 */
    public void update() {
        gameRound++;
        if(gameRound==1) {
            initializeStartingUnits();
            initializeStartingMineralCounts();
        }
        checkForGameWinner();
        if(gameWinner.getWinner() == null) {
            System.out.println("Game Round : " + gameRound);
            updateQuadTree();
            checkWeaponCollisions();
            clearDepletedActors();
        }
    }

 /**
 * Initializes values for units during the first game round.
 */
    private void initializeStartingUnits() {
        this.teamAHomeStation = GameConstants.TEAM_A_HOME_STATION;
        this.teamBHomeStation = GameConstants.TEAM_B_HOME_STATION;
        addUnit(UnitType.HOME_STATION, teamAHomeStation, Team.A);
        addUnit(UnitType.HOME_STATION, teamBHomeStation, Team.B);
    }
 /**
 * Initializes mineral count values for units during the first game round.
 */
    private void initializeStartingMineralCounts() {
        this.teamAMineralCount = this.teamBMineralCount = GameConstants.STARTING_MINERAL_COUNT;
    }
 /**
 * Provides unique identification number.
 * @return   increments uniqueID 
 */
    private synchronized int getUniqueID() {
        return uniqueID++;
    }
<<<<<<< HEAD
 /**
 * Updates game world with current unit information from the Casting Director class..
 */
=======
    private void checkForGameWinner() {
        Boolean homeStationA = false;
        Boolean homeStationB = false;
        int highestID = 0;
        int valueA = 0;
        int valueB = 0;
        Team highestIDTeam = Team.NEUTRAL;
        Team highestMineralTeam = Team.NEUTRAL;
        
        for(Unit unit : castDirector.getCurrentUnits()) {
            if(unit.getType() == UnitType.HOME_STATION) {
                switch(unit.getTeam()) {
                    case A : homeStationA = true; break;
                    case B : homeStationB = true; break;
                }
                if(homeStationA && homeStationB && gameRound < GameConstants.GAME_LIMIT) {
                    break;
                }
            }
            else {
                if(unit.getID() > highestID) {
                    highestIDTeam = unit.getTeam();
                }
                switch(unit.getTeam()) {
                    case A : valueA += unit.getType().getMineralCost();
                    case B : valueB += unit.getType().getMineralCost();
                }
                if(teamAMineralCount > teamBMineralCount) {
                    highestMineralTeam = Team.A;
                }
                else if(teamBMineralCount > teamAMineralCount) {
                    highestMineralTeam = Team.B;
                }
                else {
                    highestMineralTeam = Team.NEUTRAL;
                }
            }
        }
        if((homeStationA && !homeStationB) || (!homeStationA && homeStationB)) { // Check for Domination win
            if(homeStationA) {
                gameWinner.setWinner(Team.A);
                gameWinner.setDominationFactor(VictoryCondition.DOMINATION_WIN);
                return;
            }
            else if(homeStationB) {
                gameWinner.setWinner(Team.B);
                gameWinner.setDominationFactor(VictoryCondition.DOMINATION_WIN);
                return;
            }
        }
        if(gameRound >= GameConstants.GAME_LIMIT) {
            if(valueA > valueB) {
                gameWinner.setWinner(Team.A);
                gameWinner.setDominationFactor(VictoryCondition.VALUE_WIN);
            }
            else if(valueB > valueA) {
                gameWinner.setWinner(Team.B);
                gameWinner.setDominationFactor(VictoryCondition.VALUE_WIN);
            }
            else if(highestMineralTeam != Team.NEUTRAL) {
                gameWinner.setWinner(highestMineralTeam);
                gameWinner.setDominationFactor(VictoryCondition.MINERAL_WIN);
            }
            else {
                gameWinner.setWinner(highestIDTeam);
                gameWinner.setDominationFactor(VictoryCondition.ULT_DEFAULT_WIN);
            }
        }
    }
>>>>>>> 51d479186e74756258290265a3717a17dc41e453
    private void updateQuadTree() {
        quad.clear();
        allActors.clear();
        allActors.addAll(castDirector.getCurrentUnits());
        allActors.addAll(castDirector.getCurrentEnvironment());
        allActors.addAll(castDirector.getCurrentWeapons());
        for (int i = 0; i < allActors.size(); i++) {
            quad.insert(allActors.get(i));
        }      

    }
 /**
 * This method sorts current actor lists in order to determine actors that are weapons. Once 
 * sorted, method checks for weapons that have collided, been spent, or exploded and prints
 * current status of weapon. 
 */
    private void checkWeaponCollisions() {
        List<Actor> returnActors = new ArrayList();
        for (int i = 0; i < allActors.size(); i++) {
            if(allActors.get(i).isWeapon()) {
                Weapon weapon = (Weapon) allActors.get(i);
                if(!weapon.isSpent()){
                    returnActors.clear();
                    returnActors = quad.retrieve(returnActors, allActors.get(i));
                    System.out.println("\nWeapon " + allActors.get(i).getID() + " may collide with : ");
                    for (int x = 0; x < returnActors.size(); x++) {
                        if(!(returnActors.get(x).isWeapon())) {
                            System.out.print(returnActors.get(x).getID() + ", ");
                            if(allActors.get(i).collide(returnActors.get(x))) {
                                System.out.println("\nWeapon " + weapon.getID() + " spent status : " + weapon.isSpent());
                                System.out.println("\nWeapon " + weapon.getID() + " exploded status : " + weapon.isExploded());
                                System.out.println("Impact!");
                                weapon.damageApplication(returnActors.get(x));
                                System.out.println("\nWeapon " + weapon.getID() + " collied with unit : " + returnActors.get(x).getID());
                                System.out.println("\nWeapon " + weapon.getID() + " spent status : " + weapon.isSpent());
                                System.out.println("\nWeapon " + weapon.getID() + " exploded status : " + weapon.isExploded());
                            }
                        }
                    }
                }
            }  
        }
    }
/**
 * Clears actors, units and weapons, from the CastingDirector castDirector class if unit is dead 
 * or weapon is spent, exploded, or countdown is cleared.
 */
    private void clearDepletedActors() {
       List<Unit> checkUnits = castDirector.getCurrentUnits();
       checkUnits.stream().filter((unit) -> (unit.isDead())).forEachOrdered((unit) -> {
           removeActor(unit);
        });
       List<Weapon> checkWeapons = castDirector.getCurrentWeapons();
       checkWeapons.stream().filter((weapon) -> (weapon.isSpent() && weapon.isExploded() && weapon.getClearCountdown() >= GameConstants.WEAPON_CLEAR_COUNTDOWN)).forEachOrdered((weapon) -> {
           removeActor(weapon);
        });
    }
 /**
 * Adds unit to the CastingDirector castDirector class.
 * @param type         defines the type of unit to be added 
 * @param location     defines the location of unit to be added
 * @param team         defines the team of the unit to be added
 */
    public void addUnit(UnitType type, Location location, Team team){
        Unit unit = new Unit(spaceAI, type, getUniqueID(), location, team);
        castDirector.addToBeAdded(unit);
    }
 /**
 * Adds weapon to the CastingDirector castDirector class.
 * @param type         defines the type of weapon to be added 
 * @param location     defines the location of weapon to be added
 * @param team         defines the team of the weapon to be added
 * @param direction    defines the direction of the weapon to be added
 */
    public void addWeapon(WeaponType type, Location location, Team team, Direction direction){
        Weapon weapon = new Weapon(spaceAI, type, getUniqueID(), location, team, direction);
        castDirector.addToBeAdded(weapon);
    }
 /**
 * Adds environment to the CastingDirector castDirector class.
 * @param type         defines the type of environment to be added 
 * @param location     defines the location of environment to be added
 * @param team         defines the team of the environment to be added
 */
    public void addEnvironment(EnvironmentType type, Location location){
        Environment environment = new Environment(spaceAI, type, getUniqueID(), location);
        castDirector.addToBeAdded(environment);
    }
/**    
 * Removes actor from the CastingDirector castDirector class.
 * @param actor         defines the actor to be removed 
 */
    public void removeActor(Actor actor) {
        castDirector.addToRemovedActors(actor);
    }
<<<<<<< HEAD
/**
 * Gets Game Speed. 
 * @return gameSpeed
 */    
=======
>>>>>>> 51d479186e74756258290265a3717a17dc41e453
    public int getGameSpeed() {
        return this.gameSpeed;
    }
/**
 * Sets the Game Speed.
 * @param gameSpeed    defines the speed of the game by the frame per seconds in the Game Constants class.
 */
    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
/**
 * Gets the Game Round.
 * @return gameRound
 */
    public int getGameRound() {
        return gameRound;
    }
<<<<<<< HEAD
/**
 * Runs method updateQuadtree.
 * @return quad
 */
=======
    public GameWinner getGameWinner() {
        return gameWinner;
    }
    public Team getGameWinningTeam() {
        return gameWinner.getWinner();
    }
>>>>>>> 51d479186e74756258290265a3717a17dc41e453
    public QuadTree getUpdatedQuad() {
        updateQuadTree();
        return quad;
    }
/**
 * Gets mineral count for the requested Team.
 * @param team   defines the team
 */
    public int getMineralCount(Team team) {
        switch(team) {
            case A : {return teamAMineralCount;}
            case B : {return teamBMineralCount;}
            default : {System.out.println("ERROR IN GET MINERAL COUNT"); return 0;}
        }
    }
/**
 * Decreases mineral count.
 * @param i         the amount to be decreased
 * @param team      defines the team's mineral count to decrease
 */
    public void decreaseMineralCount(int i, Team team) {
        switch(team) {
            case A : {this.teamAMineralCount -= i; break;}
            case B : {this.teamBMineralCount -= i; break;}
            default : {System.out.println("ERROR IN DECREASE MINERAL COUNT");}
        }
   }
/**
 * Increases mineral count. 
 * @param i         the amount to be increased
 * @param team      defines the team's mineral count to increase
 */
    public void increaseMineralCount(int i, Team team) {
        switch(team) {
            case A : {this.teamAMineralCount += i; break;}
            case B : {this.teamBMineralCount += i; break;}
            default : {System.out.println("ERROR IN INCREASE MINERAL COUNT");}
        }
    }
/**
 * Obtains the initial home station location.
 * @param team    defines the team's home location
 * @return 
 */
    public Location getInitialHomeStationLocation(Team team) {
        switch(team) {
            case A : {return teamAHomeStation;}
            case B : {return teamBHomeStation;}
            default : {System.out.println("ERROR IN INITIAL HOME STATION LOCATION"); return new Location(0,0); }
        }
    }
/**
 * Passes the location variable into the GhostCircle class into the QuadTree class to obtain Actors radius and 
 * check if the location variable is occupied by an actor.
 * @param location      the desired location the player would like to check is or is not occupied
 * @return false
 */
    public boolean checkIfLocationIsEmpty(Location location) {
        ghostCircle = new GhostCircle(0,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
                if(ghostCircle.collide(returnActors.get(x))) {
                    if(returnActors.get(x).getID() != ghostCircle.getID() && !returnActors.get(x).isWeapon()) {
                        System.out.println("This location is occupied by unit " + returnActors.get(x).getID());
                        updateQuadTree();
                        return false;
                    }
                }
            }
        updateQuadTree();
        return true;
    }
/**
 * Passes the location variable into the GhostCircle class into the QuadTree class to obtain Actors radius and 
 * check if the location variable is occupied by an actor.
 * @param location      the desired location the player would like to check is or is not occupied
 * @param radius        the desired radius the player would like to check is or is not occupied
 * @return 
 */
    public boolean checkIfLocationIsEmpty(Location location, int radius) {
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
                if(ghostCircle.collide(returnActors.get(x))) {
                    if(returnActors.get(x).getID() != ghostCircle.getID() && !returnActors.get(x).isWeapon()) {
                        System.out.println("This location is occupied by unit " + returnActors.get(x).getID());
                        updateQuadTree();
                        return false;
                    }
                }
            }
        updateQuadTree();
        return true;
    }
/**
 * Passes the location variable into the GhostCircle class into the QuadTree class to obtain Actors radius and 
 * check if the location variable is occupied by an actor.
 * @param location      the desired location the player would like to check is or is not occupied
 * @param radius        the desired radius the player would like to check is or is not occupied
 * @param ID            the desired ID the player would like to check is or is not occupying the desired location     
 * @return 
 */
    public boolean checkIfLocationIsEmpty(Location location, int radius, int ID) { // Checks if location is empty except for the unit represented by "ID"
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
                if(ghostCircle.collide(returnActors.get(x))) {
                    if(returnActors.get(x).getID() != ghostCircle.getID() && returnActors.get(x).getID() != ID && !returnActors.get(x).isWeapon()) {
                        System.out.println("This location is occupied by unit " + returnActors.get(x).getID());
                        updateQuadTree();
                        return false;
                    }
                }
            }
        updateQuadTree();
        return true;
    }

    public List returnActorsInCircle(Location location, int radius) { // Returns all actors in circle.
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID()) {
                    System.out.println("Unit " + returnActors.get(x).getID() + " is in this circle.");
                    returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnActorsInCircle(Location location, int radius, int ID) { // Returns all actors in circle. Returns actors in circle except for the unit represented by "ID"
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID()
                    && returnActors.get(x).getID() != ID) {
                        System.out.println("Actor " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnUnitsInCircle(Location location, int radius) { // Returns all units in circle.
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && returnActors.get(x).isCommandable()) {
                        System.out.println("Unit " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnUnitsInCircle(Location location, int radius, int ID) { // Returns all units in circle except the unit indicated by "ID"
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && returnActors.get(x).isCommandable()
                    && returnActors.get(x).getID() != ID) {
                        System.out.println("Unit " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }    
    public List returnNonWeaponsInCircle(Location location, int radius) { // Does not return weapons.
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && !returnActors.get(x).isWeapon()) {
                        System.out.println("Actor " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnNonWeaponsInCircle(Location location, int radius, int ID) { // Does not return weapons. Returns actors in circle except for the unit represented by "ID"
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && returnActors.get(x).getID() != ID 
                    && !returnActors.get(x).isWeapon()) {
                        System.out.println("Actor " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnWeaponsInCircle(Location location, int radius) { // Returns all weapons in circle.
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && returnActors.get(x).isWeapon()) {
                        System.out.println("Weapon " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public List returnEnvironmentInCircle(Location location, int radius) { // Returns all environment in circle.
        ghostCircle = new GhostCircle(radius,location);
        quad.insert(ghostCircle);
        List<Actor> returnActors = new ArrayList();
        List<Actor> returnCollisions = new ArrayList();
        returnActors.clear();
        returnActors = quad.retrieve(returnActors, ghostCircle);
        for (int x = 0; x < returnActors.size(); x++) {
            if(ghostCircle.collide(returnActors.get(x))) {
                if(returnActors.get(x).getID() != ghostCircle.getID() 
                    && returnActors.get(x).isEnvironment()) {
                        System.out.println("Environment " + returnActors.get(x).getID() + " is in this circle.");
                        returnCollisions.add(returnActors.get(x));
                }
            }
        }
        updateQuadTree();
        return returnCollisions;
    }
    public int getUnitCount(Team team) {
        int count = 0;
        count = castDirector.getCurrentUnits().stream().filter((unit) -> (unit.getTeam() == team)).map((_item) -> 1).reduce(count, Integer::sum);
        return count;
    }
    public int getUnitCount(Team team, UnitType type) {
        int count = 0;
        count = castDirector.getCurrentUnits().stream().filter((unit) -> (unit.getTeam() == team && unit.getType() == type)).map((_item) -> 1).reduce(count, Integer::sum);
        return count;
    } 
}
