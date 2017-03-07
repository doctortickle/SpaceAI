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
 *
 * @author Dylan Russell
 * 
 * Stores basic information about an Environment object.
 */
public final class Environment extends Actor{
    
    /**
     * The EnvironmentType of this environment object.
     */
    private final EnvironmentType type; 
    /**
     * The number of minerals this environment object has currently remaining.
     */
    private int mineralCount;
    /**
     * The direction of travel of this environment object.
     */
    private Direction direction;
    /**
     * True if this environment has gone off map or been destroyed.
     */
    private boolean destroyed; 
    /**
     * Assigns an EnvironmentController to this environment.
     */
    private final EnvironmentController ec;
    
    @Override
    public boolean isCommandable() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public boolean isEnvironment() {
        return true;
    }
    
    @Override
    public boolean isShip() {
        return false;
    }

    @Override
    public boolean isStructure() {
        return false;
    }
    
    Environment(SpaceAI spaceAI, EnvironmentType type, int ID, Location location) {
        super(ID, type.getMaxHealth(), type.getBodyRadius(), location, Team.NEUTRAL, type.getSpriteImage());
        this.type = type;
        this.mineralCount = type.getMineralMax();
        this.direction = Direction.getRandom();
        this.destroyed = false;
        this.ec = new EnvironmentController(this, spaceAI.getGameWorld());
    }      
    /**
     * Returns the EnvironmentType of this structure.
     * @return the EnvironmentType of this structure.
     */
    public EnvironmentType getType() {
        return type;
    }
    /**
     * Returns the current mineral count of this environment object.
     * @return the current mineral count of this environment object.
     */
    public int getMineralCount() {
        return mineralCount;
    }
    /**
     * Returns direction of travel of this environment object.
     * @return Direction of travel of this environment object
     */
    public Direction getDirection() {
        return direction;               
    }
    /**
     * Set direction of travel for this environment object
     * @param direction direction of travel to be assigned.
     */
    void setDirection(Direction direction) {
        this.direction = direction;
    }
    /**
     * Decreases the current mineral count of this environment object by decrement.
     * @param decrement int to decrease the current mineral count by.
     * @see #mineralCount
     */
    void decreaseMineralCount(int decrement) {
        this.mineralCount -= decrement;
    }
    /**
     * Returns the boolean for destroyed.
     * @return true if environment is destroyed.
     * @see #destroyed
     */
    boolean isDestroyed() {
        return this.destroyed;
    }
    /**
     * Sets the boolean for destroyed.
     * @param destroyed boolean value to be assigned to destroyed.
     * @see #destroyed
     */
    void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }
    @Override
    void update() {
        if(this.getHealth() <= 0) {
            destroyed = true;
        }
        if(!destroyed) {
            ec.rotateImage();
            ec.move();
        }
    }
    @Override
    boolean collide(Actor actor) {
        return ec.collide(actor);
    }
    void damageApplication(Actor actor) {
        ec.damageApplication(actor);
    }
}
