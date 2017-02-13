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

import javafx.scene.image.Image;

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
    public final WeaponType type;

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

    public Weapon(WeaponType type, int ID, int x, int y) {
        super(ID, Integer.MAX_VALUE, type.getWeaponRadius(), x, y, Team.NEUTRAL, type.getSpriteImage());
        this.type = type;
    }
  
    /**
     * Get the WeaponType of this weapon.
     * @return the WeaponType of this weapon.
     */
    public WeaponType getType() {
        return type;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean collide(Actor object) {
        return false;
    }
    
}
