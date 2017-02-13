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

import javafx.scene.image.Image;

/**
 *
 * @author Dylan Russell
 * 
 * Contains the attributes of various EnvironmentType objects.
 */
public strictfp enum EnvironmentType {
    
    /**
     * A small meteor, capable of being harvested by a HARVESTER or hosting a MINING_FACILITY.
     */
    SMALL_METEOR    (1000,  10,     1,      5000,   (Math.PI)/6,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A large meteor, capable of being harvested by a HARVESTER or hosting a MINING_FACILITY.
     */
    LARGE_METEOR    (3000,  20,     1,      12000,  (Math.PI)/9,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A small asteroid, capable of being harvested by a HARVESTER.
     */
    SMALL_ASTEROID  (700,   3,      0,      500,    (Math.PI)/6,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A large asteroid, capable of being harvested by a HARVESTER.
     */
    LARGE_ASTEROID  (1500,  6,      0,      1000,   (Math.PI)/9,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A small planet, capable of hosting a SMALL_DOCK, LARGE_DOCK, or CAPITAL_DOCK. Maximum capacity of 2.
     */
    SMALL_PLANET    (10000, 30,     2,      0,      (Math.PI)/9,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A large planet, capable of hosting a SMALL_DOCK, LARGE_DOCK, or CAPITAL_DOCK. Maximum capacity of 2.
     */
    LARGE_PLANET    (30000, 50,     4,      0,      (Math.PI)/12,
    /*              maxHP   Rad     Cap     MinMax      RotV    */
                    new Image("/TestImage.png", 50, 50, true, false, true));
    
    /**
     * The maximum (not current) health of a given EnvironmentType.
     */
    private final int maxHealth;
    /**
     * The body radius of a given EnvironmentType.
     */
    private final int bodyRadius;
    /**
     * The maximum (not current) number of StructureTypes a given EnvironmentType can host.
     */
    private final int structureCap;
    /**
     * The maximum (not current) number of minerals available to be harvested or mined on a given EnvironmentType.
     */
    private final int mineralMax;
    /**
     * The rotational velocity of a given EnvironmentType.
     */
    private final double rotationV;
    /**
     * Contains the sprite data for a given WeaponType.
     */
    private final Image spriteImage;
    
    /**
     * Returns true if a given EnvironmentType has minerals to be mined or harvested. 
     * @return true if a given EnvironmentType has minerals to be mined or harvested. 
     */
    public boolean canBeMined() {
        return mineralMax > 0;
    }
    /**
     * Returns true if a given EnvironmentType has the ability to host a StructureType.
     * @return true if a given EnvironmentType has the ability to host a StructureType.
     */
    public boolean canBuildOn() {
        return structureCap > 0;
    }

    private EnvironmentType(int maxHealth, int bodyRadius, int structureCap, 
            int mineralMax, double rotationV, Image spriteImage) {
        this.maxHealth      = maxHealth;
        this.bodyRadius     = bodyRadius;
        this.structureCap   = structureCap;
        this.mineralMax     = mineralMax;
        this.rotationV      = rotationV;
        this.spriteImage    = spriteImage;
    }
    
    /**
     * Returns the maximum (not current) health of a given EnvironmentType.
     * @return the maximum (not current) health of a given EnvironmentType.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * Returns the body radius of a given EnvironmentType.
     * @return the body radius of a given EnvironmentType.
     */
    public int getBodyRadius() {
        return bodyRadius;
    }
    /**
     * Returns the maximum (not current) number of StructureTypes that can be hosted on a given EnvironmentType.
     * @return the maximum (not current) number of StructureTypes that can be hosted on a given EnvironmentType.
     */
    public int getStructureCap() {
        return structureCap;
    }
    /**
     * Returns the maximum (not current) minerals available to be harvested or mined on a given EnvironmentType.
     * @return the maximum (not current) minerals available to be harvested or mined on a given EnvironmentType.
     */
    public int getMineralMax() {
        return mineralMax;
    }
    /**
     * Returns the rotational velocity of a given EnvironmentType.
     * @return the rotational velocity of a given EnvironmentType.
     */
    public double getRotationV() {
        return rotationV;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }
     
}
