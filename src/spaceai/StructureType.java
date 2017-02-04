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
public enum StructureType {
    
    HOME_STATION        (   null,
                            null,
                            new ShipType[] {ShipType.BUILDER, ShipType.HARVESTER, ShipType.REFUELER, ShipType.FIGHTER},
                            2000,   0,      10,     10,     10,     1,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    SMALL_DOCK          (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.HARVESTER, ShipType.REFUELER},
                            500,    500,     5,     0,      0,      0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    LARGE_DOCK          (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.SIEGE, ShipType.DESTROYER, ShipType.HARVESTER, ShipType.REFUELER},
                            1200,   1000,    8,     0,      0,      0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    CAPITAL_DOCK        (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.SIEGE, ShipType.DESTROYER, ShipType.CAPITAL, ShipType.HARVESTER, ShipType.REFUELER},
                            3000,   3000,    15,     0,      0,     0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    MINING_FACILITY     (   new ShipType[] {ShipType.HARVESTER},
                            new EnvironmentType[] {EnvironmentType.SMALL_METEOR, EnvironmentType.LARGE_METEOR},
                            null,
                            500,    500,     5,      0,     10,     0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    FUEL_STATION        (   new ShipType[] {ShipType.REFUELER},
                            null,
                            null,
                            200,    100,     5,      10,    0,      0,      10000   );
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    public final ShipType[] buildSources;
    public final EnvironmentType[] buildLocations;
    public final ShipType[] buildShips;
    public final int maxHealth;
    public final int mineralCost;
    public final float structureRadius;
    public final float refuelRadius;
    public final float miningRate;
    public final float flightRadius;
    public final float bytecodeLimit;
    
    public boolean canBuildShips() {
        return this == HOME_STATION || this == SMALL_DOCK || this == LARGE_DOCK || this == CAPITAL_DOCK;
    }
    
    public boolean canRefuel() {
        return this == FUEL_STATION;
    }
    
    public boolean canMine() {
        return this == MINING_FACILITY;
    }

    private StructureType(ShipType[] buildSources, EnvironmentType[] buildLocations, ShipType[] buildShips, int maxHealth, int mineralCost, float structureRadius, float refuelRadius, float miningRate, float flightRadius, float bytecodeLimit) {
        this.buildSources       = buildSources;
        this.buildLocations     = buildLocations;
        this.buildShips         = buildShips;
        this.maxHealth          = maxHealth;
        this.mineralCost        = mineralCost;
        this.structureRadius    = structureRadius;
        this.refuelRadius       = refuelRadius;
        this.miningRate         = miningRate;
        this.flightRadius       = flightRadius;
        this.bytecodeLimit      = bytecodeLimit;
    }

    public ShipType[] getBuildSources() {
        return buildSources;
    }

    public EnvironmentType[] getBuildLocations() {
        return buildLocations;
    }

    public ShipType[] getBuildShips() {
        return buildShips;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public float getStructureRadius() {
        return structureRadius;
    }

    public float getRefuelRadius() {
        return refuelRadius;
    }

    public float getMiningRate() {
        return miningRate;
    }

    public float getFlightRadius() {
        return flightRadius;
    }

    public float getBytecodeLimit() {
        return bytecodeLimit;
    }
    
    
    
}
