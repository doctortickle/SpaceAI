/*
 * Copyright (C) 2017 dr4ur
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
 * @author dr4ur
 */
public enum UnitType {
    
    FIGHTER (   new WeaponType[] {WeaponType.SMALL_LASER},  // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                1,      // bodyRadius
                10,     // enemySensoryRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    SIEGE(   new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.PLANET_BOMBARDMENT},  // arsenal
                10,     // spawnCooldown
                50,     // maxHealth
                300,    // mineralCost
                2,      // bodyRadius
                10,     // enemySensoryRadius
                10,     // incomingDetectionRadius
                3,      // flightRadius
                1000,   // fuelMax
                3,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    DESTROYER(  new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.MINE},  // arsenal
                10,     // spawnCooldown
                100,    // maxHealth
                300,    // mineralCost
                3,      // bodyRadius
                15,     // enemySensoryRadius
                20,     // incomingDetectionRadius
                3,      // flightRadius
                1000,   // fuelMax
                3,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    CAPITAL(    new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.LARGE_BOMB, WeaponType.MINE, WeaponType.PLANET_BOMBARDMENT},  // arsenal
                50,     // spawnCooldown
                500,    // maxHealth
                1000,   // mineralCost
                5,      // bodyRadius
                20,     // enemySensoryRadius
                25,     // incomingDetectionRadius
                1,      // flightRadius
                2000,   // fuelMax
                5,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    HARVESTER(  null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                1,      // bodyRadius
                10,     // enemySensoryRadius
                20,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    REFUELER(   null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                1,      // bodyRadius
                10,     // enemySensoryRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    BUILDER(    null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                1,      // bodyRadius
                10,     // enemySensoryRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    
    HOME_STATION(   null,   // spawnLocations
                    0,      // spawnCooldown
                    2000,   // maxHealth
                    0,      // mineralCost
                    10,     // bodyRadius
                    1,      // flightRadius
                    10,     // refuelRadius
                    10,     // refuelRate
                    10,     // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    SMALL_DOCK(     new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    500,    // maxHealth
                    500,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate                    
                    0,      // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    LARGE_DOCK(     new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    1200,   // maxHealth
                    1000,   // mineralCost
                    8,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    0,      // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    CAPITAL_DOCK(   new EnvironmentType[] {EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    3000,   // maxHealth
                    3000,   // mineralCost
                    15,     // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    0,      // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    MINING_FACILITY(new EnvironmentType[] {EnvironmentType.SMALL_METEOR, EnvironmentType.LARGE_METEOR},   // spawnLocations
                    100,    // spawnCooldown
                    500,    // maxHealth
                    500,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    10,     // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    ),
    FUEL_STATION(new EnvironmentType[] {EnvironmentType.SMALL_METEOR, EnvironmentType.LARGE_METEOR},   // spawnLocations
                    100,    // spawnCooldown
                    200,    // maxHealth
                    100,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    20,     // refuelRadius
                    10,     // refuelRate
                    0,      // miningRate
                    new Image("/TestImage.png", 50, 50, true, false, true)  // spriteImage
    );
    
    /**
     * The UnitTypes that may spawn a given UnitType.
     */
    private UnitType[] spawnSources;
    /**
     * The UnitTypes a given UnitType can spawn.
     */
    private UnitType[] spawnUnits;
    /**
     * The EnvironmentTypes upon which a given UnitType must be built.
     */
    private final EnvironmentType[] spawnLocations;
    /**
     * The WeaponTypes available to a given UnitType.
     */
    private final WeaponType[] arsenal;
    /**
     * The turns after producing a given UnitType that a spawn source must wait to produce again.
     */
    private final int spawnCooldown;
    /**
     * The maximum (not current) health of a given UnitType.
     */
    private final int maxHealth;
    /**
     * The cost in minerals to produce a given UnitType.
     */
    private final int mineralCost;
    /**
     * The body radius of a given UnitType.
     */
    private final int bodyRadius;
    /**
     * The radius at which a given UnitType can sense enemy UnitTypes.
     */
    private final int enemySensorRadius;
    /**
     * The radius at which a given UnitType can detect incoming WeaponTypes.
     */
    private final int incomingDetectionRadius;
    /**
     * The distance a given UnitType can move in one turn.
     */
    private final int flightRadius;
    /**
     * The maximum (not current) fuel capacity of a given UnitType.
     */
    private final int fuelMax;
    /**
     * The rate at which a given UnitType will burn fuel while moving per turn.
     */
    private final int fuelBurnRate;
    /**
     * The radius at which a FUEL_STATION can refuel ships.
     */
    private final int refuelRadius;
    /**
     * The rate at which a FUEL_STATION refuels a unit.
     */
    private final int refuelRate;
    /**
     * The rate at which a MINING_FACILITY can mine minerals from a SMALL_METEOR or LARGE_METEOR. 
     */
    private final int miningRate;
    /**
     * The bytecode usage limit per turn for a given UnitType.
     */
    private final int bytecodeLimit;
    /**
     * Contains the sprite data for a given UnitType.
     */
    private final Image spriteImage;
    
    static {
        FIGHTER.spawnSources = new UnitType[]{UnitType.SMALL_DOCK, UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK, UnitType.HOME_STATION};
        SIEGE.spawnSources = new UnitType[] {UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK};
        DESTROYER.spawnSources = new UnitType[] {UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK};
        CAPITAL.spawnSources = new UnitType[] {UnitType.CAPITAL_DOCK};
        HARVESTER.spawnSources = new UnitType[] {UnitType.SMALL_DOCK, UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK, UnitType.HOME_STATION};
        REFUELER.spawnSources = new UnitType[] {UnitType.SMALL_DOCK, UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK, UnitType.HOME_STATION};
        BUILDER.spawnSources = new UnitType[] {UnitType.HOME_STATION};

        HOME_STATION.spawnSources = null; 
        SMALL_DOCK.spawnSources = new UnitType[] {UnitType.BUILDER};
        LARGE_DOCK.spawnSources = new UnitType[] {UnitType.BUILDER};
        CAPITAL_DOCK.spawnSources = new UnitType[] {UnitType.BUILDER};
        MINING_FACILITY.spawnSources = new UnitType[] {UnitType.HARVESTER};
        FUEL_STATION.spawnSources = new UnitType[] {UnitType.REFUELER};
        
        FIGHTER.spawnUnits = null;
        SIEGE.spawnUnits = null;
        DESTROYER.spawnUnits = null;
        CAPITAL.spawnUnits = null;
        HARVESTER.spawnUnits = new UnitType[] {UnitType.MINING_FACILITY};
        REFUELER.spawnUnits = new UnitType[] {UnitType.REFUELER};
        BUILDER.spawnUnits = new UnitType[] {UnitType.SMALL_DOCK, UnitType.LARGE_DOCK, UnitType.CAPITAL_DOCK};

        HOME_STATION.spawnUnits = new UnitType[] {UnitType.BUILDER, UnitType.HARVESTER, UnitType.REFUELER, UnitType.FIGHTER}; 
        SMALL_DOCK.spawnUnits = new UnitType[] {UnitType.FIGHTER, UnitType.HARVESTER, UnitType.REFUELER};
        LARGE_DOCK.spawnUnits = new UnitType[] {UnitType.FIGHTER, UnitType.SIEGE, UnitType.DESTROYER, UnitType.HARVESTER, UnitType.REFUELER};
        CAPITAL_DOCK.spawnUnits = new UnitType[] {UnitType.FIGHTER, UnitType.SIEGE, UnitType.DESTROYER, UnitType.CAPITAL, UnitType.HARVESTER, UnitType.REFUELER};
        MINING_FACILITY.spawnUnits = null;
        FUEL_STATION.spawnUnits = null;
    }

    private UnitType(WeaponType[] arsenal, int spawnCooldown, int maxHealth,
            int mineralCost, int bodyRadius, int enemySensorRadius, 
            int incomingDetectionRadius, int flightRadius, int fuelMax, 
            int fuelBurnRate, Image spriteImage) {
        this.spawnLocations = null;
        this.arsenal = arsenal;
        this.spawnCooldown = spawnCooldown;
        this.maxHealth = maxHealth;
        this.mineralCost = mineralCost;
        this.bodyRadius = bodyRadius;
        this.enemySensorRadius = enemySensorRadius;
        this.incomingDetectionRadius = incomingDetectionRadius;
        this.flightRadius = flightRadius;
        this.fuelMax = fuelMax;
        this.fuelBurnRate = fuelBurnRate;
        this.refuelRadius = 0;
        this.refuelRate = 0;
        this.miningRate = 0;
        this.bytecodeLimit = 10000;
        this.spriteImage = spriteImage;
    }

    private UnitType(EnvironmentType[] spawnLocations, int spawnCooldown, 
            int maxHealth, int mineralCost, int bodyRadius, int flightRadius, 
            int refuelRadius, int refuelRate, int miningRate, Image spriteImage) {
        this.spawnLocations = spawnLocations;
        this.arsenal = null;
        this.spawnCooldown = spawnCooldown;
        this.maxHealth = maxHealth;
        this.mineralCost = mineralCost;
        this.bodyRadius = bodyRadius;
        this.enemySensorRadius = 0;
        this.incomingDetectionRadius = 0;
        this.flightRadius = flightRadius;
        this.fuelMax = Integer.MAX_VALUE;
        this.fuelBurnRate = 0;
        this.refuelRadius = refuelRadius;
        this.refuelRate = refuelRate;
        this.miningRate = miningRate;
        this.bytecodeLimit = 10000;
        this.spriteImage = spriteImage;
    }
    
    public boolean canAttack() {
        return arsenal.length > 0;
    }
    /**
     * True if the ship has the capability to launch a SMALL_BOMB or LARGE_BOMB.
     * @return true if the UnitType's arsenal contains a SMALL_BOMB or LARGE_BOMB.
     */
    public boolean canBomb() {
        return this == UnitType.DESTROYER || this == UnitType.CAPITAL;
    }
    /**
     * True if the UnitType has the capability to deploy a MINE.
     * @return true if the UnitType's arsenal contains a MINE.
     */
    public boolean canMine() {
        return this == UnitType.DESTROYER || this == UnitType.CAPITAL;
    }
    /**
     * True if the UnitType has PLANET_BOMBARDMENT in it's arsenal.
     * @return true if the UnitType has PLANET_BOMBARDMENT in it's arsenal.
     */
    public boolean canPlanetBombardment() {
        return this == SIEGE || this == CAPITAL;
    }
    /**
     * True if the UnitType can construct a UnitType that is a structure.
     * @return true if the UnitType can construct a StructureType (including a MINING_FACILITY or FUEL_STATION).
     */
    public boolean canBuildStructure() {
        return this == BUILDER || this == HARVESTER || this == REFUELER;
    }
    /**
     * True if the UnitType can construct a UnitType that is a ship.
     * @return true if the UnitType can construct a StructureType (including a MINING_FACILITY or FUEL_STATION).
     */
    public boolean canBuildShip() {
        return this == SMALL_DOCK || this == LARGE_DOCK || this == CAPITAL_DOCK
               || this == HOME_STATION;
    }
    /**
     * True if the UnitType can harvest directly from a SMALL_ASTEROID, LARGE_ASTEROID, SMALL_METEOR, or LARGE_METEOR.
     * @return true if the UnitType can harvest directly from a SMALL_ASTEROID, LARGE_ASTEROID, SMALL_METEOR, or LARGE_METEOR.
     */
    public boolean canHarvest() {
        return this == HARVESTER || this == MINING_FACILITY;
    }
    /**
     * True if the UnitType can refuel another UnitType directly.
     * @return true if the UnitType can refuel another UnitType directly.
     */
    public boolean canRefuel() {
        return this == REFUELER || this == FUEL_STATION;
    }
    
    
    public UnitType[] getSpawnSources() {
        return this.spawnSources;
    }
    
    public UnitType[] getSpawnUnits() {
        return this.spawnUnits;
    }
    
    public EnvironmentType[] getSpawnLocations() {
        return spawnLocations;
    }

    public WeaponType[] getArsenal() {
        return arsenal;
    }

    public int getSpawnCooldown() {
        return spawnCooldown;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMineralCost() {
        return mineralCost;
    }

    public int getBodyRadius() {
        return bodyRadius;
    }

    public int getEnemySensorRadius() {
        return enemySensorRadius;
    }

    public int getIncomingDetectionRadius() {
        return incomingDetectionRadius;
    }

    public int getFlightRadius() {
        return flightRadius;
    }

    public int getFuelMax() {
        return fuelMax;
    }

    public int getFuelBurnRate() {
        return fuelBurnRate;
    }

    public int getRefuelRadius() {
        return refuelRadius;
    }

    public int getRefuelRate() {
        return refuelRate;
    }

    public int getMiningRate() {
        return miningRate;
    }

    public int getBytecodeLimit() {
        return bytecodeLimit;
    }

    public Image getSpriteImage() {
        return spriteImage;
    }
    
}