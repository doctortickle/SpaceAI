/*
 * Copyright (C) 2017 Dylan Russell
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

import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 * 
 * Stores basic information about a ShipType object.
 */
public class Ship extends Actor {
    
    /**
     * The ShipType of this ship.
     */
    public final ShipType type;
    
    /**
     * The number of times this ship has attacked in the current turn.
     */
    public final int attackCount;
    
    /**
     * The number of times this ship has moved in the current turn.
     */
    public final int moveCount;
    
    /**
     * The current fuel in this ship's tank.
     */
    public final int fuel;
    
    /**
     * The number of bytecodes used in the current turn.
     */
    public final int bytecodeUsed;

    @Override
    public boolean isShip() {
        return true;
    }

    @Override
    public boolean isStructure() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }

    public Ship(ShipType type,int ID, Team team, MapLocation location, ImageView spriteFrame, List<Image> imageStates) {
        super(ID, type.maxHealth, type.bodyRadius, team, location, spriteFrame, imageStates);
        this.type = type;
        this.attackCount = 0;
        this.moveCount = 0;
        this.fuel = type.fuelMax;
        this.bytecodeUsed = 0;
    }
    
    /**
     * Returns the ShipType of this ship.
     * @return the ShipType of this ship.
    */
    public ShipType getType() {
        return type;
    }
    
    /**
     * Returns the current attack count of this ship this round.
     * @return the attack count of this ship this round.
     */
    public int getAttackCount() {
        return attackCount;
    }
    /**
     * Returns the current move count of this ship this round.
     * @return the current move count of this ship this round. 
     */
    public int getMoveCount() {
        return moveCount;
    }

    public int getFuel() {
        return fuel;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean collide(Actor object) {
        return false;
    }
    
    
    
}
