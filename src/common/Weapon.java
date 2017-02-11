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

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 * 
 * Stores basic information about a WeaponType object.
 */
public class Weapon extends Actor {
    
    /**
     * The WeaponType of this weapon.
     */
    public final WeaponType type;

    /**
     * The Direction in which this weapon is moving.
     */
    public final Direction direction;

    @Override
    public boolean isShip() {
        return false;
    }

    @Override
    public boolean isStructure() {
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

    public Weapon(WeaponType type, Direction direction, int ID, Team team, MapLocation location, Image imageSprite) {
        super(type.actorType, ID, Integer.MAX_VALUE, type.weaponRadius, Team.NEUTRAL, location, imageSprite);
        this.type = type;
        this.direction = direction;
    }
  
    /**
     * Get the WeaponType of this weapon.
     * @return the WeaponType of this weapon.
     */
    public WeaponType getType() {
        return type;
    }
    /**
     * Return the Direction this weapon is currently traveling in.
     * @return the direction this weapon is currently traveling in.
     */
    public Direction getDirection() {
        return direction;
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
