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
     * A unit specialized in damaging structures and environment objects. Requires large/capital docks.
     */
    SIEGE       (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.PLANET_BOMBARDMENT}, // arsenal
                10,      50,         300,    2,  10,     10,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * A unit specialized in deploying a large arsenal against other ships. Requires large/capital docks.
     */
    DESTROYER   (new StructureType[] {StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.MINE}, // arsenal
                10,      100,        300,    3,  15,     20,     3,      1000,         3,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * The biggest, toughest ship in the game. End game unit only producable from capital docks that can wreak havoc.
     */
    CAPITAL     (new StructureType[] {StructureType.CAPITAL_DOCK}, // spawnSources
                new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.LARGE_BOMB, WeaponType.MINE, WeaponType.PLANET_BOMBARDMENT}, // arsenal
                50,      500,       1000,   5,  20,     25,     1,      2000,         5,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * Unit specialized in harvesting minerals from asteroids and meteors. May mine directly or produce a mining facility. Does not attack.
     */
    HARVESTER   (new StructureType[] {StructureType.SMALL_DOCK, StructureType.LARGE_DOCK, StructureType.CAPITAL_DOCK, StructureType.HOME_STATION}, // spawnSources
                null, // arsenal
                5,      10,         50,     1,  10,     20,     5,      1000,         1,        10000),
    /*          bCD,    maxHealth   cost    rad eRad    iRad    fRad    fuelMax     fuelBR    bCL    */
    /**
     * Unit specialized in refueling all ships. May refuel directly or deploy fueling stations. Does not attack.
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
    
    /**
     * If the ship has weapons in its arsenal, it may attack.
     * @return true if the ShipType's arsenal contains weaponry to attack.
     */
    public boolean canAttack() {
        return arsenal.length > 0;
    }
    /**
     * True if the ship has the capability to launch bombs.
     * @return true if the ShipType's arsenal contains a small or large bomb.
     */
    public boolean canBomb() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    /**
     * True if the ship has the capability to launch mines.
     * @return true if the ShipType's arsenal contains a mine.
     */
    public boolean canMine() {
        return this == ShipType.DESTROYER || this == ShipType.CAPITAL;
    }
    /**
     * True if the ship has the capability to bombard a planet.
     * @return if the ShipType's arsenal contains planet bombardment.
     */
    public boolean canPlanetBombardment() {
        return this == ShipType.SIEGE || this == ShipType.CAPITAL;
    }
    /**
     * True if the ship can construct a structure (including a mining facility or fueling station).
     * @return true if the ShipType has the ability to construct any kind of structure.
     */
    public boolean canBuildStructure() {
        return this == BUILDER || this == HARVESTER || this == REFUELER;
    }
    /**
     * True if the ship can harvest directly from an asteroid or meteor.
     * @return true if the ship can harvest directly from an asteroid or meteor.
     */
    public boolean canHarvest() {
        return this == HARVESTER;
    }
    /**
     * True if the ship can refuel another ship directly.
     * @return true if the ship can refuel another ship directly.
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
     * For all ships, this is the structure(s) capable of spawning that ShipType.
     * @return array of structures capable of spawning a given ShipType.
     */
    public StructureType[] getSpawnSources() {
        return spawnSources;
    }
    /**
     * For all ships, this is the weapon(s) available to be used.
     * @return array of weapons available for a given ShipType to use.
     */
    public WeaponType[] getArsenal() {
        return arsenal;
    }
    /**
     * For all ships, this is the required build cool-down after the ShipType is produced by a structure.
     * @return integer value for build cool-down turns required after production of a given ShipType.
     */
    public int getBuildCooldownTurns() {
        return buildCooldownTurns;
    }
    /**
     * For all ships, this is the ShipType's maximum health (not current).
     * @return integer value representing ShipType's maximum health (not current).
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * For all ships, this is the ShipType's mineral cost to produce.
     * @return integer value representing the cost to produce this given ShipType.
     */
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
   