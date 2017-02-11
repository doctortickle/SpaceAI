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
 
 Stores basic information about a Structure object.
 */
public class Structure extends Actor {
    
    /**
     * The StructureType of this structure.
     */
    public final StructureType type;
    
    /**
     * The number of times this structure has built in the current turn.
     */
    public final int buildCount;
    
    /**
     * The number of minerals this structure has mined in the current turn.
     */
    public final float minedCount;
    
    /**
     * The number of times this structure has moved in the current turn.
     */
    public final int moveCount;
    
    /**
     * The number of bytecodes used in the current turn.
     */
    public final int bytecodeUsed;

    @Override
    public boolean isShip() {
        return false;
    }

    @Override
    public boolean isStructure() {
        return true;
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }

    public Structure(StructureType type, int ID, Team team, MapLocation location, Image imageSprite) {
        super(type.actorType, ID, type.maxHealth, type.structureRadius, team, location, imageSprite);
        this.type = type;
        this.buildCount = 0;
        this.minedCount = 0;
        this.moveCount = 0;
        this.bytecodeUsed = 0;
    }

    /**
     * Returns the StructureType of this structure.
     * @return the StructureType of this structure.
    */
    public StructureType getType() {
        return type;
    }

    /**
     * Returns the number of times this structure has built this turn.
     * @return the number of times this structure has built this turn.
     */
    public int getBuildCount() {
        return buildCount;
    }
    /**
     * Returns the number of minerals this structure has mined this turn.
     * @return the number of minerals this structure has mined this turn.
     */
    public float getMinedCount() {
        return minedCount;
    }
    /**
     * Returns the number of times this structure has moved this turn.
     * @return the number of times this structure has moved this turn.
     */
    public int getMoveCount() {
        return moveCount;
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
