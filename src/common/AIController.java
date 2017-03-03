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
import java.util.List;

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
    }
    private void updateUnitMovementInfo() {
        unit.setHasMoved(true);
        unit.decreaseFuel(unit.getType().getFuelBurnRate());
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
    private boolean assertCanMove() {
        return isReadyToMove();
    }
    private boolean assertCanBuild(UnitType type) {
        return Arrays.asList(unit.getType().getSpawnUnits()).contains(type)
            && getMineralCount()-type.getMineralCost() >= 0;
    }
    private boolean assertCanFire(WeaponType type) {
        return unit.getType().canAttack() && Arrays.asList(unit.getType().getArsenal()).contains(type);
    }
    private boolean assertCanSenseLocation(Location location) {
        return canSenseLocation(location);
    }
    private boolean assertLocationIsEmpty(Location location, int radius) {
        return gameWorld.checkIfLocationIsEmpty(location, radius);
    }
    private boolean assertCanSensePartOfCircle(Location center, int radius) {
        return canSensePartOfCircle(center, radius);
    }
    private boolean assertCanSenseAllOfCircle(Location center, int radius) {
        return canSenseAllOfCircle(center, radius);
    }
    private boolean assertCanRefuel(Unit target) {
        return unit.getLocation().distanceTo(target.getLocation()) < unit.getType().getRefuelRadius()
                && unit.getType().canRefuel();
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
        return gameWorld.getUnitCount(unit.getTeam());
    }
    public final int getUnitCount(UnitType type) {
        return gameWorld.getUnitCount(unit.getTeam(), type);
    }
    public final Location getInitialHomeStationLocation(Team team) {
        return gameWorld.getInitialHomeStationLocation(team);
    }
    public final boolean canBuild(UnitType type, Direction direction) {
        Location location = getCurrentLocation().add(getType().getBodyRadius() + type.getBodyRadius(), direction);
        return isReadyToBuild() 
                && Arrays.asList(unit.getType().getSpawnUnits()).contains(type)
                && getMineralCount()-type.getMineralCost() >= 0
                && gameWorld.checkIfLocationIsEmpty(location, type.getBodyRadius())
                && onTheMap(location, type.getBodyRadius());
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
    public final int getFuelMax() {
        return unit.getType().getFuelMax();
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
    public boolean canAttack() {
        return unit.getType().canAttack();
    }
    public boolean canBomb() {
        return unit.getType().canBomb();
    }
    public boolean canDeployMine() {
        return unit.getType().canDeployMine();
    }
    public boolean canPlanetBombardment() {
        return unit.getType().canPlanetBombardment();
    }
    public boolean canBuildStructure() {
        return unit.getType().canBuildStructure();
    }
    public boolean canBuildShip() {
        return unit.getType().canBuildShip();
    }
    public boolean canHarvest() {
        return unit.getType().canHarvest();
    }
    public boolean canRefuel() {
        return unit.getType().canRefuel();
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
        return assertOnScreen(location) && getCurrentLocation().distanceTo(location) <= getSensorRadius();
    }
    public boolean canSenseIncomingLocation(Location location) {
        return assertOnScreen(location) && getCurrentLocation().distanceTo(location) <= getIncomingDetectionRadius();
    }
    public boolean canSensePartOfCircle(Location center, int radius) {
        return assertOnScreen(center) && getCurrentLocation().distanceTo(center) - radius <= getSensorRadius();
    }
    public boolean canSenseAllOfCircle(Location center, int radius) {
        return assertOnScreen(center) && getCurrentLocation().distanceTo(center) + radius <= getSensorRadius();
    }
    public boolean isLocationOccupied(Location location) {
        if(assertCanSenseLocation(location)) {
            return !gameWorld.checkIfLocationIsEmpty(location);
        }
        else {
            return false;
        }
    }
    public boolean isCircleOccupied(Location center, int radius) {
        if(assertCanSenseLocation(center)) {
            return !gameWorld.checkIfLocationIsEmpty(center, radius);
        }
        else {
            return false;
        }
    }
    public Actor senseActorAtLocation(Location location) {
        if(assertCanSenseLocation(location) && gameWorld.returnActorsInCircle(location, 0).size() > 0) {
            return (Actor) gameWorld.returnActorsInCircle(location, 0).get(0);
        }
        return null;
    }
    public Unit senseUnitAtLocation(Location location) {
        if(assertCanSenseLocation(location) && gameWorld.returnUnitsInCircle(location, 0).size() > 0) {
            return (Unit) gameWorld.returnUnitsInCircle(location, 0).get(0);
        }
        return null;
    }
    public Weapon senseWeaponAtLocation(Location location) {
        if(assertCanSenseLocation(location) && gameWorld.returnWeaponsInCircle(location, 0).size() > 0) {
            return (Weapon) gameWorld.returnWeaponsInCircle(location, 0).get(0);
        }
        return null;
    }
    public Environment senseEnvironmentAtLocation(Location location) {
        if(assertCanSenseLocation(location) && gameWorld.returnEnvironmentInCircle(location, 0).size() > 0) {
            return (Environment) gameWorld.returnEnvironmentInCircle(location, 0).get(0);
        }
        return null;       
    }
    public List<Actor> senseActors() {
        return gameWorld.returnActorsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
    }
    public List<Unit> senseActors(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        return gameWorld.returnActorsInCircle(unit.getLocation(), range);
    }
    public List<Unit> senseUnits() {
        return gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
    }
    public List<Unit> senseUnits(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        return gameWorld.returnUnitsInCircle(unit.getLocation(), range);
    }
    public List<Unit> senseUnits(Team team) {
        List<Unit> returnUnits = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
        returnUnits.stream().filter((sUnit) -> (sUnit.getTeam() == team.opponent())).forEachOrdered((sUnit) -> {
            returnUnits.remove(sUnit);
        });
        return returnUnits;
    }
    public List<Unit> senseUnits(int range, Team team) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> returnUnits = gameWorld.returnUnitsInCircle(unit.getLocation(), range);
        returnUnits.stream().filter((sUnit) -> (sUnit.getTeam() == team.opponent())).forEachOrdered((sUnit) -> {
            returnUnits.remove(sUnit);
        });
        return returnUnits;
    }
    public List<Unit> senseUnitsExceptThisUnit() {
        return gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius(), unit.getID());
    }
    public List<Unit> senseUnitsExceptThisUnit(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        return gameWorld.returnUnitsInCircle(unit.getLocation(), range, unit.getID());
    }
    public List<Unit> senseUnitsExceptThisUnit(Team team) {
        List<Unit> returnUnits = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius(), unit.getID());
        returnUnits.stream().filter((sUnit) -> (sUnit.getTeam() == team.opponent())).forEachOrdered((sUnit) -> {
            returnUnits.remove(sUnit);
        });
        return returnUnits;
    }
    public List<Unit> senseUnitsExceptThisUnit(int range, Team team) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> returnUnits = gameWorld.returnUnitsInCircle(unit.getLocation(), range, unit.getID());
        returnUnits.stream().filter((sUnit) -> (sUnit.getTeam() == team.opponent())).forEachOrdered((sUnit) -> {
            returnUnits.remove(sUnit);
        });
        return returnUnits;
    }
    public List<Weapon> senseWeapons() {
        return gameWorld.returnWeaponsInCircle(unit.getLocation(), unit.getType().getIncomingDetectionRadius());
    }
    public List<Weapon> senseWeapons(int range) {
        if(range > unit.getType().getIncomingDetectionRadius()){
            range = unit.getType().getIncomingDetectionRadius();
        }
        return gameWorld.returnWeaponsInCircle(unit.getLocation(), range);
    }
    public List<Environment> senseEnvironment() {
        return gameWorld.returnEnvironmentInCircle(unit.getLocation(), unit.getType().getSensorRadius());
    }
    public List<Environment> senseEnvironment(int range) {
        if(range > unit.getType().getSensorRadius()){
            range = unit.getType().getSensorRadius();
        }
        return gameWorld.returnEnvironmentInCircle(unit.getLocation(), range);
    }
    
    // ***********************************
    // ****** READINESS METHODS **********
    // ***********************************
    
    public boolean isReadyToBuild() {
        return unit.getBuildCooldown() == 0 && (unit.getType().canBuildShip() || unit.getType().canBuildStructure());
    }
    public boolean isReadyToMove() {
        return !unit.getHasMoved();
    }
    public boolean isReadyToFire() {
        return unit.getReloadCooldown() == 0 && unit.getType().canAttack();
    }
    public boolean isReadyToFuel() {
        return !unit.getHasRefueled() && unit.getType().canRefuel();
    }
    public boolean isReadyToHarvest() {
        return !unit.getHasHarvested() && unit.getType().canHarvest();
    }
   
    // ***********************************
    // ********* UNIT ACTIONS ************
    // ***********************************
    
    public final void move(Location location) {
        if (getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius() 
            && assertCanMove()
            && checkBoundaries(location)
            && !checkForCollision(location)) {
                updateSpriteAndLocation(location);
                updateUnitMovementInfo();
        } else {
            Direction moveDirection = getCurrentLocation().directionTo(location);
            move(moveDirection);
        }
    }
    public final void move(Direction direction) {
        Location movePoint = getCurrentLocation().add(unit.getType().getFlightRadius(), direction);
        if  (assertCanMove()
            && checkBoundaries(movePoint) 
            && !checkForCollision(movePoint)) {
            move(movePoint);   
        }
    }
    public final void build(UnitType type, Direction direction) {
        Location location = getCurrentLocation().add(getType().getBodyRadius() + type.getBodyRadius(), direction);
        if( isReadyToBuild() 
            && assertCanBuild(type)
            && assertLocationIsEmpty(location, type.getBodyRadius())
            && onTheMap(location, type.getBodyRadius()) ) {
                gameWorld.addUnit(type, location, getTeam());
                unit.setBuildCooldown(type.getSpawnCooldown());
                gameWorld.decreaseMineralCount(type.getMineralCost(),getTeam());
        }
        else {
            System.out.println("Can not build.");
        }
    }
    public final void fire(WeaponType type, Direction direction) {
        Location location = getCurrentLocation().add(getType().getBodyRadius() + type.getWeaponRadius(), direction);
        if( isReadyToFire() 
            && assertCanFire(type)
            && onTheMap(location, type.getWeaponRadius()) ) {
                Team team;
                switch(type) {
                    case MINE : team = unit.getTeam(); break;
                    default   : team = Team.NEUTRAL;
                }
                gameWorld.addWeapon(type, location, team, direction);
                unit.setReloadCooldown(type.getReloadTime());
        }
        else {
            System.out.println("Can not fire.");
        }
    }
    public final void harvest(Environment environment) {
        if(canHarvest() && environment.getMineralCount() > 0 ){
            if (unit.getType().getMiningRate() > environment.getMineralCount()) {
                int newMineralCount = environment.getMineralCount();
                environment.decreaseMineralCount(newMineralCount);
                gameWorld.increaseMineralCount(newMineralCount, unit.getTeam());
            }   
            else{
                environment.decreaseMineralCount(unit.getType().getMiningRate());
                gameWorld.increaseMineralCount(unit.getType().getMiningRate(), unit.getTeam());
            }
        }
    }
    public final void refuel(Unit target) {
        if(assertCanRefuel(target) && isReadyToFuel()) {
            target.increaseFuel(unit.getType().getRefuelRate());
            unit.setHasRefueled(true);
        }
    }
}
