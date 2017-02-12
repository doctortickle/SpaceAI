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
       if(getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius()) {
            System.out.println("In A");
            unit.getSpriteFrame().setTranslateX(location.getPixelX());
            unit.getSpriteFrame().setTranslateY(location.getPixelY());
            unit.setX(location.getX());
            unit.setY(location.getY());
       }
       else {
            System.out.println("In B");
            double dx = getCurrentLocation().getX()-location.getX();
            double dy = getCurrentLocation().getY()-location.getY();
            double theta = Math.tan(dy/dx);
            double newDx = Math.cos(theta) * unit.getType().getFlightRadius();
            System.out.println("newDx = " + newDx);
            double newDy = Math.sin(theta) * unit.getType().getFlightRadius();
            System.out.println("newDy = " + newDy);
            double newX = getCurrentLocation().getX() + newDx;
            System.out.println("newX = " + newX);
            double newY = getCurrentLocation().getY() + newDy;
            System.out.println("newY = " + newY);
            double newPixelX = Location.coordinateToPixel(newX);
            System.out.println("newPixelX = " + newPixelX);
            double newPixelY = Location.coordinateToPixel(newY);
            System.out.println("newPixelY = " + newPixelY);
            unit.getSpriteFrame().setTranslateX(5);
            unit.getSpriteFrame().setTranslateY(5);
            unit.setX(newX);
            unit.setY(newY);
       }
    }
    
    public final Location getCurrentLocation() {
        double x = unit.getX();
        double y = unit.getY();
        System.out.println(x + ", " + y);
        
        return new Location(x,y);
    }    
    
}
