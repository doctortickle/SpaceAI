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

import java.util.Objects;

/**
 *
 * @author Dylan Russell
 * 
 * Stores basic information about a StructureInfo object.
 */
public class StructureInfo implements ActorInfo {
    
    /**
     * The unique ID of this structure.
     */
    public final int ID;

    /**
     * The Team that this structure is on.
     */
    public final Team team;

    /**
     * The StructureType of this structure.
     */
    public final StructureType type;

    /**
     * The current MapLocation of this structure.
     */
    public final MapLocation location;

    /**
     * The current health of this structure.
     */
    public final float health;
    
    /**
     * The number of times this structure has built in the current turn.
     */
    public final int buildCount;
    
    /**
     * The number of minerals this structure has mined in the current turn.
     */
    public final float minedCount;
    
    /**
     * The number of times this structure has moved in the current turn.
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
        return this.type.structureRadius;
    }

    @Override
    public boolean isShip() {
        return false;
    }

    @Override
    public boolean isStructure() {
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

    public StructureInfo(int ID, Team team, StructureType type, MapLocation location, float health, int buildCount, float minedCount, int moveCount) {
        this.ID = ID;
        this.team = team;
        this.type = type;
        this.location = location;
        this.health = health;
        this.buildCount = buildCount;
        this.minedCount = minedCount;
        this.moveCount = moveCount;
    }

     /**
     * Returns the Team this structure is on.
     * @return the Team this structure is on.
     */
    public Team getTeam() {
        return team;
    }
    /**
     * Returns the StructureType of this structure.
     * @return the StructureType of this structure.
    */
    public StructureType getType() {
        return type;
    }
    /**
     * Returns the health of this structure.
     * @return the health of this structure.
     */
    public float getHealth() {
        return health;
    }
    /**
     * Returns the number of times this structure has built this turn.
     * @return the number of times this structure has built this turn.
     */
    public int getBuildCount() {
        return buildCount;
    }
    /**
     * Returns the number of minerals this structure has mined this turn.
     * @return the number of minerals this structure has mined this turn.
     */
    public float getMinedCount() {
        return minedCount;
    }
    /**
     * Returns the number of times this structure has moved this turn.
     * @return the number of times this structure has moved this turn.
     */
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.ID;
        hash = 53 * hash + Objects.hashCode(this.team);
        hash = 53 * hash + Objects.hashCode(this.type);
        hash = 53 * hash + Objects.hashCode(this.location);
        hash = 53 * hash + Float.floatToIntBits(this.health);
        hash = 53 * hash + this.buildCount;
        hash = 53 * hash + Float.floatToIntBits(this.minedCount);
        hash = 53 * hash + this.moveCount;
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
        final StructureInfo other = (StructureInfo) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (Float.floatToIntBits(this.health) != Float.floatToIntBits(other.health)) {
            return false;
        }
        if (this.buildCount != other.buildCount) {
            return false;
        }
        if (Float.floatToIntBits(this.minedCount) != Float.floatToIntBits(other.minedCount)) {
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
        return "StructureInfo{" + "ID=" + ID + ", team=" + team + ", type=" + type + ", location=" + location + ", health=" + health + ", buildCount=" + buildCount + ", minedCount=" + minedCount + ", moveCount=" + moveCount + '}';
    }
    
    
           
}
