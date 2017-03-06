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
    // ******** ENVIRONMENT UPDATES *********
    // *********************************

    void rotateImage() {
        double n = environment.getSpriteFrame().getRotate();
        n += environment.getType().getRotationV();
        environment.getSpriteFrame().setRotate(n);
    }   
}