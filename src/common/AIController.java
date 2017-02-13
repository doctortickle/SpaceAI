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

import world.GameWorld;

/**
 *
 * @author Dylan Russell
 * This class is used to control the units the player will command.
 */
public strictfp class AIController {
    
    Unit unit;
    GameWorld gameWorld;
    
    public AIController(Unit unit, GameWorld gameWorld) {
        this.unit = unit;
        this.gameWorld = gameWorld;
    }
    
    // *********************************
    // ******** INTERNAL METHODS *******
    // *********************************
    
    private final void assertOnScreen() {
        // TODO
    }
    
    // *********************************
    // ****** GLOBAL QUERY METHODS *****
    // *********************************
    
    public final int getGameRound() {
        return gameWorld.getGameRound();
    }
    public final int getMineralCount() {
        return 0; // TODO
    }
    public final int getUnitCount() {
        return 0; // TODO
    }
    public final int getUnitCount(UnitType type) {
        return 0; // TODO
    }
    public final Location getInitialHomeStationLocation(Team t) {
        return new Location(0,0); // TODO
    }
    
    // *********************************
    // ****** UNIT QUERY METHODS *******
    // *********************************
    
    public final int getID() {
        return unit.getID();
    }
    public final Team getTeam() {
        return unit.getTeam();
    }
    public final UnitType getType() {
        return unit.getType();
    }
    public final int getBodyRadius() {
        return unit.getRadius();
    }
    public final Location getCurrentLocation() {
        double x = unit.getX();
        double y = unit.getY();
        return new Location(x,y);
    }
    public final int getHealth() {
        return unit.getHealth();
    }
    public final int getFuel() {
        return unit.getFuel();
    }
    public final WeaponType[] getArsenal() {
        return unit.getType().getArsenal();
    }
    public final UnitType[] getSpawnSources() {
        return unit.getType().getSpawnSources();
    }
    public final UnitType[] getSpawnUnits() {
        return unit.getType().getSpawnUnits();
    }
    public int getSensorRadius() {
        return unit.getType().getSensorRadius();
    }
    public int getIncomingDetectionRadius() {
        return unit.getType().getIncomingDetectionRadius();
    }
    public double getFlightRadius() {
        return unit.getType().getFlightRadius();
    }
    public int getFuelBurnRate() {
        return unit.getType().getFuelBurnRate();
    }
    public int getRefuelRadius() {
        return unit.getType().getRefuelRadius();
    }
    public int getRefuelRate() {
        return unit.getType().getRefuelRate();
    }
    public int getMiningRate() {
        return unit.getType().getMiningRate();
    }
    
    // *************************************
    // ****** GENERAL SENSOR METHODS *******
    // *************************************
    
    private void assertCanSenseLocation(Location location) throws Exception {
        // TODO
    }
    private void assertCanSensePartOfCircle(Location center, int radius) {
        // TODO
    }
    private void assertCanSenseAllOfCircle(Location center, int radius) {
        // TODO
    }
    public boolean onTheMap(Location location) {
        return true;
        // TODO
    }
    public boolean onTheMap(Location location, int radius) {
        return true;
        // TODO
    }
    public boolean canSenseLocation(Location location) {
        return getCurrentLocation().distanceTo(location) <= getSensorRadius();
    }
    public boolean canSenseIncomingLocation(Location location) {
        return getCurrentLocation().distanceTo(location) <= getIncomingDetectionRadius();
    }
    public boolean canSensePartOfCircle(Location center, double radius) {
        return getCurrentLocation().distanceTo(center) - radius <= getSensorRadius();
    }
    public boolean canSenseAllOfCirlce(Location center, double radius) {
        return getCurrentLocation().distanceTo(center) + radius <= getSensorRadius();
    }
    public boolean isLocationOccupied(Location location) {
        return true;
        // TODO
    }
    public final void move(Location location) {
       System.out.println("\nMoving to " + location.getX() +", "+ location.getY());
       System.out.println("distance to point - " + getCurrentLocation().distanceTo(location));
       if(getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius()) {
            System.out.println("In A");
            unit.getSpriteFrame().setTranslateX(location.getPixelX());
            unit.getSpriteFrame().setTranslateY(location.getPixelY());
            unit.setX(location.getX());
            unit.setY(location.getY());
       }
       else {
           System.out.println("In B");
            Direction moveDirection = getCurrentLocation().directionTo(location);
            move(moveDirection);
       }
    }
    public final void move(Direction direction) {
        Location movePoint = getCurrentLocation().add(unit.getType().getFlightRadius(), direction);
        move(movePoint);   
    }


}
