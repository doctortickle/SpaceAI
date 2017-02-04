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
public enum ShipType {
    
    FIGHTER     (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION}, 
                new WeaponType[] {WeaponType.SMALL_LASER}, 
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    SIEGE       (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK},
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.PLANET_BOMBARDMENT},
                10,      50,         300,    2,  10,     10,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    DESTROYER   (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK},
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.MINE},
                10,      100,        300,    3,  15,     20,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    CAPITAL     (new StructureType[] {StructureType.CAPITAL_DOCK},
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.LARGE_BOMB, WeaponType.MINE, WeaponType.PLANET_BOMBARDMENT},
                50,      500,       1000,   5,  20,     25,     1,      2000,         5,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    HARVESTER   (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION},
                null,
                5,      10,         50,     1,  10,     20,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    REFUELER    (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION},
                null,
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    BUILDER     (new StructureType[] {StructureType.HOME_STATION},
                null,
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000);
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    public final StructureType[] spawnSources;
    public final WeaponType[] arsenal;
    public final int buildCooldownTurns;
    public final int maxHealth;
    public final int mineralCost;
    public final float bodyRadius;
    public final float enemySensorRadius;
    public final float incomingDetectionRadius;
    public final float flightRadius;
    public final int fuelMax;
    public final int fuelBurnRate;
    public final int bytecodeLimit;
        
    public boolean canAttack() {
        return arsenal.length > 0;
    }
    public boolean canBomb() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    public boolean canMine() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    public boolean canPlanetBombardment() {
        return this == ShipType.SIEGE || this == ShipType.CAPITAL;
    }
    public boolean canBuildStructure() {
        return this == BUILDER;
    }
    public boolean canHarvest() {
        return this == HARVESTER;
    }
    public boolean canRefuel() {
        return this == REFUELER;
    }
          
    private ShipType(StructureType[] spawnSources, WeaponType[] arsenal, int buildCooldownTurns, int maxHealth, int mineralCost, float bodyRadius,
              float enemySensorRadius, float incomingDetectionRadius, float flightRadius, int fuelMax, int fuelBurnRate, int bytecodeLimit) {
        this.spawnSources             = spawnSources;
        this.arsenal                  = arsenal;
        this.buildCooldownTurns       = buildCooldownTurns;
        this.maxHealth                = maxHealth;
        this.mineralCost              = mineralCost;
        this.bodyRadius               = bodyRadius;
        this.enemySensorRadius        = enemySensorRadius;
        this.incomingDetectionRadius  = incomingDetectionRadius;
        this.flightRadius             = flightRadius;
        this.bytecodeLimit            = bytecodeLimit;
        this.fuelMax                  = fuelMax;
        this.fuelBurnRate             = fuelBurnRate;
    }

    public StructureType[] getSpawnSources() {
        return spawnSources;
    }

    public WeaponType[] getArsenal() {
        return arsenal;
    }

    public int getBuildCooldownTurns() {
        return buildCooldownTurns;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public float getBodyRadius() {
        return bodyRadius;
    }

    public float getEnemySensorRadius() {
        return enemySensorRadius;
    }

    public float getIncomingDetectionRadius() {
        return incomingDetectionRadius;
    }

    public float getFlightRadius() {
        return flightRadius;
    }

    public int getBytecodeLimit() {
        return bytecodeLimit;
    }

    public int getFuelMax() {
        return fuelMax;
    }

    public int getFuelBurnRate() {
        return fuelBurnRate;
    }
        
}
   