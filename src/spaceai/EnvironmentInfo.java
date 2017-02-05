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
 * Stores basic information about an EnvironmentType object.
 */
public class EnvironmentInfo implements ActorInfo {
    
    /**
     * The unique ID of this environment object.
     */
    public final int ID;

    /**
     * The Team that this environment object is on.
     */
    public final Team team;

    /**
     * The EnvironmentType of this environment object.
     */
    public final EnvironmentType type;

    /**
     * The current MapLocation of this environment object.
     */
    public final MapLocation location;

    /**
     * The current health of this environment object.
     */
    public final float health;
    
    /**
     * The number of minerals this environment object has currently remaining.
     */
    public final int mineralCount;
    
    /**
     * The current number of StructureType structures on this environment object.
     */
    public final int structureCount;

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
        return false;
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
        return true;
    }

    public EnvironmentInfo(int ID, Team team, EnvironmentType type, MapLocation location, float health, int mineralCount, int structureCount) {
        this.ID = ID;
        this.team = team;
        this.type = type;
        this.location = location;
        this.health = health;
        this.mineralCount = mineralCount;
        this.structureCount = structureCount;
    }
    
    /**
     * Returns the Team this environment object is on.
     * @return the Team this environment object is on.
     */
    public Team getTeam() {
        return team;
    }
    /**
     * Returns the EnvironmentType of this structure.
     * @return the EnvironmentType of this structure.
    */
    public EnvironmentType getType() {
        return type;
    }
    /**
     * Returns the health of this environment object.
     * @return the health of this environment object.
     */
    public float getHealth() {
        return health;
    }
    /**
     * Returns the current mineral count of this environment object.
     * @return the current mineral count of this environment object.
     */
    public int getMineralCount() {
        return mineralCount;
    }
    /**
     * Returns the current number of StructureType structures built upon this environment object.
     * @return the current number of StructureType structures built upon this environment object.
     */
    public int getStructureCount() {
        return structureCount;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.ID;
        hash = 97 * hash + Objects.hashCode(this.team);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.location);
        hash = 97 * hash + Float.floatToIntBits(this.health);
        hash = 97 * hash + this.mineralCount;
        hash = 97 * hash + this.structureCount;
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
        final EnvironmentInfo other = (EnvironmentInfo) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (Float.floatToIntBits(this.health) != Float.floatToIntBits(other.health)) {
            return false;
        }
        if (this.mineralCount != other.mineralCount) {
            return false;
        }
        if (this.structureCount != other.structureCount) {
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
        return "EnvironmentInfo{" + "ID=" + ID + ", team=" + team + ", type=" + type + ", location=" + location + ", health=" + health + ", mineralCount=" + mineralCount + ", structureCount=" + structureCount + '}';
    }
    
    
    
}
