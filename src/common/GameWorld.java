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

import common.*;
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

    public GameWorld(SpaceAI spaceAI, CastingDirector castDirector) {
        this.spaceAI = spaceAI;
        this.castDirector = castDirector;
    }

    public void update() {
        gameRound++;
        if(gameRound==1) {
            initializeStartingUnits();
        }
        System.out.print("Game Round : " + gameRound);
    }
    public int getGameRound() {
        return gameRound;
    }
    public void initializeStartingUnits() {
        addUnit(UnitType.HOME_STATION, new Location(0,30), Team.A);
        addUnit(UnitType.HOME_STATION, new Location(0,-30), Team.B);
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
    
    private synchronized int getUniqueID() {
        return uniqueID++;
    }
}
