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
package spaceai;

/**
 *
 * @author Dylan Russell
 */
public interface ActorInfo {
    
    /**
     * Returns the ID of this actor.
     *
     * @return the ID of this actor.
     */
    int getID();

    /**
     * Returns the center location of this actor.
     *
     * @return the center location of this actor.
     */
    MapLocation getLocation();

    /**
     * Returns the radius of this actor.
     *
     * @return the radius of this actor.
     */
    float getRadius();

    /**
     * Returns whether this actor is a ship.
     *
     * @return true if this actor is a ship; false otherwise.
     */
    boolean isShip();

    /**
     * Returns whether this actor is a structure.
     *
     * @return true if this actor is a structure; false otherwise.
     */
    boolean isStructure();

    /**
     * Returns whether this actor is a weapon.
     *
     * @return true if this actor is a weapon; false otherwise.
     */
    boolean isWeapon();
    
    /**
     * Returns whether this actor is an environmental object.
     * 
     * @return true if this actor is an environmental object; false otherwise.
     */
    boolean isEnvironment();
           
        
}
