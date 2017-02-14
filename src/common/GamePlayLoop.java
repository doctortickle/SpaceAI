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


import static common.GameConstants.FRAMES_PER_ROUND;
import javafx.animation.AnimationTimer;


public class GamePlayLoop extends AnimationTimer {
    
    int pulse;
    SpaceAI spaceAI;
    GameWorld gameWorld;
    CastingDirector castDirector;

    public GamePlayLoop(SpaceAI spaceAI, GameWorld gameWorld, CastingDirector castDirector) {
        this.spaceAI = spaceAI;
        this.gameWorld = gameWorld;
        this.castDirector = castDirector;
    }
    
    @Override
    public void handle(long now) {
        if(pulse<FRAMES_PER_ROUND) {
            pulse++;
            System.out.println(pulse);
        }
        else{
            pulse = 0;
        }
        if(pulse==0) {
            gameWorld.update();
            spaceAI.update();
            updateActors();
        }    
    }
    
    private void updateActors() {
        for(Unit unit : castDirector.getCurrentUnits() ) {
            unit.update();
        }
        for(Weapon weapon : castDirector.getCurrentWeaponss() ) {
            weapon.update();
        }
        for(Environment environment : castDirector.getCurrentEnvironment() ) {
            environment.update();
        }
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
