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
 * Stores basic information about a WeaponType object.
 */
public class WeaponInfo implements ActorInfo {
    
    /**
     * The unique ID of this weapon.
     */
    public final int ID;

    /**
     * The WeaponType of this weapon.
     */
    public final WeaponType type;

    /**
     * The Direction in which this weapon is moving.
     */
    public final Direction direction;

    /**
     * The current MapLocation of this weapon.
     */
    public final MapLocation location;

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
        return this.type.weaponRadius;
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
        return true;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }

    public WeaponInfo(int ID, WeaponType type, Direction direction, MapLocation location) {
        this.ID = ID;
        this.type = type;
        this.direction = direction;
        this.location = location;
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
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.ID;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.direction);
        hash = 97 * hash + Objects.hashCode(this.location);
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
        final WeaponInfo other = (WeaponInfo) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (!Objects.equals(this.direction, other.direction)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "WeaponInfo{" + "ID=" + ID + ", type=" + type + ", direction=" + direction + ", location=" + location + '}';
    }
    
}
