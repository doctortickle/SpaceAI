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
 * Stores basic information about a WeaponType object.
 */
public final class Weapon extends Actor {
    
    /**
     * The WeaponType of this weapon.
     */
    private final WeaponType type;
    private final Direction direction;
    private boolean spent;
    private boolean exploded;
    private final WeaponController wc;
    private int clearCountdown;

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

    public Weapon(SpaceAI spaceAI, WeaponType type, int ID, Location location, Team team, Direction direction) {
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
        this.wc = new WeaponController(this, spaceAI.gameWorld);
    }
  
    /**
     * Get the WeaponType of this weapon.
     * @return the WeaponType of this weapon.
     */
    public WeaponType getType() {
        return type;
    }
    
    public Direction getDirection() {
        return direction;
    }

    public boolean isSpent() {
        return spent;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    public int getClearCountdown() {
        return clearCountdown;
    }

    public void setClearCountdown(int clearCountdown) {
        this.clearCountdown = clearCountdown;
    }

    @Override
    public void update() {
        if(!isSpent() && (getType() != WeaponType.MINE)) {
            wc.move(direction);
        }
        if(isSpent() && isExploded()) {
            clearCountdown++;
        }
    }

    @Override
    public boolean collide(Actor actor) {
        return wc.collide(actor);
    }   
    public void damageApplication(Actor actor) {
        wc.damageApplication(actor);
    }   
}
