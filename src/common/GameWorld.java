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

    public GameWorld(SpaceAI spaceAI, CastingDirector castDirector) {
        this.spaceAI = spaceAI;
        this.castDirector = castDirector;
        this.gameSpeed = GameConstants.FRAMES_PER_ROUND_5;
        this.quad = new QuadTree(0,GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT, GameConstants.TOP_LEFT_X_PIXEL, GameConstants.TOP_LEFT_Y_PIXEL);
        this.allActors = new ArrayList<>();
        this.ghostCircle = null;
    }

    public void update() {
        gameRound++;
        if(gameRound==1) {
            initializeStartingUnits();
            initializeStartingMineralCounts();
        }
        System.out.println("Game Round : " + gameRound);
        updateQuadTree();
        checkWeaponCollisions();
        clearDepletedActors();
    }

    private void initializeStartingUnits() {
        this.teamAHomeStation = GameConstants.TEAM_A_HOME_STATION;
        this.teamBHomeStation = GameConstants.TEAM_B_HOME_STATION;
        addUnit(UnitType.HOME_STATION, teamAHomeStation, Team.A);
        addUnit(UnitType.HOME_STATION, teamBHomeStation, Team.B);
    }
    private void initializeStartingMineralCounts() {
        this.teamAMineralCount = this.teamBMineralCount = GameConstants.STARTING_MINERAL_COUNT;
    }
    private synchronized int getUniqueID() {
        return uniqueID++;
    }
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
    public void addUnit(UnitType type, Location location, Team team){
        Unit unit = new Unit(spaceAI, type, getUniqueID(), location, team);
        castDirector.addToBeAdded(unit);
    }
    public void addWeapon(WeaponType type, Location location, Team team, Direction direction){
        Weapon weapon = new Weapon(spaceAI, type, getUniqueID(), location, team, direction);
        castDirector.addToBeAdded(weapon);
    }
    public void addEnvironment(EnvironmentType type, Location location){
        Environment environment = new Environment(spaceAI, type, getUniqueID(), location);
        castDirector.addToBeAdded(environment);
    }
    public void removeActor(Actor actor) {
        castDirector.addToRemovedActors(actor);
    }
    
    public int getGameSpeed() {
        return this.gameSpeed;
    }
    public void setGameSpeed(int gameSpeed) {
        this.gameSpeed = gameSpeed;
    }
    public int getGameRound() {
        return gameRound;
    }
    public QuadTree getUpdatedQuad() {
        updateQuadTree();
        return quad;
    }
    public int getMineralCount(Team team) {
        switch(team) {
            case A : {return teamAMineralCount;}
            case B : {return teamBMineralCount;}
            default : {System.out.println("ERROR IN GET MINERAL COUNT"); return 0;}
        }
    }
    public void decreaseMineralCount(int i, Team team) {
        switch(team) {
            case A : {this.teamAMineralCount -= i; break;}
            case B : {this.teamBMineralCount -= i; break;}
            default : {System.out.println("ERROR IN DECREASE MINERAL COUNT");}
        }
    }
    public void increaseMineralCount(int i, Team team) {
        switch(team) {
            case A : {this.teamAMineralCount += i; break;}
            case B : {this.teamBMineralCount += i; break;}
            default : {System.out.println("ERROR IN INCREASE MINERAL COUNT");}
        }
    }
    public Location getInitialHomeStationLocation(Team team) {
        switch(team) {
            case A : {return teamAHomeStation;}
            case B : {return teamBHomeStation;}
            default : {System.out.println("ERROR IN INITIAL HOME STATION LOCATION"); return new Location(0,0); }
        }
    }
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
}
