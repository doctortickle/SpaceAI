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

/**
 *
 * @author dr4ur
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
     * Tracks the cooldown required to build another unit.
     */
    private int buildCooldown;
    private int reloadCooldown;
    private int refuelCooldown;
    /**
     * Tracks if a unit has moved this turn.
     */
    private boolean hasMoved;
    /**
     * Defines the AIController for this unit.
     */
    private boolean dead;
    private boolean stalled;
    private final AIController ac;
    
    
    public Unit(SpaceAI spaceAI, UnitType type, int ID, Location location, Team team) {
        super(ID, type.getMaxHealth(), type.getBodyRadius(), location, team, type.getSpriteImage());
        this.fuel = type.getFuelMax();
        this.type = type;
        this.buildCooldown = 0;
        this.reloadCooldown = 0;
        this.refuelCooldown = 0;
        this.hasMoved = false;
        this.dead = false;
        this.stalled = false;
        this.ac = new AIController(this, spaceAI.getGameWorld());
    }

    @Override
    public void update() {
        beginTurn();
        if(checkCondition()) {
            runAICommand();
        }  
    }
    
    private void beginTurn() {
        System.out.println("\n"+ getType() + " " + getTeam() + getID());
        System.out.println("Health = " + getHealth());
        System.out.println("Fuel = " + fuel);
        if(buildCooldown > 0) {
            buildCooldown--;
        }
        if(reloadCooldown > 0) {
            reloadCooldown--;
        }
        if(refuelCooldown > 0) {
            refuelCooldown--;
        }
        if(hasMoved) {
            hasMoved = false;
        }
        if(getHealth() <= 0) {
            System.out.println("I am dead!");
            dead = true;
        }
        if(fuel <= 0) {
            System.out.println("I am stalled because I have no fuel!");
            stalled = true;
        }
    }
    private boolean checkCondition() {
        return !dead && !stalled;
    }
    private void runAICommand() {
        if(this.getTeam()==Team.A) {
            AICommandA.run(ac);
        }
        if(this.getTeam()==Team.B) {
            AICommandB.run(ac);
        }
    }

    public UnitType getType() {
        return type;
    }
    public int getFuel() {
        return fuel;
    }
    public void setFuel(int remove) {
        this.fuel -= remove;
        if(fuel < 0) {
            fuel = 0;
        }
    }
    public int getBuildCooldown() {
        return buildCooldown;
    }
    public void setBuildCooldown(int i) {
        this.buildCooldown = i;
    }
    public int getReloadCooldown() {
        return reloadCooldown;
    }
    public void setReloadCooldown(int i) {
        this.reloadCooldown = i;
    }
    public int getRefuelCooldown() {
        return refuelCooldown;
    }
    public void setRefuelCooldown(int refuelCooldown) {
        this.refuelCooldown = refuelCooldown;
    }
    public boolean getHasMoved() {
        return hasMoved;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public boolean isDead() {
        return dead;
    }
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    public boolean isStalled() {
        return stalled;
    }
    public void setStalled(boolean stalled) {
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
    public boolean collide(Actor actor) {
        return this.getLocation().distanceTo(actor.getLocation()) < this.getRadius() + actor.getRadius();
    }
}
