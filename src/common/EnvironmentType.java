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

import javafx.scene.image.Image;

/**
 *
 * @author Dylan Russell
 * 
 * Contains the attributes of various EnvironmentType objects.
 */
public strictfp enum EnvironmentType {
    
    /**
     * A small asteroid, capable of being harvested by a HARVESTER or hosting a MINING_FACILITY.
     */
    SMALL_ASTEROID    (1000,    0,      10,      5000,   (Math.PI)/6,
    /*              maxHP   trvSpd    Rad     MinMax      RotV    */
                    new Image("/TestImage.png", 10*GameConstants.COORDINATE_TO_PIXEL*2, 10*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)),
    /**
     * A large asteroid, capable of being harvested by a HARVESTER or hosting a MINING_FACILITY.
     */
    LARGE_ASTEROID    (3000,    0,     20,      12000,  (Math.PI)/9,
    /*              maxHP   trvSpd    Rad     MinMax      RotV    */
                    new Image("/TestImage.png", 20*GameConstants.COORDINATE_TO_PIXEL*2, 20*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)),
    /**
     * A small meteor, capable of being harvested by a HARVESTER.
     */
    SMALL_METEOR  (700,       3,      3,      500,    (Math.PI)/6,
    /*              maxHP   trvSpd    Rad    MinMax      RotV    */
                    new Image("/TestImage.png", 3*GameConstants.COORDINATE_TO_PIXEL*2, 3*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)),
    /**
     * A large meteor, capable of being harvested by a HARVESTER.
     */
    LARGE_METEOR  (1500,      2,       6,     1000,   (Math.PI)/9,
    /*              maxHP   trvSpd    Rad    MinMax      RotV    */
                    new Image("/TestImage.png", 6*GameConstants.COORDINATE_TO_PIXEL*2, 6*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)),
    /**
     * A small planet, capable of hosting a SMALL_DOCK, LARGE_DOCK, or CAPITAL_DOCK. Maximum capacity of 2.
     */
    SMALL_PLANET    (10000,    0,     30,      0,      (Math.PI)/9,
    /*              maxHP   trvSpd    Rad    MinMax      RotV    */
                    new Image("/TestImage.png", 30*GameConstants.COORDINATE_TO_PIXEL*2, 30*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)),
    /**
     * A large planet, capable of hosting a SMALL_DOCK, LARGE_DOCK, or CAPITAL_DOCK. Maximum capacity of 2.
     */
    LARGE_PLANET    (30000,    0,     50,      0,      (Math.PI)/12,
    /*              maxHP   trvSpd    Rad    MinMax      RotV    */
                    new Image("/LARGE_PLANET.png", 50*GameConstants.COORDINATE_TO_PIXEL*2, 50*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true));

    /**
     * The maximum (not current) health of a given EnvironmentType.
     */
    private final int maxHealth;
    /**
     * The travel speed of a given EnvironmentType.
     */
    private final int travelSpeed;
    /**
     * The body radius of a given EnvironmentType.
     */
    private final int bodyRadius;
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
    

    private EnvironmentType(int maxHealth, int travelSpeed, int bodyRadius,
            int mineralMax, double rotationV, Image spriteImage) {
        this.maxHealth      = maxHealth;
        this.travelSpeed    = travelSpeed;
        this.bodyRadius     = bodyRadius;
        this.mineralMax     = mineralMax;
        this.rotationV      = rotationV;
        this.spriteImage    = spriteImage;
    }
    
    /**
     * Returns the maximum (not current) health of a given EnvironmentType.
     * @return int maximum (not current) health of a given EnvironmentType.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * Returns the travel speed of a given EnvironmentType.
     * @return int travel speed of a given EnvironmentType.
     */
    public int getTravelSpeed() {
        return travelSpeed;
    }
    /**
     * Returns the body radius of a given EnvironmentType.
     * @return int body radius of a given EnvironmentType.
     */
    public int getBodyRadius() {
        return bodyRadius;
    }
    /**
     * Returns the maximum (not current) minerals available to be harvested or mined on a given EnvironmentType.
     * @return int maximum (not current) minerals available to be harvested or mined on a given EnvironmentType.
     */
    public int getMineralMax() {
        return mineralMax;
    }
    /**
     * Returns true if a given EnvironmentType has minerals to be mined or harvested. 
     * @return true if a given EnvironmentType has minerals to be mined or harvested. 
     */
    public boolean isHarvestable() {
        return mineralMax > 0;
    }

    /**
     * Returns the rotational velocity of a given EnvironmentType.
     * @return double rotational velocity of a given EnvironmentType.
     */
    public double getRotationV() {
        return rotationV;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }
     
}
