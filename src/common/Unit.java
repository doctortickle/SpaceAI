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

import player.AICommandA;
import player.AICommandB;

/**
 * This class represents actors that are directly commandable by the player via AICommand.
 * @author Dylan Russell
 * @author Myles Russell
 * @version 1.0
 * @see UnitType
 */
public final class Unit extends Actor {

    /**
     * The UnitType of this unit.
     */
    private final UnitType type;
    /**
     * The current fuel in this unit's tank.
     */
    private int fuel;
    /**
     * Tracks the cooldown (# of turns) required to buildShip another unit.
     */
    private int buildCooldown;
    /**
     * Tracks the cooldown (# of turns) required to fire another weapon.
     */
    private int reloadCooldown;
    /**
     * True if a unit has refueled this turn.
     */
    private boolean hasRefueled;
    /**
     * True if a unit has moved this turn.
     */
    private boolean hasMoved;
    /**
     * True if a unit has harvested this turn.
     */
    private boolean hasHarvested;
    /**
     * True if a unit is dead. 
     */
    private boolean dead;
    /**
     * True if a unit is stalled due to zero fuel.
     */
    private boolean stalled;
    /**
     * Holds the AIController for this unit.
     */
    private final AIController ac;
    private final AICommandA playerA;
    private final AICommandB playerB;
    
    /**
     * Creates a new unit object.
     * @param spaceAI the instance of SpaceAI that animates the game
     * @param type the UnitType of the unit to be instantiated
     * @param ID the ID of the unit to be instantiated
     * @param location the location of the unit to be instantiated
     * @param team the team of the unit to be instantiated
     */
    Unit(SpaceAI spaceAI, UnitType type, int ID, Location location, Team team) {
        super(ID, type.getMaxHealth(), type.getBodyRadius(), location, team, type.getSpriteImage());
        this.fuel = type.getFuelMax();
        this.type = type;
        this.buildCooldown = 0;
        this.reloadCooldown = 0;
        this.hasRefueled = false;
        this.hasMoved = false;
        this.hasHarvested = false;
        this.dead = false;
        this.stalled = false;
        this.ac = new AIController(this, spaceAI.getGameWorld());
        this.playerA = new AICommandA();
        this.playerB = new AICommandB();
    }

