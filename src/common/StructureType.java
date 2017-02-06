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

/**
 *
 * @author Dylan Russell
 * 
 * Contains the attributes of various StructureType objects.
 */
public enum StructureType {
    /**
     * The starting StructureType for both teams. Only structure capable of producing a BUILDER. Only structure that can move.
     */
    HOME_STATION        (   null,
                            null,
                            new ShipType[] {ShipType.BUILDER, ShipType.HARVESTER, ShipType.REFUELER, ShipType.FIGHTER},
                            2000,   0,      10,     10,     10,     1,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    /**
     * The smallest and most basic StructureType. Capable of producing necessary early game units.
     */
    SMALL_DOCK          (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.HARVESTER, ShipType.REFUELER},
                            500,    500,     5,     0,      0,      0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    /**
     * An upgraded variant of the SMALL_DOCK. Capable of producing the necessary mid game units - a DESTROYER and a SIEGE ship.
     */
    LARGE_DOCK          (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.SIEGE, ShipType.DESTROYER, ShipType.HARVESTER, ShipType.REFUELER},
                            1200,   1000,    8,     0,      0,      0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    /**
     * The largest and most capable of StructureTypes. Capable of producing all units (except BUILDER), notably the deadly CAPITAL ship.
     */
    CAPITAL_DOCK        (   new ShipType[] {ShipType.BUILDER},
                            new EnvironmentType[] {EnvironmentType.LARGE_PLANET},
                            new ShipType[] {ShipType.FIGHTER, ShipType.SIEGE, ShipType.DESTROYER, ShipType.CAPITAL, ShipType.HARVESTER, ShipType.REFUELER},
                            3000,   3000,    15,     0,      0,     0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    /**
     * A facility that can be built upon a SMALL_METEOR or a LARGE_METEOR to harvest minerals. Can only be built by a HARVESTER.
     */
    MINING_FACILITY     (   new ShipType[] {ShipType.HARVESTER},
                            new EnvironmentType[] {EnvironmentType.SMALL_METEOR, EnvironmentType.LARGE_METEOR},
                            null,
                            500,    500,     5,      0,     10,     0,      10000   ),
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    /**
     * A station that can refuel ShipTypes within a given radius. Can only be built by a REFUELER.
     */
    FUEL_STATION        (   new ShipType[] {ShipType.REFUELER},
                            null,
                            null,
                            200,    100,     5,      10,    0,      0,      10000   );
    /*                      HP      cost    rad     rfRad   mRate   fRad    bCL     */
    
    /**
     * The ShipTypes that may spawn a given StructureType.
     */
    public final ShipType[] buildSources;
    /**
     * The EnvironmentTypes upon which a given StructureType can be built.
     */
    public final EnvironmentType[] buildLocations;
    /**
     * The ShipTypes a given StructureType can spawn.
     */
    public final ShipType[] buildShips;
    /**
     * The maximum (not current) health of a given StructureType.
     */
    public final int maxHealth;
    /**
     * The mineral cost to produce a given StructureType.
     */
    public final int mineralCost;
    /**
     * The structure radius of a given StructureType.
     */
    public final float structureRadius;
    /**
     * The radius at which a FUEL_STATION can refuel ships.
     */
    public final float refuelRadius;
    /**
     * The rate at which a MINING_FACILITY can mine minerals from a SMALL_METEOR or LARGE_METEOR. 
     */
    public final float miningRate;
    /**
     * The distance a given StructureType can move per turn. Only HOME_STATION can move. 
     */
    public final float flightRadius;
    /**
     * The bytecode usage limit per turn for a given StructureType.
     */
    public final float bytecodeLimit;
    
    /**
     * If the given StructureType can produce one or more ShipType, as indicated by buildShips[], will return true.
     * @return true if given StructureType can produce one or more ShipType.
     */
    public boolean canBuildShips() {
        return this == HOME_STATION || this == SMALL_DOCK || this == LARGE_DOCK || this == CAPITAL_DOCK;
    }
    /**
     * If the given StructureType can refuel other ShipTypes, will return true.
     * @return true if given StructureType can refuel other ShipTypes.
     */
    public boolean canRefuel() {
        return this == FUEL_STATION;
    }
    /**
     * If the given StructureType can mine a SMALL_METEOR or LARGE_METEOR, will return true.
     * @return true if the given StructureType can mine a SMALL_METEOR or LARGE_METEOR.
     */
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
    /**
     * Returns the ShipTypes that may spawn a given StructureType.
     * @return array of ShipTypes that may spawn a given StructureType. Null if no ShipTypes may produce given StructureType.
     */
    public ShipType[] getBuildSources() {
        return buildSources;
    }
    /**
     * Returns the EnvironmentTypes upon which a given StructureType must be built.
     * @return array of EnvironmentTypes upon which a given StructureType must be built. Null if given StructureType may be built in space.
     */
    public EnvironmentType[] getBuildLocations() {
        return buildLocations;
    }
    /**
     * Returns the ShipTypes that a given StructureType may produce.
     * @return array of ShipTypes a given StructureType may produce. Null if given StructureType can not produce ShipTypes.
     */
    public ShipType[] getBuildShips() {
        return buildShips;
    }
    /**
     * Returns the maximum (not current) health of a given StructureType.
     * @return the maximum (not current) health of a given StructureType.
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * Returns the mineral cost to spawn a given StructureType.
     * @return the mineral cost to spawn a given StructureType.
     */
    public int getMineralCost() {
        return mineralCost;
    }
    /**
     * Returns the structure radius of a given StructureType.
     * @return the structure radius of a given StructureType.
     */
    public float getStructureRadius() {
        return structureRadius;
    }
    /**
     * Returns the radius at which a FUEL_STATION may refuel adjacent ShipTypes.
     * @return the radius at which a FUEL_STATION may refuel adjacent ShipTypes.
     */
    public float getRefuelRadius() {
        return refuelRadius;
    }
    /**
     * Returns the rate at which a MINING_FACILITY can mine minerals from a SMALL_METEOR or LARGE_METEOR.
     * @return the rate at which a MINING_FACILITY can mine minerals from a SMALL_METEOR or LARGE_METEOR.
     */
    public float getMiningRate() {
        return miningRate;
    }
    /**
     * Returns the distance a given StructureType may move in one turn. Only HOME_STATION can move.
     * @return the distance a given StructureType may move in one turn.
     */
    public float getFlightRadius() {
        return flightRadius;
    }
    /**
     * Returns the bytecode usage limit for a given StructureType.
     * @return the bytecode usage limit for a given StructureType.
     */
    public float getBytecodeLimit() {
        return bytecodeLimit;
    }
    
}
