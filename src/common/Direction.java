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
 * @author Dylan Russell
 * 
 * This class represents directions on the game map.
 */
public class Direction {
    
    public final double radians;
    
    public Direction(double radians) {
        this.radians = reduce(radians);
    }
    
    public Direction(double dx, double dy) {
        assertValid(dx);
        assertValid(dy);
        if (dx == 0 && dy == 0) {
            dy = 1;
        }
        this.radians = reduce((double)Math.atan2(dy, dx));
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
    
}
