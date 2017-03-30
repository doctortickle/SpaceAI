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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;

class GamePlayLoop extends AnimationTimer {
    
    int pulse;
    SpaceAI spaceAI;
    GameWorld gameWorld;
    CastingDirector castDirector;
    int gameSpeed;
    Replay replay;

    GamePlayLoop(SpaceAI spaceAI, GameWorld gameWorld, CastingDirector castDirector) throws IOException {
        this.spaceAI = spaceAI;
        this.gameWorld = gameWorld;
        this.castDirector = castDirector;
        this.gameSpeed = gameWorld.getGameSpeed();
        this.replay = new Replay(spaceAI.getReplayName());
    }
    
    @Override
    public void handle(long now) {
        if(!checkForWinner()) {
            if(pulse<gameSpeed) {
            pulse++;
            }
            else{
                pulse = 0;
            }
            if(pulse==0) {
                setGameSpeed(); //Checks the current game speed and changes var. 
                gameWorld.update(); //Round number, check winner, quad tree, health checks, weapon collision checks, Home Station/Mineral initialization
                spaceAI.update(); //Adds nodes, adds and removes pixels
                updateActors(); // Runs the space AI Controller
                updateReplay(); // Builds replay file
            }     
        }
        if(checkForWinner()) {
            System.out.println("Team " + gameWorld.getGameWinner().getWinner() + " wins due to " + gameWorld.getGameWinner().getDominationFactor());
            stop();
        }
    }
    private void setGameSpeed() {
        gameSpeed = gameWorld.getGameSpeed();
        System.out.println("Game speed is set at " + 5/gameSpeed + "x normal speed.");
    }
    private void updateActors() {
        castDirector.getCurrentActors().forEach((actor) -> {
            actor.update();
        });
    }
    private void updateReplay() {
        if(spaceAI.getRecordReplay()) {
            castDirector.getCurrentActors().forEach((actor) -> {
                try {
                    replay.addRound(gameWorld.getGameRound(), actor);
                } catch (IOException ex) {
                    Logger.getLogger(GamePlayLoop.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
    }
    private boolean checkForWinner() {
        return gameWorld.getGameWinningTeam() != null;
    }
    
    @Override
    public void start() {
        super.start();
    }    
    @Override
    public void stop() {
        super.stop();
    }
    
}
