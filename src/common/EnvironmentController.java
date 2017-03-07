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
public strictfp class EnvironmentController {
    private Environment environment;
    private GameWorld gameWorld;
    private final double leftBoundary;
    private final double rightBoundary;
    private final double bottomBoundary;
    private final double topBoundary;
    
    public EnvironmentController(Environment environment, GameWorld gameWorld) {
        this.environment = environment;
        this.gameWorld = gameWorld;
        this.leftBoundary = GameConstants.MIN_X_COORDINATE;
        this.rightBoundary = GameConstants.MAX_X_COORDINATE;
        this.bottomBoundary = GameConstants.MIN_Y_COORDINATE;
        this.topBoundary = GameConstants.MAX_Y_COORDINATE;
    }
    
    // *********************************
    // ***** ENVIRONMENT UPDATES *******
    // *********************************

    void rotateImage() {
        double n = environment.getSpriteFrame().getRotate();
        n += environment.getType().getRotationV();
        environment.getSpriteFrame().setRotate(n);
    }       
        
    // ***********************************
    // ******MOVE - WeaponController******
    // ***********************************
    
 // Planets can be destroyed
 // Meteors deal damage to ship equivalent to the amount of health left in the meteor
 //        
        
    private boolean checkBoundaries(Location location) {
        if(location.getY() >= topBoundary - environment.getType().getBodyRadius()) { return false; }
        if(location.getY() <= bottomBoundary + environment.getType().getBodyRadius()) { return false; } 
        if(location.getX() >= rightBoundary - environment.getType().getBodyRadius()) { return false; }
        if(location.getX() <= leftBoundary + environment.getType().getBodyRadius()) { return false; }
        return true;
    }           
    public final void move(Direction direction) {
        Location movePoint = environment.getLocation().add(environment.getType().gettravelSpeed(), direction);
        if  (checkBoundaries(movePoint)) {
            updateSpriteAndLocation(movePoint);   
        }
        else if (!checkBoundaries(movePoint)) {
            environment.setDestroyed(true);
        }
    }
    private void updateSpriteAndLocation(Location location) {
        environment.updateLocation(location.getX(), location.getY());
        environment.getSpriteFrame().setTranslateX(location.getPixelX());
        environment.getSpriteFrame().setTranslateY(location.getPixelY());
    }
}