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
/**
 * This class is an immutable representation of two-dimensional coordinates
 * in the SpaceAI world.
 */

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

public final strictfp class MapLocation implements Serializable, Comparable<MapLocation> {

    private static final long serialVersionUID = -8945913587066072824L;
    /**
     * The x-coordinate.
     */
    public final float x;
    /**
     * The y-coordinate.
     */
    public final float y;

    /**
     * Creates a new MapLocation representing the location
     * with the given coordinates.
     *
     * @param x the x-coordinate of the location
     * @param y the y-coordinate of the location
     *
     */
    public MapLocation(float x, float y) {
        this.x = x == Float.NaN ? 0 : x;
        this.y = y == Float.NaN ? 0 : y;
    }

    /**
     * A comparison function for MapLocations. Smaller x values go first, with ties broken by smaller y values.
     *
     * @param other the MapLocation to compare to.
     * @return whether this MapLocation goes before the other one.
     *
     */
    @Override
    public int compareTo(MapLocation other) {
        if (x != other.x) {
            if(x < other.x)
                return -1;
            else
                return 1;
        } else {
            if(y < other.y)
                return -1;
            else if (y > other.y)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Two MapLocations are regarded as equal if
     * their coordinates are the same.
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof MapLocation)) {
            return false;
        }

        return (((MapLocation) obj).x == this.x) && (((MapLocation) obj).y == this.y);
    }

    @Override
    public int hashCode() {
        return Float.floatToIntBits(this.x) * 13 + Float.floatToIntBits(this.y) * 23;
    }

    public static MapLocation valueOf(String s) {
        String[] coord = StringUtils.replaceChars(s, "[](){}", null).split(",");
        if (coord.length != 2)
            throw new IllegalArgumentException("Invalid map location string");
        float x = Float.valueOf(coord[0].trim());
        float y = Float.valueOf(coord[1].trim());

        return new MapLocation(x, y);
    }

    /**
     * Checks where two given circles collide at any point.
     *
     * @param center1 the center of the first circle
     * @param radius1 the radius of the first circle
     * @param center2 the center of the second circle
     * @param radius2 the radius of the second circle
     * @return true if the given circles collide at any point, false otherwise.
     *
     */
    public static boolean doCirclesCollide(MapLocation center1, float radius1, MapLocation center2, float radius2){
        return center1.distanceTo(center2) <= radius1 + radius2;
    }

    @Override
    public String toString() {
        return String.format("[%f, %f]", this.x, this.y);
    }

    /**
     * Computes the Euclidean distance from this location to the specified
     * location.
     *
     * @param location the location to compute the distance to
     * @return the distance to the given location
     *
     */
    public final float distanceTo(MapLocation location) {
        float dx = this.x - location.x;
        float dy = this.y - location.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Computes the squared distance from this location to the specified
     * location.
     *
     * @param location the location to compute the squared distance to
     * @return the squared distance to the given location
     *
     */
    public final float distanceSquaredTo(MapLocation location) {
        float dx = this.x - location.x;
        float dy = this.y - location.y;
        return dx * dx + dy * dy;
    }

    /**
     * Determines whether this location is within a specified distance
     * from target location.
     *
     * @param location the location to test
     * @param dist the distance for the location to be within
     * @return true if the given location is within dist to this one; false otherwise
     *
     */
    public final boolean isWithinDistance(MapLocation location, float dist) {
        return this.distanceTo(location) <= dist;
    }

    /**
     * Determines whether this location is within one stride of the given ship.
     *
     * @param ship the ship to test
     * @return true if this location is within one flight radius of the given ship; false otherwise
     *
     */
    public final boolean isWithinFlightRadius(ShipInfo ship){
        return isWithinDistance(ship.location, ship.type.flightRadius);
    }
    
    /**
     * Determines whether this location is within the enemy sensor radius of the
     * given ship.
     *
     * @param ship the robot to test
     * @return true if this location is within the ship's enemy sensor radius,
     *         false otherwise
     *
     */
    public final boolean isWithinEnemySensorRadius(ShipInfo ship){
        return isWithinDistance(ship.location, ship.type.enemySensorRadius);
    }

    /**
     * Determines whether this location is within the incoming weapon sight radius of the
     * given ship.
     *
     * @param ship the ship to test
     * @return true if this location is within ship's incoming weapon sight radius,
     *         false otherwise
     *
     */
    public final boolean isWithinIncomingDetectionRadius(ShipInfo ship){
        return isWithinDistance(ship.location, ship.type.incomingDetectionRadius);
    }
    
    /**
     * Determines whether this location is within the detection radius of the
     * given weapon. Only MINE will have a detection radius greater than 0.
     *
     * @param mine the weapon to test
     * @return true if this location is within WeaponTypes detection radius,
     *         false otherwise
     *
     */
    public final boolean isWithinDetectionRadius(WeaponInfo mine){
        return isWithinDistance(mine.location, mine.type.detectionRadius);
    }
    
    /**
     * Determines whether this location is within the explosion radius of the
     * given weapon. Only MINE, SMALL_BOMB, and LARGE_BOMB will have an explosion radius greater than 
     * their body radius.
     * 
     * @param weapon the weapon to test
     * @return true if this location is within WeaponTypes explosion radius,
     *         false otherwise
     *
     */
    public final boolean isWithinExplosionRadius(WeaponInfo weapon){
        return isWithinDistance(weapon.location, weapon.type.explosionRadius);
    }
    
    /**
     * Determines whether this location is within one stride of the given structure.
     *
     * @param structure the structure to test
     * @return true if this location is within one flight radius of the given structure; false otherwise
     *
     */
    public final boolean isWithinFlightRadius(StructureInfo structure){
        return isWithinDistance(structure.location, structure.type.flightRadius);
    }
    
    /**
     * Determines whether this location is within the refuel radius of the given structure. Only FUEL_STATION
     * will have a refuel radius greater than 0.
     *
     * @param fuelStation the structure to test
     * @return true if this location is within the refuel radius radius of the given structure; false otherwise
     *
     */
    public final boolean isWithinRefuelRadius(StructureInfo fuelStation){
        return isWithinDistance(fuelStation.location, fuelStation.type.refuelRadius);
    }

    /**
     * Returns the Direction from this MapLocation to <code>location</code>.
     * If <code>location</code> is null then the return value is null.
     * If <code>location</code> equals this location then the return value is null.
     *
     * @param location The location to which the Direction will be calculated
     * @return The Direction to <code>location</code> from this MapLocation.
     *
     */
    public final Direction directionTo(MapLocation location) {
        if(location == null) {
            return null;
        }
        if(this.equals(location)){
            return null;
        }
        return new Direction(this,location);
    }

    /**
     * Returns a new MapLocation object representing a location
     * one unit in distance from this one in the given direction.
     *
     * @param direction the direction to add to this location
     * @return a MapLocation for the location one unit in distance in the given
     *         direction.
     *
     */
    public final MapLocation add(Direction direction) {
        if(direction == null) {
            return new MapLocation(x ,y);
        }
        float dx = (float)Math.cos(direction.radians);
        float dy = (float)Math.sin(direction.radians);
        return new MapLocation(x + dx, y + dy);
    }

    /**
     * Returns a new MapLocation object representing a location
     * one unit in distance from this one in the given direction
     * represented in radians.
     *
     * @param radians the radians of the direction to add to this location,
     *                note that 0 radians points right
     * @return a MapLocation for the location one unit in distance in the given
     *         direction.
     *
     */
    public final MapLocation add(float radians) {
        return this.add(new Direction(radians));
    }

    /**
     * Returns a new MapLocation object representing a location
     * {@code dist} units away from this one in the given direction.
     *
     * @param direction the direction to add to this location
     * @param dist  the distance the locations should be apart
     * @return a MapLocation for the location dist away from this location
     *         in the given direction.
     *
     */
    public final MapLocation add(Direction direction, float dist) {
        if(direction == null) {
            return new MapLocation(x ,y);
        }
        float dx = (float)(dist * Math.cos(direction.radians));
        float dy = (float)(dist * Math.sin(direction.radians));
        return new MapLocation(x + dx, y + dy);
    }

    /**
     * Returns a new MapLocation object representing a location
     * {@code dist} units away from this one in the given direction
     * represented in radians.
     *
     * @param radians the radians of the direction to add to this location,
     *                note that 0 radians points right
     * @param dist  the distance the locations should be apart
     * @return a MapLocation for the location dist away from this location
     *         in the given direction.
     *
     */
    public final MapLocation add(float radians, float dist) {
        return this.add(new Direction(radians), dist);
    }

    /**
     * Returns a new MapLocation object representing a location
     * one unit in distance from this one in the opposite direction
     * of the given direction.
     *
     * @param direction the direction to subtract from this location
     * @return a MapLocation for the location one unit in distance in the
     *         opposite of the given direction.
     *
     */
    public final MapLocation subtract(Direction direction) {
        if(direction == null) {
            return new MapLocation(x,y);
        }
        return this.add(direction.opposite());
    }

    /**
     * Returns a new MapLocation object representing a location
     * one unit in distance from this one in the opposite direction of the
     * given direction represented in radians.
     *
     * @param radians the radians of the direction to subtract from this location,
     *                note that 0 radians points right
     * @return a MapLocation for the location one unit in distance in the given
     *         direction.
     *
     */
    public final MapLocation subtract(float radians) {
        return this.subtract(new Direction(radians));
    }

    /**
     * Returns a new MapLocation object representing a location
     * {@code dist} units in distance from this one in the opposite direction
     * of the given direction.
     *
     * @param direction the direction to subtract from this location
     * @param dist  the distance the locations should be apart
     * @return a MapLocation for the location dist away from this location
     *         in the opposite of the given direction.
     *
     */
    public final MapLocation subtract(Direction direction, float dist) {
        if(direction == null) {
            return new MapLocation(x,y);
        }
        return this.add(direction.opposite(), dist);
    }

    /**
     * Returns a new MapLocation object representing a location
     * {@code dist} units in distance from this one in the opposite direction of the
     * given direction represented in radians.
     *
     * @param radians the radians of the direction to subtract from this location,
     *                note that 0 radians points right
     * @param dist  the distance the locations should be apart
     * @return a MapLocation for the location one unit in distance in the given
     *         direction.
     *
     */
    public final MapLocation subtract(float radians, float dist) {
        return this.subtract(new Direction(radians), dist);
    }

    /**
     * Returns a new MapLocation object translated from this location
     * by a fixed amount.
     *
     * @param dx the amount to translate in the x direction
     * @param dy the amount to translate in the y direction
     * @return the new MapLocation that is the translated version of the original.
     *
     */
    public final MapLocation translate(float dx, float dy) {
        return new MapLocation(x + dx, y + dy);
    }

    /**
     * For use by serializers.
     *
     */
    private MapLocation() {
        this(0,0);
    }
}
