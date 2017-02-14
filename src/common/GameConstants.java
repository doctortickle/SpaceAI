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
    
    double PIXEL_TO_COORDINATE = 1/2;
    double COORDINATE_TO_PIXEL = 2/1;
    int FRAMES_PER_ROUND_9 = 1;
    int FRAMES_PER_ROUND_8 = 2;
    int FRAMES_PER_ROUND_7 = 3;
    int FRAMES_PER_ROUND_6 = 4;
    int FRAMES_PER_ROUND_5 = 5;
    int FRAMES_PER_ROUND_4 = 10; 
    int FRAMES_PER_ROUND_3 = 15; 
    int FRAMES_PER_ROUND_2 = 20; 
    int FRAMES_PER_ROUND_1 = 30;
    
    // *********************************
    // ****** GAMEPLAY CONSTANTS *******
    // *********************************
    
    int STARTING_MINERAL_COUNT = 1000;
    Location TEAM_A_HOME_STATION = new Location(0,100);
    Location TEAM_B_HOME_STATION = new Location(0,-100);
    
}
