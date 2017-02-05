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
 */
public enum ShipType {
    /**
     * A basic fighting unit. Quick and cheap. Can be constructed by all docks.
     */
    FIGHTER     (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER}, // arsenal
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * A unit specialized in damaging StructureType and EnvironmentType objects. Requires LARGE_DOCK or CAPITAL_DOCK.
     */
    SIEGE       (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.PLANET_BOMBARDMENT}, // arsenal
                10,      50,         300,    2,  10,     10,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * A unit specialized in deploying a large arsenal against other ShipTypes. Requires LARGE_DOCK or CAPITAL_DOCK.
     */
    DESTROYER   (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.MINE}, // arsenal
                10,      100,        300,    3,  15,     20,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * The biggest, toughest ShipType in the game. End game ShipType only producable from CAPITAL_DOCK that can wreak havoc.
     */
    CAPITAL     (new StructureType[] {StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.LARGE_BOMB, WeaponType.MINE, WeaponType.PLANET_BOMBARDMENT}, // arsenal
                50,      500,       1000,   5,  20,     25,     1,      2000,         5,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * Unit specialized in harvesting minerals from SMALL_ASTEROID, LARGE_ASTEROID, SMALL_METEOR, and LARGE_METEOR. May mine directly or produce a MINING_FACILITY. Does not attack.
     */
    HARVESTER   (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION}, // spawnSources
                null, // arsenal
                5,      10,         50,     1,  10,     20,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * Unit specialized in refueling all ShipTypes. May refuel directly or deploy FUEL_STATION. Does not attack.
     */
    REFUELER    (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION}, // spawnSources
                null, // arsenal
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * Standard builder unit. May construct all types of docks. Does not attack.
     */
    BUILDER     (new StructureType[] {StructureType.HOME_STATION}, // spawnSources
                null, // arsenal
                5,      10,         50,     1,  10,     15,     5,      1000,         1,        10000);
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    
    /**
     * The StructureTypes that may spawn a given ShipType.
     */
    public final StructureType[] spawnSources;
    /**
     * The WeaponTypes available to a given ShipType.
     */
    public final WeaponType[] arsenal;
    /**
     * The turns after producing a given ShipType that a spawn source must wait to produce again.
     */
    public final int buildCooldownTurns;
    /**
     * The maximum (not current) health of a given ShipType.
     */
    public final int maxHealth;
    /**
     * The cost in minerals to produce a given ShipType.
     */
    public final int mineralCost;
    /**
     * The body radius of a given ShipType.
     */
    public final float bodyRadius;
    /**
     * The radius at which a given ShipType can sense enemy ShipTypes and StructureTypes.
     */
    public final float enemySensorRadius;
    /**
     * The radius at which a given ShipType can detect incoming WeaponTypes.
     */
    public final float incomingDetectionRadius;
    /**
     * The distance a given ShipType can move in one turn.
     */
    public final float flightRadius;
    /**
     * The maximum (not current) fuel capacity of a given ShipType.
     */
    public final int fuelMax;
    /**
     * The rate at which a given ShipType will burn fuel while moving per turn.
     */
    public final int fuelBurnRate;
    /**
     * The bytecode usage limit per turn for a given ShipType.
     */
    public final int bytecodeLimit;
    
    /**
     * If the ShipType has WeaponTypes in its arsenal, it may attack.
     * @return true if the ShipType's arsenal contains WeaponTypes to attack.
     */
    public boolean canAttack() {
        return arsenal.length > 0;
    }
    /**
     * True if the ship has the capability to launch a SMALL_BOMB or LARGE_BOMB.
     * @return true if the ShipType's arsenal contains a SMALL_BOMB or LARGE_BOMB.
     */
    public boolean canBomb() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    /**
     * True if the ShipType has the capability to deploy a MINE.
     * @return true if the ShipType's arsenal contains a MINE.
     */
    public boolean canMine() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    /**
     * True if the ShipType has PLANET_BOMBARDMENT in it's arsenal.
     * @return true if the ShipType has PLANET_BOMBARDMENT in it's arsenal.
     */
    public boolean canPlanetBombardment() {
        return this == ShipType.SIEGE || this == ShipType.CAPITAL;
    }
    /**
     * True if the ShipType can construct a StructureType (including a MINING_FACILITY or FUEL_STATION).
     * @return true if the ShipType can construct a StructureType (including a MINING_FACILITY or FUEL_STATION).
     */
    public boolean canBuildStructure() {
        return this == BUILDER || this == HARVESTER || this == REFUELER;
    }
    /**
     * True if the ShipType can harvest directly from a SMALL_ASTEROID, LARGE_ASTEROID, SMALL_METEOR, or LARGE_METEOR.
     * @return true if the ShipType can harvest directly from a SMALL_ASTEROID, LARGE_ASTEROID, SMALL_METEOR, or LARGE_METEOR.
     */
    public boolean canHarvest() {
        return this == HARVESTER;
    }
    /**
     * True if the ShipType can refuel another ShipType directly.
     * @return true if the ShipType can refuel another ShipType directly.
     */
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
    /**
     * For all ShipTypes, this is the StructureType(s) capable of spawning that ShipType.
     * @return array of StructureTypes capable of spawning a given ShipType.
     */
    public StructureType[] getSpawnSources() {
        return spawnSources;
    }
    /**
     * For all ShipTypes, this is the WeaponTypes(s) available to be used.
     * @return array of WeaponTypes available for a given ShipType to use. Returns null if given ShipType can not attack.
     */
    public WeaponType[] getArsenal() {
        return arsenal;
    }
    /**
     * For all ShipTypes, this is the required build cool-down after the ShipType is produced by a StructureType.
     * @return integer value for build cool-down turns required after production of a given ShipType.
     */
    public int getBuildCooldownTurns() {
        return buildCooldownTurns;
    }
    /**
     * For all ShipTypes, this is the ShipType's maximum health (not current).
     * @return integer value representing ShipType's maximum health (not current).
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * For all ShipTypes, this is the ShipType's mineral cost to produce.
     * @return integer value representing the cost to produce this given ShipType.
     */
    public int getMineralCost() {
        return mineralCost;
    }
    /**
     * Returns the body radius of the given ShipType.
     * @return the body radius of the given ShipType.
     */
    public float getBodyRadius() {
        return bodyRadius;
    }
    /**
     * Returns the radius at which this ShipType can sense an enemy ShipType or StructureType.
     * @return the radius at which this ShipType can sense an enemy ShipType or StructureType.
     */
    public float getEnemySensorRadius() {
        return enemySensorRadius;
    }
    /**
     * Returns the radius at which this ShipType can sense an incoming WeaponType.
     * @return the radius at which this ShipType can sense an incoming WeaponType.
     */
    public float getIncomingDetectionRadius() {
        return incomingDetectionRadius;
    }
    /**
     * Returns the maximum distance a given ShipType can move in one turn.
     * @return the maximum distance a given ShipType can move in one turn.
     */
    public float getFlightRadius() {
        return flightRadius;
    }
    /**
     * Returns the bytecode limit for a given ShipType in one turn.
     * @return the bytecode limit for a given ShipType in one turn.
     */
    public int getBytecodeLimit() {
        return bytecodeLimit;
    }
    /**
     * Returns the maximum fuel capacity of a given ShipType.
     * @return the maximum fuel capacity of a given ShipType.
     */
    public int getFuelMax() {
        return fuelMax;
    }
    /**
     * Returns the rate at which a given ShipType burns fuel while moving per turn.
     * @return the rate at which a given ShipType burns fuel while moving per turn.
     */
    public int getFuelBurnRate() {
        return fuelBurnRate;
    }
      
}
   