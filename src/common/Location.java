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

import static common.GameConstants.COORDINATE_TO_PIXEL;
import static common.GameConstants.PIXEL_TO_COORDINATE;

/**
 *
 * @author Dylan Russell
 * 
 * This class intended to convert pixel locations into a coordinate system for 
 * use in the SpaceAI game. 
 */
public class Location {
    
    private final double x;
    private final double y;
    private final double pixelX;
    private final double pixelY;
    
    public Location(double x, double y) {
        this.x = x == Float.NaN ? 0 : x;
        this.y = y == Float.NaN ? 0 : y;
        this.pixelX = coordinateToPixel(x);
        this.pixelY = -(coordinateToPixel(y));
    }
    
    static double coordinateToPixel(double i) {
        return i*COORDINATE_TO_PIXEL;      
    }
    
    static double pixelToCoordinate(double i) {
        if(i==0) {
            return 0;
        }
        return i*PIXEL_TO_COORDINATE;      
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getPixelX() {
        return pixelX;
    }

    public double getPixelY() {
        return pixelY;
    }
    
    public final double distanceTo(Location location) {
        double dx = this.getX() - location.getX();
        double dy = this.getY() - location.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
   
}
