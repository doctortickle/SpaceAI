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

    public GameWorld(SpaceAI spaceAI, CastingDirector castDirector) {
        this.spaceAI = spaceAI;
        this.castDirector = castDirector;
        this.gameSpeed = GameConstants.FRAMES_PER_ROUND_5;
        this.quad = new QuadTree(0,GameConstants.CENTER_WIDTH, GameConstants.CENTER_HEIGHT, GameConstants.TOP_LEFT_X_PIXEL, GameConstants.TOP_LEFT_Y_PIXEL);
        this.allActors = new ArrayList<Actor>();
    }

    public void update() {
        gameRound++;
        if(gameRound==1) {
            initializeStartingUnits();
            initializeStartingMineralCounts();
        }
        System.out.println("Game Round : " + gameRound);
        updateQuadTree();
        checkQuadTree();
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
    private void checkQuadTree() {
        List<Actor> returnActors = new ArrayList();
        for (int i = 0; i < allActors.size(); i++) {
            returnActors.clear();
            returnActors = quad.retrieve(returnActors, allActors.get(i));
            System.out.println("\nUnit " + allActors.get(i).getID() + " can collide with : ");
            for (int x = 0; x < returnActors.size(); x++) {
                System.out.print(returnActors.get(x).getID() + ", ");
                //Collision algorithm here.
            }
        }
    }
    public void addUnit(UnitType type, Location location, Team team){
        Unit unit = new Unit(spaceAI, type, getUniqueID(), location, team);
        castDirector.addToBeAdded(unit);
    }
    public void addWeapon(WeaponType type, Location location){
        Weapon weapon = new Weapon(type, getUniqueID(), location);
        castDirector.addToBeAdded(weapon);
    }
    public void addEnvironment(EnvironmentType type, Location location){
        Environment environment = new Environment(type, getUniqueID(), location);
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
            case B : {return teamAHomeStation;}
            default : {System.out.println("ERROR IN INITIAL HOME STATION LOCATION"); return new Location(0,0); }
        }
    }
    public boolean checkIfLocationIsEmpty(Location location) {
        return true;
    }
}
