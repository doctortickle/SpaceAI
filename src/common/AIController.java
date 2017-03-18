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
import java.util.Comparator;
import java.util.List;

//test

/**
 *
 * @author Dylan Russell This class is used to control the units the player will
 * command.
 */
public strictfp class AIController {

    private final Unit unit;
    private final GameWorld gameWorld;
    private final double leftBoundary;
    private final double rightBoundary;
    private final double bottomBoundary;
    private final double topBoundary;
    private final Direction defaultDirection;

    AIController(Unit unit, GameWorld gameWorld) {
        this.unit = unit;
        this.gameWorld = gameWorld;
        this.leftBoundary = gameWorld.getMinXCoordinate();
        this.rightBoundary = gameWorld.getMaxXCoordinate();
        this.bottomBoundary = gameWorld.getMinYCoordinate();
        this.topBoundary = gameWorld.getMaxYCoordinate();
        this.defaultDirection = new Direction(0);
    }

    // *********************************
    // ******** INTERNAL METHODS *******
    // *********************************

    private void updateSpriteAndLocation(Location location) {
        setSpriteFrameRotation(location);
        unit.updateLocation(location.getX(), location.getY());
        unit.getSpriteFrame().setTranslateX(location.getPixelX());
        unit.getSpriteFrame().setTranslateY(location.getPixelY());
    }
    private void setSpriteFrameRotation(Location location) {
        Direction direction = unit.getLocation().directionTo(location);
        if(unit.isShip()) {
            double rotate = direction.degreesBetween(defaultDirection);
            unit.getSpriteFrame().setRotate(rotate);
        }
    }
    private void updateUnitMovementInfo() {
        unit.setHasMoved(true);
        unit.decreaseFuel(unit.getType().getFuelBurnRate());
    }
    private void setDead() {
        unit.setDead(true);
        unit.setStalled(true);
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
    private Environment findNearestEnvironment() {
        List<Environment> environment = senseEnvironment();
        if(environment.size() > 0) {
            return environment.get(0);           
        }
        return null;
    }
    private Location courseCorrect(Direction direction, Environment environment) {
        double currentDistance = getLocation().distanceTo(environment.getLocation());
        Location movePoint = getLocation().add(getType().getFlightRadius(), direction);
        double projectedDistance = movePoint.distanceTo(environment.getLocation());
        double delta = projectedDistance - currentDistance;
        if(projectedDistance > 0) {
            Location correctedPoint = movePoint.add(delta, movePoint.directionTo(environment.getLocation()));
            return correctedPoint;
        }
        if(projectedDistance < 0) {
            Location correctedPoint = movePoint.subtract(delta, movePoint.directionTo(environment.getLocation()));
            return correctedPoint;
        }
        return movePoint;
    }
    private boolean assertOnScreen(Location location) {
        if(location.getY() >= topBoundary - unit.getRadius()) { return false; }
        if(location.getY() <= bottomBoundary + unit.getRadius()) { return false; }
        if(location.getX() >= rightBoundary - unit.getRadius()) { return false; }
        if(location.getX() <= leftBoundary + unit.getRadius()) { return false; }
        return true;
    }
    private boolean assertOnScreen(Location location, int radius) {
        Location check1 = location.add(radius,Direction.NORTH);
        Location check2 = location.add(radius,Direction.EAST);
        Location check3 = location.add(radius,Direction.SOUTH);
        Location check4 = location.add(radius,Direction.WEST);
        return assertOnScreen(check1) && assertOnScreen(check2) && assertOnScreen(check3) && assertOnScreen(check4);
    }
    private boolean assertCanSenseLocation(Location location) {
        return assertOnScreen(location) && getLocation().distanceTo(location) <= getSensorRadius();
    }
    private boolean assertCanSenseIncomingLocation(Location location) {
        return assertOnScreen(location) && getLocation().distanceTo(location) <= getIncomingDetectionRadius();
    }
    private boolean assertLocationIsEmpty(Location location) {
        return gameWorld.checkIfLocationIsEmpty(location);
    }
    private boolean assertLocationIsEmpty(Location location, int radius) {
        return gameWorld.checkIfLocationIsEmpty(location, radius);
    }
    private boolean assertCanSensePartOfCircle(Location center, int radius) {
        return assertOnScreen(center) && getLocation().distanceTo(center) - radius <= getSensorRadius();
    }
    private boolean assertCanSenseAllOfCircle(Location center, int radius) {
        return assertOnScreen(center) && getLocation().distanceTo(center) + radius <= getSensorRadius();
    }
    private boolean assertCanMove(Location location) {
        return getLocation().distanceTo(location) <= unit.getType().getFlightRadius()
               && !checkForCollision(location);
    }
    private boolean assertCanOrbit(Environment environment) {
        return unit.getLocation().distanceTo(environment.getLocation()) > unit.getRadius() + environment.getRadius() + GameConstants.MIN_ORBIT_DISTANCE;
    }
    private boolean assertValidMoveLocation(Location location) {
        return !unit.getLocation().equals(location) 
                && checkBoundaries(location);
    }
    private boolean assertNeedsEnvironment(UnitType type) {
        return type == UnitType.SMALL_DOCK || type == UnitType.LARGE_DOCK || type == UnitType.CAPITAL_DOCK || type == UnitType.HARVESTING_FACILITY;
    }
    private boolean assertCorrectEnvironment(UnitType type, Environment environment) {
        return Arrays.asList(type.getSpawnLocations()).contains(environment.getType());
    }
    private boolean assertCanConstruct(UnitType type, Location location, Environment environment) {
        return Arrays.asList(unit.getType().getSpawnUnits()).contains(type)
            && type.isStructure()
            && assertNeedsEnvironment(type)
            && assertCorrectEnvironment(type, environment)
            && getMineralCount()-type.getMineralCost() >= 0
            && location.distanceTo(environment.getLocation()) - (environment.getType().getBodyRadius() + type.getBodyRadius()) > GameConstants.MIN_ENV_BUILD_DISTANCE
            && location.distanceTo(environment.getLocation()) - (environment.getType().getBodyRadius() + type.getBodyRadius()) < GameConstants.MAX_ENV_BUILD_DISTANCE;
    }
    private boolean assertCanConstruct(UnitType type) {
        return Arrays.asList(unit.getType().getSpawnUnits()).contains(type)
            && !assertNeedsEnvironment(type)
            && getMineralCount()-type.getMineralCost() >= 0;
    }
    private boolean assertCanFire(WeaponType type) {
        return unit.getType().canAttack() && Arrays.asList(unit.getType().getArsenal()).contains(type);
    }
    private boolean assertCanRefuel(Unit target) {
        return unit.getLocation().distanceTo(target.getLocation()) < unit.getType().getRefuelRadius()
                && unit.getType().canRefuel()
                && target.getTeam() == unit.getTeam();
    }
    private boolean assertCanHarvest(Environment environment) {
        return environment.getLocation().distanceTo(unit.getLocation()) < (unit.getType().getHarvestingRadius() + environment.getRadius())
                 && environment.getMineralCount() > 0 
                 && unit.getType().canHarvest();
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
    public final Location getLocation() {
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
        return unit.getType().getHarvestingRate();
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
        return assertOnScreen(location, radius);
    }
    public boolean canSenseLocation(Location location) {
        return assertCanSenseLocation(location);
    }
    public boolean canSenseIncomingLocation(Location location) {
        return assertCanSenseIncomingLocation(location);
    }
    public boolean canSensePartOfCircle(Location center, int radius) {
        return assertCanSensePartOfCircle(center, radius);
    }
    public boolean canSenseAllOfCircle(Location center, int radius) {
        return assertCanSenseAllOfCircle(center, radius);
    }
    public boolean isLocationOccupied(Location location) {
        if(assertCanSenseLocation(location)) {
            return !assertLocationIsEmpty(location);
        }
        else {
            return false;
        }
    }
    public boolean isCircleOccupied(Location center, int radius) {
        if(assertCanSenseAllOfCircle(center, radius)) {
            return !assertLocationIsEmpty(center, radius);
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
        List<Actor> actorList = gameWorld.returnActorsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
        actorList.sort(
            Comparator.comparing(
                actor -> unit.getLocation().distanceTo(actor.getLocation())));
        return actorList;
    }
    public List<Actor> senseActors(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Actor> actorList = gameWorld.returnActorsInCircle(unit.getLocation(), range);
        actorList.sort(
            Comparator.comparing(
                actor -> unit.getLocation().distanceTo(actor.getLocation())));
        return actorList;
    }
    public List<Unit> senseUnits() {
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnits(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), range);
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnits(Team team) {
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius());
        unitList.stream()
                .filter((sUnit) -> (sUnit.getTeam() == team.opponent()))
                .forEachOrdered((sUnit) -> {unitList.remove(sUnit);});
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnits(int range, Team team) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), range);
        unitList.stream()
                .filter((sUnit) -> (sUnit.getTeam() == team.opponent()))
                .forEachOrdered((sUnit) -> {unitList.remove(sUnit);});
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnitsExceptThisUnit() {
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius(), unit.getID());
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnitsExceptThisUnit(int range) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), range, unit.getID());
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnitsExceptThisUnit(Team team) {
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), unit.getType().getSensorRadius(), unit.getID());
        unitList.stream()
                .filter((sUnit) -> (sUnit.getTeam() == team.opponent()))
                .forEachOrdered((sUnit) -> {unitList.remove(sUnit);});
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Unit> senseUnitsExceptThisUnit(int range, Team team) {
        if(range > unit.getType().getSensorRadius()) {
            range = unit.getType().getSensorRadius();
        }
        List<Unit> unitList = gameWorld.returnUnitsInCircle(unit.getLocation(), range, unit.getID());
        unitList.stream()
                .filter((sUnit) -> (sUnit.getTeam() == team.opponent()))
                .forEachOrdered((sUnit) -> {unitList.remove(sUnit);});
        unitList.sort(
            Comparator.comparing(
                unit2 -> unit.getLocation().distanceTo(unit2.getLocation())));
        return unitList;
    }
    public List<Weapon> senseWeapons() {
        List<Weapon> weaponList = gameWorld.returnWeaponsInCircle(unit.getLocation(), unit.getType().getIncomingDetectionRadius());
        weaponList.sort(
            Comparator.comparing(
                weapon -> unit.getLocation().distanceTo(weapon.getLocation())));
        return weaponList;
    }
    public List<Weapon> senseWeapons(int range) {
        if(range > unit.getType().getIncomingDetectionRadius()){
            range = unit.getType().getIncomingDetectionRadius();
        }
        List<Weapon> weaponList = gameWorld.returnWeaponsInCircle(unit.getLocation(), range);
        weaponList.sort(
            Comparator.comparing(
                weapon -> unit.getLocation().distanceTo(weapon.getLocation())));
        return weaponList;
    }
    public List<Environment> senseEnvironment() {
        List<Environment> environmentList = gameWorld.returnEnvironmentInCircle(unit.getLocation(), unit.getType().getSensorRadius());
        environmentList.sort(
            Comparator.comparing(
                environment -> unit.getLocation().distanceTo(environment.getLocation())));
        return environmentList;
    }
    public List<Environment> senseEnvironment(int range) {
        if(range > unit.getType().getSensorRadius()){
            range = unit.getType().getSensorRadius();
        }
        List<Environment> environmentList = gameWorld.returnEnvironmentInCircle(unit.getLocation(), range);
        environmentList.sort(
            Comparator.comparing(
                environment -> unit.getLocation().distanceTo(environment.getLocation())));
        return environmentList;
    }
    public Location senseOrbitDistance(Environment environment, double distance) {
        if(assertCanSensePartOfCircle(environment.getLocation(), environment.getType().getBodyRadius())) {
            return environment.getLocation().add(environment.getType().getBodyRadius() + distance, environment.getLocation().directionTo(unit.getLocation()));
        }
        return null;
    }
    public Location senseOrbitDistance(Environment environment, double distance, Direction direction) {
        if(assertCanSensePartOfCircle(environment.getLocation(), environment.getType().getBodyRadius())) {
            return environment.getLocation().add(environment.getType().getBodyRadius() + distance, direction);
        }
        return null;       
    }
    
    // ***********************************
    // ****** READINESS METHODS **********
    // ***********************************
    
    public boolean isReadyToMove() {
        return !unit.getHasMoved();
    }
    public boolean isReadyToBuild() {
        return unit.getBuildCooldown() == 0 && (unit.getType().canBuildShip() || unit.getType().canBuildStructure());
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
    public boolean canMove(Location location) {
        return  isReadyToMove()
                && assertCanMove(location)
                && assertValidMoveLocation(location);
                
    }
    public boolean canMove(Direction direction) {
        Location location = getLocation().add(unit.getType().getFlightRadius(), direction);
        return  isReadyToMove()
                && assertCanMove(location)
                && assertValidMoveLocation(location);
    }
    public boolean canConstruct(UnitType type, Direction direction) {
        Location location = getLocation().add(getType().getBodyRadius() + type.getBodyRadius() + GameConstants.BUILD_DISTANCE, direction);
        if(assertNeedsEnvironment(type)) {
            Environment environment = findNearestEnvironment();
            if(environment != null) {
                return assertCanConstruct(type, location, environment)
                    && assertLocationIsEmpty(location, type.getBodyRadius())
                    && assertOnScreen(location, type.getBodyRadius())
                    && isReadyToBuild();
            }
            return false;
        }
        return assertCanConstruct(type)
            && assertLocationIsEmpty(location, type.getBodyRadius())
            && assertOnScreen(location, type.getBodyRadius())
            && isReadyToBuild();
    }
    public boolean canOrbit(Environment environment) {
        return assertCanSensePartOfCircle(environment.getLocation(), environment.getRadius())
                && assertCanOrbit(environment);
    }
    public boolean canFire(WeaponType type, Direction direction) {
        Location location = getLocation().add(getType().getBodyRadius() + type.getWeaponRadius(), direction);
        return assertCanFire(type)
                && assertOnScreen(location, type.getWeaponRadius())
                && isReadyToFire();
    }
    public boolean canHarvest(Environment environment) {
        return assertCanHarvest(environment)
                && isReadyToHarvest();
    }
    public boolean canRefuel(Unit target) {
        return assertCanRefuel(target) 
               && isReadyToFuel();
    }
   
    // ***********************************
    // ********* UNIT ACTIONS ************
    // ***********************************
    
    public final void move(Location location) {
        if (canMove(location) ) {
                updateSpriteAndLocation(location);
                updateUnitMovementInfo();
                
        } else if(assertValidMoveLocation(location)){
                Direction moveDirection = getLocation().directionTo(location);
                move(moveDirection);
        }
    }
    public final void move(Direction direction) {
        Location movePoint = getLocation().add(unit.getType().getFlightRadius(), direction);
        if  (canMove(movePoint)) {
                move(movePoint);   
        }
    }
    public final void move(Direction direction, double distance) {
        Location movePoint = getLocation().add(distance, direction);
        if  (canMove(movePoint)) {
                move(movePoint);   
        }
    }
    public final void orbitClockwise(Environment environment) {
        if(canOrbit(environment)) {
            Direction direction = unit.getLocation().directionTo(environment.getLocation()).getOrthogonalLeft();
            Location movePoint = courseCorrect(direction, environment);
            move(movePoint);
        }
    }
    public final void orbitCounterClockwise(Environment environment) {
        if(canOrbit(environment)) {
            Direction direction = unit.getLocation().directionTo(environment.getLocation()).getOrthogonalRight();
            Location movePoint = courseCorrect(direction, environment);
            move(movePoint);           
        } 
    }
    public final void construct(UnitType type, Direction direction) {
        Location location = getLocation().add(getType().getBodyRadius() + type.getBodyRadius() + GameConstants.BUILD_DISTANCE, direction);
        if(canConstruct(type, direction)) {
            gameWorld.addUnit(type, location, getTeam());
            unit.setBuildCooldown(type.getSpawnCooldown());
            gameWorld.decreaseMineralCount(type.getMineralCost(),getTeam());
        }
        else {
            System.out.println("Can not construct.");
        }
    }
    public final void fire(WeaponType type, Direction direction) {
        Location location = getLocation().add(getType().getBodyRadius() + type.getWeaponRadius(), direction);
        if(canFire(type, direction)) {
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
            if(canHarvest(environment)){
                    if (unit.getType().getHarvestingRate() > environment.getMineralCount()) {
                        int newMineralCount = environment.getMineralCount();
                        environment.decreaseMineralCount(newMineralCount);
                        gameWorld.increaseMineralCount(newMineralCount, unit.getTeam());
                    }   
                    else{
                        environment.decreaseMineralCount(unit.getType().getHarvestingRate());
                        gameWorld.increaseMineralCount(unit.getType().getHarvestingRate(), unit.getTeam());
                    }
                    unit.setHasHarvested(true);
            }
    }
    public final void refuel(Unit target) {
        if(canRefuel(target)) {
                target.increaseFuel(unit.getType().getRefuelRate());
                unit.setHasRefueled(true);
        }
    }
    public final void selfDestruct() {
        setDead();
    }
}
