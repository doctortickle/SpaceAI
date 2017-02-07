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
import java.util.Objects;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 * 
 * Stores basic information about an EnvironmentType object.
 */
public class Environment extends Actor{
    
    /**
     * The EnvironmentType of this environment object.
     */
    public final EnvironmentType type;
   
    /**
     * The number of minerals this environment object has currently remaining.
     */
    public final int mineralCount;
    
    /**
     * The current number of StructureType structures on this environment object.
     */
    public final int structureCount;
    
    @Override
    public boolean isShip() {
        return false;
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
        return true;
    }

    public Environment(EnvironmentType type, int ID, Team team, MapLocation location, ImageView spriteFrame, List<Image> imageStates) {
        super(ID, type.maxHealth, type.bodyRadius, team, location, spriteFrame, imageStates);
        this.type = type;
        this.mineralCount = type.mineralMax;
        this.structureCount = 0;
    }
       
    /**
     * Returns the EnvironmentType of this structure.
     * @return the EnvironmentType of this structure.
    */
    public EnvironmentType getType() {
        return type;
    }

    /**
     * Returns the current mineral count of this environment object.
     * @return the current mineral count of this environment object.
     */
    public int getMineralCount() {
        return mineralCount;
    }
    /**
     * Returns the current number of StructureType structures built upon this environment object.
     * @return the current number of StructureType structures built upon this environment object.
     */
    public int getStructureCount() {
        return structureCount;
    }

    @Override
    public void update() {
        // update method goes here.
    }

    @Override
    public boolean collide(Actor object) {
        return false;
    }
    
}
