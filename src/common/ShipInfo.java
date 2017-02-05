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

import java.util.Objects;

/**
 *
 * @author Dylan Russell
 * 
 * Stores basic information about a ShipType object.
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
     * The ShipType of this ship.
     */
    public final ShipType type;

    /**
     * The current MapLocation of this ship.
     */
    public final MapLocation location;

    /**
     * The current health of this ship.
     */
    public final float health;
    
    /**
     * The number of times this ship has attacked in the current turn.
     */
    public final int attackCount;
    
    /**
     * The number of times this ship has moved in the current turn.
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
    
    /**
     * Returns the Team this ship is on.
     * @return the team this ship is on.
     */
    public Team getTeam() {
        return team;
    }
    /**
     * Returns the ShipType of this ship.
     * @return the ShipType of this ship.
    */
    public ShipType getType() {
        return type;
    }
    /**
     * Returns the health of this ship.
     * @return the health of this ship.
     */
    public float getHealth() {
        return health;
    }
    /**
     * Returns the current attack count of this ship this round.
     * @return the attack count of this ship this round.
     */
    public int getAttackCount() {
        return attackCount;
    }
    /**
     * Returns the current move count of this ship this round.
     * @return the current move count of this ship this round. 
     */
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.ID;
        hash = 71 * hash + Objects.hashCode(this.team);
        hash = 71 * hash + Objects.hashCode(this.type);
        hash = 71 * hash + Objects.hashCode(this.location);
        hash = 71 * hash + Float.floatToIntBits(this.health);
        hash = 71 * hash + this.attackCount;
        hash = 71 * hash + this.moveCount;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ShipInfo other = (ShipInfo) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (Float.floatToIntBits(this.health) != Float.floatToIntBits(other.health)) {
            return false;
        }
        if (this.attackCount != other.attackCount) {
            return false;
        }
        if (this.moveCount != other.moveCount) {
            return false;
        }
        if (this.team != other.team) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ShipInfo{" + "ID=" + ID + ", team=" + team + ", type=" + type + ", location=" + location + ", health=" + health + ", attackCount=" + attackCount + ", moveCount=" + moveCount + '}';
    }
    
    
    
}
