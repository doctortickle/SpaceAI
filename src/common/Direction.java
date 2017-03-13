/*
 * Copyright (C) 2017 dr4ur
 * This game is a heavily modified version of Battlecode 2017. I give 
 * significant credit to the team at MIT for their work on Battlecode and for
 * inspiring this body of work. Please check out battlecode.org for more info.
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

import java.util.Random;
import static common.DecimalUtils.round;

/**
 *
 * @author Dylan Russell
 * 
 * This class represents directions on the game map.
 */
public strictfp class Direction {
    
    private final double radians;
    private final double degrees;
    public static final Direction NORTH = getNorth();
    public static final Direction SOUTH = getSouth();
    public static final Direction EAST = getEast();
    public static final Direction WEST = getWest();
    
    // *********************************
    // ********** CONSTRUCTORS *********
    // *********************************
    
    public Direction(double radians) {
        this.radians = reduce(radians);
        this.degrees = calcDegrees(radians);
    }   
    public Direction(double dx, double dy) {
        assertValid(dx);
        assertValid(dy);
        if (dx == 0 && dy == 0) {
            dy = 1;
        }
        this.radians = reduce(Math.atan2(dy, dx));
        this.degrees = calcDegrees(radians);
    }
    public Direction(Location start, Location finish) {
        this(finish.getX() - start.getX(), finish.getY() - start.getY());
    }
    
    // *********************************
    // ****** GETTERS AND SETTERS ******
    // *********************************
    
    public double getRadians() {
        return this.radians;
    }
    public double getDegrees() {
        return this.degrees;
    }
    private double calcRadians(double degrees) {
        return Math.toRadians(degrees);
    }
    private double calcDegrees(double radians) {
        return Math.toDegrees(radians);
    }
    
    // *********************************
    // ***** MANIPULATION METHODS ******
    // *********************************
    
    public Direction rotateLeftRads(double radians) {
        return new Direction(this.radians + radians);
    }
    public Direction rotateRightRads(double radians) {
        return new Direction(this.radians - radians);
    }
    public Direction opposite() {
        return rotateLeftRads(Math.PI);
    }
    public Direction getOrthogonalLeft() {
        return rotateLeftRads(Math.PI/2);
    }
    public Direction getOrthogonalRight() {
        return rotateRightRads(Math.PI/2);
    }
    public double radiansBetween(Direction other) {
        return reduce(other.radians - this.radians);
    }
    public double degreesBetween(Direction other) {
        return Math.toDegrees(radiansBetween(other));
    }
    public static Direction getRandomDirection() {
        int start = -1;
        int end = 1;
        double x = start + (new Random().nextDouble() * (end - start));
        double y = start + (new Random().nextDouble() * (end - start));
        return new Direction(x,y);
    }
    
    // *********************************
    // ******* INTERNAL METHODS ********
    // *********************************
    
    private static Direction getNorth() {
        return new Direction(0,1);
    }
    private static Direction getSouth() {
        return new Direction(0,-1);
    }
    private static Direction getEast() {
        return new Direction(1,0);
    }
    private static Direction getWest() {
        return new Direction(-1,0);
    }
    // Internally used to keep angles in the range (-Math.PI,Math.PI]
    private double reduce(double rads) {
        if(rads <= -(double)Math.PI) {
            int circles = (int)Math.ceil(-(rads+Math.PI)/(2*Math.PI));
            return rads + (double)(Math.PI*2*circles);
        } else if (rads > (double)Math.PI) {
            int circles = (int)Math.ceil((rads-Math.PI)/(2*Math.PI));
            return rads - (double)(Math.PI*2*circles);
        }
        return rads;
    }
    // Stop NaN or infinity directions from messing things up
    private void assertValid(double num) {
        if(Double.isNaN(num)) {
            throw new RuntimeException("Direction can not take a NaN double as an argument");
        } else if (Double.isInfinite(num)) {
            throw new RuntimeException("Direction can not take +/- infinity as an argument");
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.radians) ^ (Double.doubleToLongBits(this.radians) >>> 32));
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.degrees) ^ (Double.doubleToLongBits(this.degrees) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other == this) return true;
        if(!(other instanceof Direction)) return false;
        return this.radians == ((Direction) other).radians;
    }
    public boolean equals(Direction other, double epsilon) {
        double difference = this.radiansBetween(other);
        return (Math.abs(difference) <= epsilon);
    }  
}
