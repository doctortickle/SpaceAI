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
 * @author Dylan
 */
public class ShipInfo implements ActorInfo {
    
        /**
     * The unique ID of this ship.
     */
    public final int ID;

    /**
     * The Team that this ship is on.
     */
    public final Team team;

    /**
     * The type of this ship.
     */
    public final ShipType type;

    /**
     * The current location of this ship.
     */
    public final MapLocation location;

    /**
     * The current health of this ship.
     */
    public final float health;
    
    /**
     * The number of times this Ship has attacked in the current turn.
     */
    public final int attackCount;
    
    /**
     * The number of times this Ship has moved in the current turn.
     */
    public final int moveCount;

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public MapLocation getLocation() {
        return this.location;
    }

    @Override
    public float getRadius() {
        return this.type.bodyRadius;
    }

    @Override
    public boolean isShip() {
        return true;
    }

    @Override
    public boolean isStructure() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }

    public ShipInfo(int ID, Team team, ShipType type, MapLocation location, float health, int attackCount, int moveCount) {
        super();
        this.ID = ID;
        this.team = team;
        this.type = type;
        this.location = location;
        this.health = health;
        this.attackCount = attackCount;
        this.moveCount = moveCount;
    }
    
    
    
}
