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
package spaceai;

/**
 *
 * @author Dylan Russell
 * 
 * Contains the attributes of various WeaponType objects.
 */
public enum WeaponType {
    /**
     * The most basic WeaponType, quick and light. Can be fired by all attack-capable units.
     */
    SMALL_LASER         (10,         1,      0,      0,      1,  1,      0.5    ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    /**
     * An upgraded laser variant; more powerful, but twice as slow. Can be fired by a DESTROYER and a CAPITAL ship.
     */
    LARGE_LASER         (7,          3,      0,      0,      3,  3,      1      ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    /**
     * A projectile that explodes on impact with an Actor and does damage to adjacent Actors. Can be fired by a DESTROYER and a CAPITAL ship.
     */
    SMALL_BOMB          (6,          3,      6,      0,      10, 10,     5      ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    /**
     * A heavy bomb variant; more powerful, a greater explosion radius, and twice as slow. Can be fired only by a CAPITAL ship.
     */
    LARGE_BOMB          (4,          4,      12,     0,      20, 20,     10     ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    /**
     * A deployable mine that will explode when an enemy ShipType enters its detection radius. Can be deployed by a DESTROYER and a CAPITAL ship.
     */
    MINE                (0,          3,      10,     7,      15, 15,     20     ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    /**
     * A powerful blast that will deal increased damage to StructureTypes and EnvironmentalType objects. Can be fired by SIEGE and CAPITAL ships.
     */
    PLANET_BOMBARDMENT  (4,          4,      0,      0,      20, 50,     10     );
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    
    /**
     * The speed of a WeaponType when launched and during flight.
     */
    public final float launchSpeed;
    /**
     * The radius of a given WeaponType. This radius determines collision with other Actors.
     */
    public final float weaponRadius; //Used for all weapons.
    /**
     * The radius within which damage will be incurred by an Actor when a MINE is detonated.
     */
    public final float explosionRadius; //Used for bombs and mines.
    /**
     * The radius at which a MINE will detect an enemy ShipType and subsequently detonate.
     */
    public final float detectionRadius; //Used for mines.
    /**
     * The damage inflicted upon an Actor that collides with a WeaponType.
     */
    public final int damage; //Used for all weapons.
    /**
     * The damage inflicted specifically to a StructureType if it collides with a WeaponType. Only varies for PLANET_BOMBARDMENT.
     */
    public final int structureDamage; //Used for all weapons.
    /**
     * The amount of time a ShipType must wait after firing or deploying a given WeaponType before firing or deploying again.
     */
    public final double reloadTime; //Used for all weapons.

    private WeaponType(float launchSpeed, float weaponRadius, float explosionRadius, float detectionRadius, int damage, int structureDamage, double reloadTime) {
        this.launchSpeed        = launchSpeed;
        this.weaponRadius       = weaponRadius;
        this.explosionRadius    = explosionRadius;
        this.detectionRadius    = detectionRadius;
        this.damage             = damage;
        this.structureDamage    = structureDamage;
        this.reloadTime         = reloadTime;
    }
    
    /**
     * Returns the speed of a WeaponType when launched and during flight.
     * @return the speed of a WeaponType when launched and during flight.
     */    
    public float getLaunchSpeed() {
        return launchSpeed;
    }
    /**
     * Returns the radius of a given WeaponType. This radius determines collision with other Actors.
     * @return The radius of a given WeaponType.
     */
    public float getWeaponRadius() {
        return weaponRadius;
    }
    /**
     * Returns the radius within which damage will be incurred by Actors when a MINE is detonated.
     * @return the radius within which damage will be incurred by Actors when a MINE is detonated.
     */
    public float getExplosionRadius() {
        return explosionRadius;
    }
    /**
     * Returns the radius at which a MINE will detect an enemy ShipType and subsequently detonate.
     * @return the radius at which a MINE will detect an enemy ShipType and subsequently detonate.
     */
    public float getDetectionRadius() {
        return detectionRadius;
    }
    /**
     * Returns the damage inflicted upon an Actor that collides with a WeaponType.
     * @return the damage inflicted upon an Actor that collides with a WeaponType.
     */
    public int getDamage() {
        return damage;
    }
    /**
     * Returns the damage inflicted specifically to a StructureType if it collides with a WeaponType. Only varies for PLANET_BOMBARDMENT.
     * @return the damage inflicted specifically to a StructureType if it collides with a WeaponType.
     */
    public int getStructureDamage() {
        return structureDamage;
    }
    /**
     * Returns the amount of time a ShipType must wait after firing or deploying a given WeaponType before firing or deploying again.
     * @return the amount of time a ShipType must wait after firing or deploying a given WeaponType before firing or deploying again.
     */
    public double getReloadTime() {
        return reloadTime;
    }
        
}
