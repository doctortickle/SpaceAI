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
 * This class is used to control the units the player will command.
 */
public class AIController {
    
    Unit unit;
    
    public AIController(Unit unit) {
        this.unit = unit;
    }
    
    public final void move(Location location) {
       System.out.println("\nMoving to " + location.getX() +", "+ location.getY());
       if(getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius()) {
            System.out.println("In A");
            unit.getSpriteFrame().setTranslateX(location.getPixelX());
            unit.getSpriteFrame().setTranslateY(location.getPixelY());
            unit.setX(location.getX());
            unit.setY(location.getY());
       }
       else {
            System.out.println("In B");
            moveDirection(location);
       }
    }
    
    public final void moveDirection(Location location) {
        
        double dx = calcDx(getCurrentLocation(),location);
        double dy = calcDy(getCurrentLocation(),location);
        if (dx == 0 && dy == 0) {
            dy = 1;
        }
        double theta = Math.atan2(dy, dx);
        double newDx = Math.cos(theta) * unit.getType().getFlightRadius();
        double newDy = Math.sin(theta) * unit.getType().getFlightRadius();
        double x = getCurrentLocation().getX();
        double y = getCurrentLocation().getY();
        double newX = x + newDx;
        double newY = y + newDy;
        move(new Location(newX,newY));
    }
    
    public final Location getCurrentLocation() {
        double x = unit.getX();
        double y = unit.getY();
        
        return new Location(x,y);
    }    
    
    private double calcDx(Location location1, Location location2) {
        double dx = location1.getX()-location2.getX();
        return dx;
    }
    
    private double calcDy(Location location1, Location location2) {
        double dy = location1.getY()-location2.getY();
        return dy;
    }
    
    private double calcTheta(Location location) {
        double theta = Math.tan(calcDy(location, getCurrentLocation())/calcDx(location, getCurrentLocation()));
        System.out.println("Theta = " + theta);
        return theta;
    }
    
    private double calcNewDx(Location location) {
        double newDx = Math.cos(calcTheta(location)) * unit.getType().getFlightRadius();
        return newDx;
    }
    
    private double calcNewDy(Location location) {
        double newDy = Math.sin(calcTheta(location)) * unit.getType().getFlightRadius();
        return newDy;
    }
    
    private double getNewX(double newDx) {
        double newX = getCurrentLocation().getX() + newDx;
        return newX;
    }
    
    private double getNewY(double newDy) {
        double newY = getCurrentLocation().getY() + newDy;
        return newY;
    }
}
