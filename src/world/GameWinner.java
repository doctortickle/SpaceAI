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

import common.VictoryCondition;
import common.Team;

/**
 *
 * @author Dylan Russell
 * 
 * This class returns the winning team and sets their victory condition.
 */
public class GameWinner {
    
    private Team winner;
    private VictoryCondition victoryCondition;

    public GameWinner(){
        this.winner = null;
        this.victoryCondition = null;
    }

    public void setWinner(Team t){
        winner = t;
    }

    public void setDominationFactor(VictoryCondition d){
        victoryCondition = d;
    }

    public Team getWinner(){
        return winner;
    }

    public VictoryCondition getDominationFactor(){
        return victoryCondition;
    }
    
    
}
