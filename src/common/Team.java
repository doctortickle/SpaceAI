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
package common;

/**
 *
 * @author Dylan Russell
 * 
 * Contains the team constants - A, B, and NEUTRAL.
 */
public enum Team {
    /**
     * Team A.
     */
    A,
    /**
     * Team B.
     */
    B,
    /**
     * Neutral objects.
     */
    NEUTRAL;

    /**
     * Determines the team that is the opponent of this team.
     *
     * @return the opponent of this team.
     *
     * 
     */
    public Team opponent() {
        switch (this) {
            case A:
                return B;
            case B:
                return A;
            default:
                return NEUTRAL;
        }
    }

    /**
     * Returns whether a ship or structure of this team is a player-controlled entity
     * (team A or team B).
     *
     * @return true a ship or structure of this team is player-controlled; false otherwise.
     *
     * 
     */
    public boolean isPlayer() {
        return this == A || this == B;
    }
}
   
