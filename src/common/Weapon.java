/*
 * Copyright (C) 2017 Dylan Russell
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

/**
 * This class represents actors that deal damage to other units and environment actors.
 * @author Dylan Russell
 * @version 1.0
 * @see WeaponType
 */
public final class Weapon extends Actor {
    
    /**
     * The WeaponType of this weapon.
     */
    private final WeaponType type;
    /**
     * The direction of travel of this weapon.
     */
    private final Direction direction;
    /**
     * True if this weapon has collided with another actor.
     */
    private boolean spent;
    /**
     * True if this weapon has dealt damage to another actor.
     */
    private boolean exploded;
    /**
     * Tracks the countdown until the explosion animation is removed from the screen.
     */
    private int clearCountdown;
    /**
     * Holds the WeaponController for this weapon.
     */
    private final WeaponController wc;
    /**
     * Creates a new weapon object.
     * @param spaceAI the instance of SpaceAI that animates the game
     * @param type the WeaponType of this weapon
     * @param ID the unique ID of this weapon
     * @param location the current location of this weapon
     * @param team the team of this weapon
     * @param direction the direction of travel of this weapon
     */
    Weapon(SpaceAI spaceAI, WeaponType type, int ID, Location location, Team team, Direction direction) {
        super(ID, Integer.MAX_VALUE, type.getCollisionRadius(type), location, team, type.getSpriteImage());
        this.type = type;
        this.spent = false;
        this.exploded = false;
        this.clearCountdown = 0;
        if(type == WeaponType.MINE) {
            this.direction = null;
        }
        else {
            this.direction = direction;
        }
        this.wc = new WeaponController(this, spaceAI.getGameWorld());
    } 
    
    /**
     * Returns the WeaponType of this weapon.
     * @return WeaponType of this weapon.
     * @see WeaponType
     */
    public WeaponType getType() {
        return type;
    }  
    /**
     * Returns the direction of travel of this weapon.
     * @return Direction of travel of this weapon
     * @see Direction
     * @see #direction
     */
    public Direction getDirection() {
        return direction;
    }
    /**
     * Returns true if the weapon has collided with another actor.
     * @return boolean true if this weapon has collided with another actor.
     * @see #spent
     */
    boolean isSpent() {
        return spent;
    }
    /**
     * Sets the value of spent.
     * @param spent boolean value to be assigned to spent.
     * @see spent
     */
    void setSpent(boolean spent) {
        this.spent = spent;
    }
    /**
     * Returns true if the weapon has incurred damage upon another actor.
     * @return boolean true if this weapon has incurred damage upon another actor.
     * @see #exploded
     */
    boolean isExploded() {
        return exploded;
    }
    /**
     * Sets the value of exploded.
     * @param exploded boolean value to be assigned to exploded.
     * @see exploded
     */
    void setExploded(boolean exploded) {
        this.exploded = exploded;
    }
    /**
     * Returns the countdown until the explosion animation can be cleared from the game screen.
     * @return int the countdown until the explosion animation can be cleared from the game screen.
     * @see #clearCountdown
     */
    int getClearCountdown() {
        return clearCountdown;
    }
    /**
     * Sets the value of clearCountdown to the inputted value.
     * @param clearCountdown int value to be assigned to clearCountdown
     * @see #clearCountdown
     */
    void setClearCountdown(int clearCountdown) {
        this.clearCountdown = clearCountdown;
    }
    @Override
    void update() {
        if(!isSpent() && (getType() != WeaponType.MINE)) {
            wc.move(direction);
        }
        if(isSpent() && isExploded()) {
            clearCountdown++;
        }
    }
    @Override
    public boolean isCommandable() {
        return false;
    }
    @Override
    public boolean isWeapon() {
        return true;
    }
    @Override
    public boolean isEnvironment() {
        return false;
    }   
    @Override
    public boolean isShip() {
        return false;
    }
    @Override
    public boolean isStructure() {
        return false;
    }
    @Override
    boolean collide(Actor actor) {
        return wc.collide(actor);
    } 
    /**
     * Runs the damageApplication function for the given actor in WeaponController.
     * @param actor the actor for whom damageApplication will be run.
     * @see WeaponController
     * @see WeaponController#damageApplication(common.Actor) 
     */
    void damageApplication(Actor actor) {
        wc.damageApplication(actor);
    }   
}
