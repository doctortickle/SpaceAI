/*
 * Copyright (C) 2017 Dylan Russell
 * This game is a heavily modified version of Battlecode 2017. I give 
 * significant credit to the team at MIT for their work on Battlecode and for
 * inspiring this body of work. Please check out battlecode.org for more info.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License; or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful;
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not; see <http://www.gnu.org/licenses/>.
 */
package common;

/**
 *
 * @author Dylan Russell
 * 
 * GameConstants are stored here.
 */
public strictfp interface GameConstants {
    
    // *********************************
    // ****** ANIMATION CONSTANTS ******
    // *********************************
    
    final double SIZE_FACTOR = 2d; // Bigger size factor = bigger units and a smaller map. Image sprites go up to SIZE_FACTOR 3.
    final double PIXEL_TO_COORDINATE = 1d/SIZE_FACTOR;
    final double COORDINATE_TO_PIXEL = SIZE_FACTOR/1d;
    final int FRAMES_PER_ROUND_9 = 1;
    final int FRAMES_PER_ROUND_8 = 2;
    final int FRAMES_PER_ROUND_7 = 3;
    final int FRAMES_PER_ROUND_6 = 4;
    final int FRAMES_PER_ROUND_5 = 5;
    final int FRAMES_PER_ROUND_4 = 10; 
    final int FRAMES_PER_ROUND_3 = 15; 
    final int FRAMES_PER_ROUND_2 = 20; 
    final int FRAMES_PER_ROUND_1 = 30;
    final double    WINDOW_WIDTH = 1400, WINDOW_HEIGHT = 800, 
                    CENTER_WIDTH = 900, CENTER_HEIGHT = 700;
    final int WEAPON_CLEAR_COUNTDOWN = 3;

    // *********************************
    // ****** GAMEWORLD CONSTANTS ******
    // *********************************
    
    final double MAX_OBJECT_RADIUS = EnvironmentType.LARGE_PLANET.getBodyRadius();
    final double MIN_X_COORDINATE = -((CENTER_WIDTH/2d)*PIXEL_TO_COORDINATE);
    final double MAX_X_COORDINATE = ((CENTER_WIDTH/2d)*PIXEL_TO_COORDINATE);
    final double MIN_Y_COORDINATE = -((CENTER_HEIGHT/2d)*PIXEL_TO_COORDINATE);
    final double MAX_Y_COORDINATE = ((CENTER_HEIGHT/2d)*PIXEL_TO_COORDINATE);
    final double TOP_LEFT_X_PIXEL = -(CENTER_WIDTH/2d);
    final double TOP_LEFT_Y_PIXEL = -(CENTER_HEIGHT/2d);
    
    // *********************************
    // ****** GAMEPLAY CONSTANTS *******
    // *********************************
    
    final int STARTING_MINERAL_COUNT = 10000;
    final Location TEAM_A_HOME_STATION = new Location(0,100);
    final Location TEAM_B_HOME_STATION = new Location(0,-100);
    final int MIN_ENV_BUILD_DISTANCE = 1;
    final int MAX_ENV_BUILD_DISTANCE = 10;
    final int GAME_LIMIT = 5000; 
}
