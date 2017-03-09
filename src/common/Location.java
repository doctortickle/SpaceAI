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

import static common.DecimalUtils.round;
import static common.GameConstants.COORDINATE_TO_PIXEL;
import static common.GameConstants.PIXEL_TO_COORDINATE;

/**
 *
 * @author Dylan Russell
 * 
 * This class intended to convert pixel locations into a coordinate system for 
 * use in the SpaceAI game. 
 */
public strictfp class Location {
    
    private double x;
    private double y;
    private double pixelX;
    private double pixelY;
    
    public Location(double x, double y) {
        this.x = x == Double.NaN ? 0 : x;
        this.y = y == Double.NaN ? 0 : y;
        this.pixelX = coordinateToPixel(x);
        this.pixelY = -coordinateToPixel(y);
    }
    public Location(Unit unit) {
        this.x = unit.getLocation().getX();
        this.y = unit.getLocation().getY();
        this.pixelX = coordinateToPixel(x);
        this.pixelY = -coordinateToPixel(y);
    }
    public Location(Weapon weapon) {
        this.x = weapon.getLocation().getX();
        this.y = weapon.getLocation().getY();
        this.pixelX = coordinateToPixel(x);
        this.pixelY = -coordinateToPixel(y);
    }
    public Location(Environment environment) {
        this.x = environment.getLocation().getX();
        this.y = environment.getLocation().getY();
        this.pixelX = coordinateToPixel(x);
        this.pixelY = -coordinateToPixel(y);
    }
    
    private double coordinateToPixel(double i) {
        return i*COORDINATE_TO_PIXEL;      
    }
    private double pixelToCoordinate(double i) {
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
    public void setX(double x) {
        this.x = x == Float.NaN ? 0 : x;
        this.pixelX = coordinateToPixel(x);
    } 
    public void setY(double y) {
        this.y = y == Float.NaN ? 0 : y;
        this.pixelY = -coordinateToPixel(y);
    }
    public double getPixelX() {
        return pixelX;
    }
    public double getPixelY() {
        return pixelY;
    }
    public void setPixelX(double x) {
        this.pixelX = x;
        this.x = pixelToCoordinate(x) == Float.NaN ? 0 : pixelToCoordinate(x);
    }
    public void setPixelY(double y) {
        this.pixelY = y;
        this.y = -pixelToCoordinate(y) == Float.NaN ? 0 : -pixelToCoordinate(y);
    }
   
    public final double distanceTo(Location location) {
        double dx = this.getX() - location.getX();
        double dy = this.getY() - location.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        distance = (double)Math.round(distance * 1000d) / 1000d;
        return distance;
    }
    public final Direction directionTo(Location location) {
        double dx = location.getX() - this.getX();
        double dy = location.getY() - this.getY();
        return new Direction(dx, dy);
    }
    public final Location add(float distance, Direction direction) {
        double dx = round(Math.cos(direction.getRadians()) * distance, 3);
        double dy = round(Math.sin(direction.getRadians()) * distance, 3);
        double xn = this.x + dx;
        double yn = this.y + dy;
        return new Location(xn,yn);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        return (((Location) obj).x == this.x) && (((Location) obj).y == this.y);
    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        return hash;
    }
}
