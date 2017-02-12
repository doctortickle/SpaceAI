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
public class Unit extends Actor {

    /**
     * The UnitType of this unit.
     */
    private final UnitType type;
    /**
     * The current fuel in this unit's tank.
     */
    private final int fuel;
    
    public Unit(UnitType type, int ID, int x, int y, Team team) {
        super(ID, type.maxHealth, type.bodyRadius, x, y, team, type.spriteImage);
        this.fuel = type.fuelMax;
        this.type = type;
    }

    @Override
    public void update() {
        this.x += this.type.flightRadius;
        this.spriteFrame.setTranslateX(x);
    }

    public UnitType getType() {
        return type;
    }

    public int getFuel() {
        return fuel;
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
    public boolean collide(Actor object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
