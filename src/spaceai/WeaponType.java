/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceai;

/**
 *
 * @author Dylan Russell
 */
public enum WeaponType {
    
    SMALL_LASER         (10,         1,      0,      0,      1,  1,      0.5    ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    LARGE_LASER         (7,          3,      0,      0,      3,  3,      1      ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    SMALL_BOMB          (6,          3,      6,      0,      10, 10,     5      ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    LARGE_BOMB          (4,          4,      12,     0,      20, 20,     10     ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    MINE                (0,          3,      10,     7,      15, 15,     20     ),
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    PLANET_BOMBARDMENT  (4,          4,      0,      0,      20, 50,     10     );
    /*              launchSpd   weaponRad   exRad   detRad  dam strDam  relTime   */
    
    public final float launchSpeed; //Used for all weapons
    public final float weaponRadius; //Used for all weapons.
    public final float explosionRadius; //Used for bombs and mines.
    public final float detectionRadius; //Used for mines.
    public final int damage; //Used for all weapons.
    public final int structureDamage; //Used for all weapons.
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
        
    public float getLaunchSpeed() {
        return launchSpeed;
    }

    public float getWeaponRadius() {
        return weaponRadius;
    }

    public float getExplosionRadius() {
        return explosionRadius;
    }

    public float getDetectionRadius() {
        return detectionRadius;
    }

    public int getDamage() {
        return damage;
    }

    public int getStructureDamage() {
        return structureDamage;
    }

    public double getReloadTime() {
        return reloadTime;
    }
        
}
