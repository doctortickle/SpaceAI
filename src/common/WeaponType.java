/*
 * Copyright (C) 2017 Russell Software
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
 * Details the attributes of the various types of weapons available in Space AI. 
 * @author Dylan Russell
 * @version 1.0
 * @see Weapon
 */
public strictfp enum WeaponType {
    /**
     * The most basic WeaponType, quick and light. Can be fired by all attack-capable units.
     */
    SMALL_LASER         (10,         2,      0,      0,      1,  1,      1,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/SMALL_LASER.png", 8, 8, true, false, true)),
    /**
     * An upgraded laser variant; more powerful, but twice as slow. Can be fired by a DESTROYER and a CAPITAL ship.
     */
    LARGE_LASER         (7,          4,      0,      0,      3,  3,      3,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A projectile that explodes on impact with an Actor and does damage to adjacent Actors. Can be fired by a DESTROYER and a CAPITAL ship.
     */
    SMALL_BOMB          (6,          4,      10,      0,      10, 10,     5,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/SMALL_BOMB.png", 16, 16, true, false, true)),
    /**
     * A heavy bomb variant; more powerful, a greater explosion radius, and twice as slow. Can be fired only by a CAPITAL ship.
     */
    LARGE_BOMB          (4,          7,      15,     0,      20, 20,     10,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/LARGE_BOMB.png", 28, 28, true, false, true)),
    /**
     * A deployable mine that will explode when an enemy unit enters its detection radius. Can be deployed by a DESTROYER and a CAPITAL ship.
     */
    MINE                (0,          4,      13,     7,      15, 15,     20,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/TestImage.png", 50, 50, true, false, true)),
    /**
     * A powerful blast that will deal increased damage to structures and EnvironmentType objects. Can be fired by SIEGE and CAPITAL ships.
     */
    PLANET_BOMBARDMENT  (4,          7,      0,      0,      20, 50,     10,
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
                    new Image("/TestImage.png", 50, 50, true, false, true));
    
    /**
     * The speed of a WeaponType.
     */
    private final int launchSpeed;
    /**
     * The radius of a given WeaponType. This radius determines collision with other Actors.
     */
    private final int weaponRadius;
    /**
     * The radius within which damage will be incurred by an actor when a mine or bomb is detonated.
     */
    private final int explosionRadius;
    /**
     * The radius at which a MINE will detect an enemy unit and subsequently detonate.
     */
    private final int detectionRadius; //Used for mines.
    /**
     * The damage inflicted upon an Actor that collides with a WeaponType.
     */
    private final int unitDamage;
    /**
     * The damage inflicted specifically to a structure.
     */
    private final int environmentDamage; //Used for all weapons.
    /**
     * The amount of time a unit must wait after firing or deploying a given WeaponType before firing or deploying again.
     */
    private final int reloadTime; //Used for all weapons.
    /**
     * Contains the sprite data for a given WeaponType.
     */
    private final Image spriteImage;
    
    private WeaponType(int launchSpeed, int weaponRadius, int explosionRadius, 
            int detectionRadius, int unitDamage, int environmentDamage, int reloadTime,
            Image spriteImage) {
        this.launchSpeed        = launchSpeed;
        this.weaponRadius       = weaponRadius;
        this.explosionRadius    = explosionRadius;
        this.detectionRadius    = detectionRadius;
        this.unitDamage         = unitDamage;
        this.environmentDamage  = environmentDamage;
        this.reloadTime         = reloadTime;
        this.spriteImage        = spriteImage;
    }
    
    /**
     * Returns the speed of a WeaponType when launched and during flight.
     * @return int the speed of a WeaponType when launched and during flight
     * @see #launchSpeed
     */    
    public int getLaunchSpeed() {
        return launchSpeed;
    }
    /**
     * Returns the radius of a given WeaponType. This radius determines collision with other Actors.
     * @return int radius of a given WeaponType
     * @see #weaponRadius
     */
    public int getWeaponRadius() {
        return weaponRadius;
    }
    /**
     * Returns the radius within which damage will be incurred.
     * @return int radius within which damage will be incurred
     * @see #explosionRadius
     */
    public int getExplosionRadius() {
        return explosionRadius;
    }
    /**
     * Returns the radius at which a MINE will detect an enemy unit and subsequently detonate.
     * @return int radius at which a MINE will detect an enemy unit and subsequently detonate.
     * @see #detectionRadius
     */
    public int getDetectionRadius() {
        return detectionRadius;
    }
    /**
     * Returns the radius that a QuadTree will use to determine if a collision needs to be checked.
     * @param weaponType the weaponType being checked.
     * @return int radius that should be used by QuadTree when detecting collisions
     */
    public int getCollisionRadius(WeaponType weaponType) {
        switch(weaponType) {
            case SMALL_LASER        : return weaponType.getWeaponRadius();
            case LARGE_LASER        : return weaponType.getWeaponRadius(); 
            case SMALL_BOMB         : return weaponType.getExplosionRadius(); 
            case LARGE_BOMB         : return weaponType.getExplosionRadius(); 
            case MINE               : return weaponType.getExplosionRadius(); 
            case PLANET_BOMBARDMENT : return weaponType.getWeaponRadius();
            default                 : return weaponType.getWeaponRadius();
        }
    }
    /**
     * Returns the damage inflicted upon an Actor that collides with a WeaponType.
     * @return int damage inflicted upon an Actor that collides with a WeaponType
     * @see #unitDamage
     */
    public int getUnitDamage() {
        return unitDamage;
    }
    /**
     * Returns the damage inflicted specifically to a structure by a WeaponType.
     * @return int damage inflicted specifically to a structure by a WeaponType
     * @see #environmentDamage
     */
    public int getEnvironmentDamage() {
        return environmentDamage;
    }
    /**
     * Returns the amount of time a unit must wait after firing or deploying a given WeaponType before firing or deploying again.
     * @return int number of turns a unit must wait after firing or deploying a given WeaponType before firing or deploying again.
     * @see #reloadTime
     */
    public int getReloadTime() {
        return reloadTime;
    }
    /**
     * Returns the sprite image for a given WeaponType.
     * @return Image sprite image for a given WeaponType.
     * @see #spriteImage
     */
    public Image getSpriteImage() {
        return spriteImage;
    }     
}
