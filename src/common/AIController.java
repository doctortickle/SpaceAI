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

import java.util.Arrays;

//test

/**
 *
 * @author Dylan Russell This class is used to control the units the player will
 * command.
 */
public strictfp class AIController {

    private Unit unit;
    private GameWorld gameWorld;
    private final double leftBoundary;
    private final double rightBoundary;
    private final double bottomBoundary;
    private final double topBoundary;

    public AIController(Unit unit, GameWorld gameWorld) {
        this.unit = unit;
        this.gameWorld = gameWorld;
        this.leftBoundary = GameConstants.MIN_X_COORDINATE;
        this.rightBoundary = GameConstants.MAX_X_COORDINATE;
        this.bottomBoundary = GameConstants.MIN_Y_COORDINATE;
        this.topBoundary = GameConstants.MAX_Y_COORDINATE;
    }

    // *********************************
    // ******** INTERNAL METHODS *******
    // *********************************
    private boolean assertOnScreen(Location location) {
        if(location.getY() >= topBoundary - unit.getRadius()) { return false; }
        if(location.getY() <= bottomBoundary + unit.getRadius()) { return false; }
        if(location.getX() >= rightBoundary - unit.getRadius()) { return false; }
        if(location.getX() <= leftBoundary + unit.getRadius()) { return false; }
        return true;
    }
    private void updateSpriteAndLocation(Location location) {
        unit.updateLocation(location.getX(), location.getY());
        unit.getSpriteFrame().setTranslateX(location.getPixelX());
        unit.getSpriteFrame().setTranslateY(location.getPixelY());
        System.out.println(unit.getLocation().getX() + ", " + unit.getLocation().getY());
    }
    private boolean checkForCollision(Location location) {
        return !gameWorld.checkIfLocationIsEmpty(location, unit.getRadius(), unit.getID());
    }
    private boolean checkBoundaries(Location location) {  
        if(location.getY() >= topBoundary - unit.getRadius()) { return false; }
        if(location.getY() <= bottomBoundary + unit.getRadius()) { return false; }
        if(location.getX() >= rightBoundary - unit.getRadius()) { return false; }
        if(location.getX() <= leftBoundary + unit.getRadius()) { return false; }
        return true;
    }
    private boolean assertCanSenseLocation(Location location) {
        return canSenseLocation(location);
    }
    private boolean assertCanSensePartOfCircle(Location center, int radius) {
        return canSensePartOfCircle(center, radius);
    }
    private boolean assertCanSenseAllOfCircle(Location center, int radius) {
        return canSenseAllOfCircle(center, radius);
    }
    // *********************************
    // **** GAMEWORLD QUERY METHODS ****
    // *********************************
    public final int getGameRound() {
        return gameWorld.getGameRound();
    }

    public final int getMineralCount() {
        return gameWorld.getMineralCount(getTeam());
    }

    public final int getUnitCount() {
        return 0; // TODO
    }

    public final int getUnitCount(UnitType type) {
        return 0; // TODO
    }

    public final Location getInitialHomeStationLocation(Team team) {
        return gameWorld.getInitialHomeStationLocation(team);
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
        return unit.getLocation();
    }

    public final int getHealth() {
        return unit.getHealth();
    }

    public final int getFuel() {
        return unit.getFuel();
    }
    
    public final int getBuildCooldown() {
        return unit.getBuildCooldown();
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
    public boolean onTheMap(Location location) {
        return assertOnScreen(location);
    }
    public boolean onTheMap(Location location, int radius) {
        Location check1 = location.add(radius,Direction.NORTH);
        Location check2 = location.add(radius,Direction.EAST);
        Location check3 = location.add(radius,Direction.SOUTH);
        Location check4 = location.add(radius,Direction.WEST);
        return assertOnScreen(check1) && assertOnScreen(check2) && assertOnScreen(check3) && assertOnScreen(check4);
    }
    public boolean canSenseLocation(Location location) {
        return getCurrentLocation().distanceTo(location) <= getSensorRadius();
    }
    public boolean canSenseIncomingLocation(Location location) {
        return getCurrentLocation().distanceTo(location) <= getIncomingDetectionRadius();
    }
    public boolean canSensePartOfCircle(Location center, int radius) {
        return getCurrentLocation().distanceTo(center) - radius <= getSensorRadius();
    }
    public boolean canSenseAllOfCircle(Location center, int radius) {
        return getCurrentLocation().distanceTo(center) + radius <= getSensorRadius();
    }
    public boolean isLocationOccupied(Location location) {
        return !gameWorld.checkIfLocationIsEmpty(location);
    }
    public boolean isCircleOccupied(Location center, int radius) {
        return !gameWorld.checkIfLocationIsEmpty(center, radius);
    }
    
    // ***********************************
    // ****** READINESS METHODS **********
    // ***********************************
    
    public boolean isReadyToBuild() {
        return unit.getBuildCooldown() == 0;
    }

    public final void move(Location location) {
        if (getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius() 
            && checkBoundaries(location)
            && !checkForCollision(location)) {
                updateSpriteAndLocation(location);
        } else {
            Direction moveDirection = getCurrentLocation().directionTo(location);
            move(moveDirection);
        }
    }
    public final void move(Direction direction) {
        Location movePoint = getCurrentLocation().add(unit.getType().getFlightRadius(), direction);
        if(checkBoundaries(movePoint) && !checkForCollision(movePoint)) {
            move(movePoint);   
        }
    }
    public final void build(UnitType type, Direction direction) {
        Location location = getCurrentLocation().add(getType().getBodyRadius() + type.getBodyRadius(), direction);
        if( isReadyToBuild() 
            && Arrays.asList(unit.getType().getSpawnUnits()).contains(type)
            && getMineralCount()-type.getMineralCost() >= 0
            && gameWorld.checkIfLocationIsEmpty(location, type.getBodyRadius())
            && onTheMap(location, type.getBodyRadius()) ) {
                gameWorld.addUnit(type, location, getTeam());
                unit.setBuildCooldown(type.getSpawnCooldown());
                gameWorld.decreaseMineralCount(type.getMineralCost(),getTeam());
        }
        else {
            System.out.println("Can not build.");
        }
    }
}
