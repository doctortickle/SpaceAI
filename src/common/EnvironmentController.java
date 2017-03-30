/*
 * Copyright (C) 2017 dr4ur
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General License for more details.
 *
 * You should have received a copy of the GNU General License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package common;

/**
 *
 * @author dr4ur
 */
strictfp class EnvironmentController {
    private final Environment environment;
    private final GameWorld gameWorld;
    private final double leftBoundary;
    private final double rightBoundary;
    private final double bottomBoundary;
    private final double topBoundary;
    
    EnvironmentController(Environment environment, GameWorld gameWorld) {
        this.environment = environment;
        this.gameWorld = gameWorld;
        this.leftBoundary = Map.getMinXCoordinate();
        this.rightBoundary = Map.getMaxXCoordinate();
        this.bottomBoundary = Map.getMinYCoordinate();
        this.topBoundary = Map.getMaxYCoordinate();
    }
    
    // *********************************
    // ***** ENVIRONMENT UPDATES *******
    // *********************************

    private void updateSpriteAndLocation(Location location) {
        environment.updateLocation(location.getX(), location.getY());
        environment.getSpriteFrame().setTranslateX(location.getPixelX());
        environment.getSpriteFrame().setTranslateY(location.getPixelY());
    }      
    private boolean checkBoundaries(Location location) {
        if(location.getY() >= topBoundary - environment.getType().getBodyRadius()) { return false; }
        if(location.getY() <= bottomBoundary + environment.getType().getBodyRadius()) { return false; } 
        if(location.getX() >= rightBoundary - environment.getType().getBodyRadius()) { return false; }
        if(location.getX() <= leftBoundary + environment.getType().getBodyRadius()) { return false; }
        return true;
    }           
    final void move() {
        if(environment.getType().getTravelSpeed() > 0) {
            Location movePoint = environment.getLocation().add(environment.getType().getTravelSpeed(), environment.getDirection());
            if  (checkBoundaries(movePoint)) {
                updateSpriteAndLocation(movePoint);   
            }
            else if (!checkBoundaries(movePoint)) {
                updateSpriteAndLocation(returnOpposite());
            }  
        }
    }
    private Location returnOpposite() {
        return new Location(-environment.getLocation().getX(), -environment.getLocation().getY());
    }
    void rotateImage() {
        double n = environment.getSpriteFrame().getRotate();
        n += environment.getType().getRotationV();
        environment.getSpriteFrame().setRotate(n);
    }       
  
    // *********************************
    // ***** COLLISION AND DAMAGE ******
    // *********************************
    
    boolean collide(Actor actor) {
        return environment.getLocation().distanceTo(actor.getLocation()) < environment.getType().getBodyRadius() + actor.getRadius();
    }
    void damageApplication(Actor actor) {
        actor.decreaseHealth(environment.getHealth());
        environment.setDestroyed(true);
        environment.decreaseHealth(environment.getHealth());
    }
    
}