    @Override
    void update() {
        beginTurn();
        if(checkCondition()) {
            runAICommand();
        }  
    }
    /**
     * Various unit checks to be conducted at the beginning of every turn.
     */
    private void beginTurn() {
        System.out.println("\n"+ getType() + " " + getTeam() + getID());
        System.out.println("Health = " + getHealth());
        if(type.isShip()) {
            System.out.println("Fuel = " + fuel);
        }
        if(buildCooldown > 0) {
            buildCooldown--;
        }
        if(reloadCooldown > 0) {
            reloadCooldown--;
        }
        if(hasRefueled) {
            hasRefueled = false;
        }
        if(hasMoved) {
            hasMoved = false;
        }
        if(hasHarvested) {
            hasHarvested = false;
        }
        if(getHealth() <= 0) {
            System.out.println("I am dead!");
            dead = true;
        }
        if(fuel <= 0) {
            System.out.println("I am stalled because I have no fuel!");
            stalled = true;
        }
        else if(fuel > 0) {
            stalled = false;
        }
    }
    /**
     * Checks if a unit is either dead or stalled.
     * @return true if the unit is neither dead nor stalled
     * @see #dead
     * @see #stalled
     */
    private boolean checkCondition() {
        return !dead && !stalled;
    }
    /**
     * Runs the AICommand class that corresponds to the units team.
     */
    private void runAICommand() {
        if(this.getTeam()==Team.A) {
            playerA.run(ac);
        }
        if(this.getTeam()==Team.B) {
            playerB.run(ac);
        }
    }
    /**
     * Returns the UnitType of this unit.
     * @return UnitType of this unit
     * @see UnitType
     */
    public UnitType getType() {
        return type;
    }
    /**
     * Returns the current fuel in this unit's tank.
     * @return int current fuel in this unit's tank
     * @see #fuel
     */
    int getFuel() {
        return fuel;
    }
    /**
     * Decreases the fuel by the parameter remove.
     * @param remove amount to decrease this unit's fuel
     * @see #fuel
     */
    void decreaseFuel(int remove) {
        this.fuel -= remove;
        if(fuel < 0) {
            fuel = 0;
        }
    }
    /**
     * Increases the fuel by the parameter add.
     * @param add amount to increase this unit's fuel
     * @see #fuel
     */
    void increaseFuel(int add) {
        this.fuel += add;
        if(fuel > this.getType().getFuelMax()) {
            fuel = this.getType().getFuelMax();
        }
    }
    /**
     * Returns the current buildShip cooldown for this unit.
     * @return int current buildShip cooldown for this unit
     * @see #buildCooldown
     */
    int getBuildCooldown() {
        return buildCooldown;
    }
    /**
     * Set the buildShip cooldown of this unit to the parameter i.
     * @param i buildShip cooldown to be applied to this unit.
     * @see #buildCooldown
     */
    void setBuildCooldown(int i) {
        this.buildCooldown = i;
    }
    /**
     * Returns the current reload cooldown for this unit.
     * @return int current reload cooldown for this unit.
     * @see #reloadCooldown
     */
    int getReloadCooldown() {
        return reloadCooldown;
    }
    /**
     * Set the reload cooldown of this unit to parameter i.
     * @param i reload cooldown to be applied to this unit.
     * @see #reloadCooldown
     */
    void setReloadCooldown(int i) {
        this.reloadCooldown = i;
    }
    /**
     * Returns the current value of hasRefueled for this unit.
     * @return boolean hasRefueled
     * @see #hasRefueled
     */
    boolean getHasRefueled() {
        return hasRefueled;
    }
    /**
     * Set the current value of hasRefueled for this unit.
     * @param hasRefueled true if unit has refueled this turn.
     * @see #hasRefueled
     */
    void setHasRefueled(boolean hasRefueled) {
        this.hasRefueled = hasRefueled;
    }
    /**
     * Returns the current value of hasMoved for this unit.
     * @return boolean hasMoved
     * @see #hasMoved
     */
    boolean getHasMoved() {
        return hasMoved;
    }
    /**
     * Set the current value of hasMoved for this unit.
     * @param hasMoved true if unit has moved this turn.
     * @see #hasMoved
     */
    void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    /**
     * Returns the current value of hasHarvested for this unit.
     * @return boolean hasHarvested
     * @see #hasHarvested
     */
    boolean getHasHarvested() {
        return hasHarvested;
    }
    /**
     * Set the current value of hasHarvested for this unit.
     * @param hasHarvested true if unit has harvested this turn.
     * @see #hasHarvested
     */
    void setHasHarvested(boolean hasHarvested) {
        this.hasHarvested = hasHarvested;
    }
    /**
     * Returns the current value of dead for this unit.
     * @return boolean dead
     * @see #dead
     */
    boolean isDead() {
        return dead;
    }
    /**
     * Set the current value of dead for this unit.
     * @param dead true if unit is dead.
     * @see #dead
     */
    void setDead(boolean dead) {
        this.dead = dead;
    }
    /**
     * Returns the current value of stalled for this unit.
     * @return boolean stalled
     * @see #stalled
     */
    boolean isStalled() {
        return stalled;
    }
    /**
     * Set the current value of stalled for this unit.
     * @param stalled true if unit is stalled.
     * @see #stalled
     */
    void setStalled(boolean stalled) {
        this.stalled = stalled;
    }
     
    @Override
    public boolean isCommandable() {
        return true;
    }
    @Override
    public boolean isWeapon() {
        return false;
    }
    @Override
    public boolean isEnvironment() {
        return false;
    }
    @Override
    public boolean isShip() {
        return getType().isShip();
    }
    @Override
    public boolean isStructure() {
        return getType().isStructure();
    }  
    @Override
    boolean collide(Actor actor) {
        return this.getLocation().distanceTo(actor.getLocation()) < this.getRadius() + actor.getRadius();
    }
}
