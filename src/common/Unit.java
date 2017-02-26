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
    /**
     * Tracks if a unit has moved this turn.
     */
    private boolean hasMoved;
    /**
     * Defines the AIController for this unit.
     */
    private final AIController ac;
    
    
    public Unit(SpaceAI spaceAI, UnitType type, int ID, Location location, Team team) {
        super(ID, type.getMaxHealth(), type.getBodyRadius(), location, team, type.getSpriteImage());
        this.fuel = type.getFuelMax();
        this.type = type;
        this.buildCooldown = 0;
        this.hasMoved = false;
        this.ac = new AIController(this, spaceAI.gameWorld);
    }

    @Override
    public void update() {
        if(this.getTeam()==Team.A) {
            AICommandA.run(ac);
        }
        if(this.getTeam()==Team.B) {
            AICommandB.run(ac);
        }
        if(buildCooldown > 0) {
            buildCooldown--;
        }
    }

    public UnitType getType() {
        return type;
    }
    public int getFuel() {
        return fuel;
    }
    public int getBuildCooldown() {
        return buildCooldown;
    }
    public void setBuildCooldown(int i) {
        this.buildCooldown = i;
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
        if(this.getLocation().distanceTo(actor.getLocation()) < this.getRadius() + actor.getRadius()) {
            return true;
        }
        else {
            return false;
        }
    }
}
