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
public strictfp class AIController {
    
    Unit unit;
    
    public AIController(Unit unit) {
        this.unit = unit;
    }
    
    public final void move(Location location) {
       System.out.println("\nMoving to " + location.getX() +", "+ location.getY());
       System.out.println("distance to point - " + getCurrentLocation().distanceTo(location));
       if(getCurrentLocation().distanceTo(location) <= unit.getType().getFlightRadius()) {
            System.out.println("In A");
            unit.getSpriteFrame().setTranslateX(location.getPixelX());
            unit.getSpriteFrame().setTranslateY(location.getPixelY());
            unit.setX(location.getX());
            unit.setY(location.getY());
       }
       else {
           System.out.println("In B");
            Direction moveDirection = getCurrentLocation().directionTo(location);
            move(moveDirection);
       }
    }
    public final void move(Direction direction) {
        Location movePoint = getCurrentLocation().add(unit.getType().getFlightRadius(), direction);
        move(movePoint);   
    }
    public final UnitType getType() {
        return unit.getType();
    }
    public final Location getCurrentLocation() {
        double x = unit.getX();
        double y = unit.getY();
        
        return new Location(x,y);
    }
}
