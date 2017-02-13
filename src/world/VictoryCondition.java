/*
 * Copyright (C) 2017 Dylan Russell
 * This game is a heavily modified version of Battlecode 2017. I give 
 * significant credit to the team at MIT for their work on Battlecode and for
 * inspiring this body of work. Please check out battlecode.org for more info.
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
package world;

/**
 *
 * @author Dylan Russell
 * 
 * This class enumerates the conditions by which a player can win.
 */
public enum VictoryCondition {
    
    /**
     * In the case that the max numbers of rounds are met and all other win 
     * conditions are tied, the player with the highest Actor ID will win.
     */
    ULT_DEFAULT_WIN,
    
    /**
     * In the case that the max numbers of rounds are met, and the below win
     * conditions are tied, the victor will be decided by the highest banked 
     * mineral count.
    */
    MINERAL_WIN,
    
    /**
     * In the case that the max numbers of rounds are met, the victor will 
     * first be decided by the greatest value of actors currently alive.
     */
    VALUE_WIN,
    
    /**
     * If Team "A" destroys the HOME_STATION of Team "B", Team "A" will be 
     * declared victorious.
     */
    DOMINATION_WIN;
    
}
