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
 * Details the attributes of the various types of units available in Space AI. 
 * All of these unit types can be directly commanded by the player.
 * @author Dylan Russell
 * @version 1.0
 * @see Unit
 */
public strictfp enum UnitType {
    /**
     * A basic attacking unit. Quick and cheap, but with little firepower.
     */
    FIGHTER (   new WeaponType[] {WeaponType.SMALL_LASER},  // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                5,      // bodyRadius
                10,     // sensorRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate
                new Image("/FIGHTER.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A unit specializing in dealing damage to structures and environments. Can use planet bombardment.
     */
    SIEGE(   new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.PLANET_BOMBARDMENT},  // arsenal
                10,     // spawnCooldown
                50,     // maxHealth
                300,    // mineralCost
                8,      // bodyRadius
                10,     // sensorRadius
                10,     // incomingDetectionRadius
                3,      // flightRadius
                1000,   // fuelMax
                3,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate 
                new Image("/SIEGE.png", 8*GameConstants.COORDINATE_TO_PIXEL*2, 8*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A unit specializing in dealing damage to other ships. Can deploy mines and small bombs.
     */
    DESTROYER(  new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.MINE},  // arsenal
                10,     // spawnCooldown
                100,    // maxHealth
                300,    // mineralCost
                10,      // bodyRadius
                15,     // sensorRadius
                20,     // incomingDetectionRadius
                3,      // flightRadius
                1000,   // fuelMax
                3,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate
                new Image("/DESTROYER.png", 10*GameConstants.COORDINATE_TO_PIXEL*2, 10*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A slow-moving and expensive, but powerful, unit. Can deploy all possible weapons.
     */
    CAPITAL(    new WeaponType[] {WeaponType.SMALL_LASER, WeaponType.LARGE_LASER, WeaponType.SMALL_BOMB, WeaponType.LARGE_BOMB, WeaponType.MINE, WeaponType.PLANET_BOMBARDMENT},  // arsenal
                50,     // spawnCooldown
                500,    // maxHealth
                1000,   // mineralCost
                20,      // bodyRadius
                20,     // sensorRadius
                25,     // incomingDetectionRadius
                1,      // flightRadius
                2000,   // fuelMax
                5,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate
                new Image("/CAPITAL.png", 20*GameConstants.COORDINATE_TO_PIXEL*2, 20*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A unit specializing in gathering minerals from asteroids and meteors. Can build mining facilities.
     */
    HARVESTER(  null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                5,      // bodyRadius
                10,     // sensorRadius
                20,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                GameConstants.MAX_ENV_BUILD_DISTANCE +1,      // harvestingRadius
                20,      // harvestingRate
                new Image("/HARVESTER.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A unit specializing in refueling friendly ships. Can build fuel stations.
     */
    REFUELER(   null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                5,      // bodyRadius
                10,     // sensorRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                10,      // refuelRadius
                5,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate
                new Image("/REFUELER.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * The primary construction unit. Can construct all docks.
     */
    BUILDER(    null,   // arsenal
                5,      // spawnCooldown
                10,     // maxHealth
                50,     // mineralCost
                5,      // bodyRadius
                30,     // sensorRadius
                15,     // incomingDetectionRadius
                5,      // flightRadius
                1000,   // fuelMax
                1,      // fuelBurnRate
                0,      // refuelRadius
                0,      // refuelRate
                0,      // harvestingRadius
                0,      // harvestingRate
                new Image("/BUILDER.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * The primary and most important structure. Both teams begin with a home station. 
     * Only structure capable of building builders. Only structure that can move.
     */
    HOME_STATION(   null,   // spawnLocations
                    0,      // spawnCooldown
                    2000,   // maxHealth
                    0,      // mineralCost
                    10,     // bodyRadius
                    1,      // flightRadius
                    10,     // refuelRadius
                    10,     // refuelRate
                    0,      // harvestingRadius    
                    10,     // harvestingRate
                    new Image("/HOME_STATION.png", 10*GameConstants.COORDINATE_TO_PIXEL*2, 10*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * The cheapest and least versatile space dock. Can build fighters, harvesters, and refuelers.
     */
    SMALL_DOCK(     new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    500,    // maxHealth
                    500,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    0,      // harvestingRadius
                    0,      // harvestingRate
                    new Image("/TestImage.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A superior space dock. Can build fighters, siege, destroyers, harvesters, and refuelers.
     */
    LARGE_DOCK(     new EnvironmentType[] {EnvironmentType.SMALL_PLANET, EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    1200,   // maxHealth
                    1000,   // mineralCost
                    8,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    0,      // harvestingRadius
                    0,      // harvestingRate
                    new Image("/TestImage.png", 8*GameConstants.COORDINATE_TO_PIXEL*2, 8*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * The most expensive and powerful space dock. Can build all units (except builders), including the mighty capital ship.
     */
    CAPITAL_DOCK(   new EnvironmentType[] {EnvironmentType.LARGE_PLANET},   // spawnLocations
                    100,    // spawnCooldown
                    3000,   // maxHealth
                    3000,   // mineralCost
                    15,     // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    0,      // harvestingRadius
                    0,      // harvestingRate
                    new Image("/TestImage.png", 15*GameConstants.COORDINATE_TO_PIXEL*2, 15*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A structure that can continually harvest minerals from meteors.
     */
    MINING_FACILITY(new EnvironmentType[] {EnvironmentType.SMALL_ASTEROID, EnvironmentType.LARGE_ASTEROID},   // spawnLocations
                    100,    // spawnCooldown
                    500,    // maxHealth
                    500,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    0,      // refuelRadius
                    0,      // refuelRate
                    6,      // harvestingRadius
                    10,     // harvestingRate
                    new Image("/TestImage.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
    ),
    /**
     * A structure that can continually refuel friendly ships in a given radius.
     */
    FUEL_STATION(   null,   // spawnLocations
                    100,    // spawnCooldown
                    200,    // maxHealth
                    100,    // mineralCost
                    5,      // bodyRadius
                    0,      // flightRadius
                    20,     // refuelRadius
                    10,     // refuelRate
                    0,      // harvestingRadius
                    0,      // harvestingRate
                    new Image("/TestImage.png", 5*GameConstants.COORDINATE_TO_PIXEL*2, 5*GameConstants.COORDINATE_TO_PIXEL*2, true, false, true)  // spriteImage
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
     * The radius at which a given UnitType can sense enemy units.
     */
    private final int sensorRadius;
    /**
     * The radius at which a given UnitType can detect incoming weapons.
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
     * The radius at which a REFUELER or FUEL_STATION can refuel ships.
     */
    private final int refuelRadius;
    /**
     * The rate at which a REFUELER or FUEL_STATION refuels a unit.
     */
    private final int refuelRate;
    /**
     *  The radius at which a HARVESTER or MINING_FACILITY can harvest minerals from an appropriate EnvironmentType.
     */
    private final int harvestingRadius;
    /**
     * The rate at which a HARVESTER or MINING_FACILITY can harvest minerals from an appropriate EnvironmentType.
     */
    private final int harvestingRate;
    /**
     * True if a UnitType is a ship.
     */
    private final boolean ship;
    /**
     * True if a UnitType is a structure.
     */
    private final boolean structure;
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
            int mineralCost, int bodyRadius, int sensorRadius, 
            int incomingDetectionRadius, int flightRadius, int fuelMax, 
            int fuelBurnRate, int refuelRadius, int refuelRate, int harvestingRadius, int harvestingRate,  Image spriteImage) {
        this.spawnLocations = null;
        this.arsenal = arsenal;
        this.spawnCooldown = spawnCooldown;
        this.maxHealth = maxHealth;
        this.mineralCost = mineralCost;
        this.bodyRadius = bodyRadius;
        this.sensorRadius = sensorRadius;
        this.incomingDetectionRadius = incomingDetectionRadius;
        this.flightRadius = flightRadius;
        this.fuelMax = fuelMax;
        this.fuelBurnRate = fuelBurnRate;
        this.refuelRadius = refuelRadius;
        this.refuelRate = refuelRate;
        this.harvestingRadius = harvestingRadius;
        this.harvestingRate = harvestingRate;
        this.ship = true;
        this.structure = false;
        this.bytecodeLimit = 10000;
        this.spriteImage = spriteImage;
    }

    private UnitType(EnvironmentType[] spawnLocations, int spawnCooldown, 
            int maxHealth, int mineralCost, int bodyRadius, int flightRadius, 
            int refuelRadius, int refuelRate, int harvestingRadius, int harvestingRate, Image spriteImage) {
        this.spawnLocations = spawnLocations;
        this.arsenal = null;
        this.spawnCooldown = spawnCooldown;
        this.maxHealth = maxHealth;
        this.mineralCost = mineralCost;
        this.bodyRadius = bodyRadius;
        this.sensorRadius = 0;
        this.incomingDetectionRadius = 0;
        this.flightRadius = flightRadius;
        this.fuelMax = 10000;
        this.fuelBurnRate = 0;
        this.refuelRadius = refuelRadius;
        this.refuelRate = refuelRate;
        this.harvestingRadius = harvestingRadius;
        this.harvestingRate = harvestingRate;
        this.ship = false;
        this.structure = true;
        this.bytecodeLimit = 10000;
        this.spriteImage = spriteImage;
    }
    
    /**
     * Determines if the UnitType has the capability to launch any kind of WeaponType.
     * @return true if the UnitType's arsenal contains any kind of WeaponType.
     * @see #FIGHTER
     * @see #SIEGE
     * @see #DESTROYER
     * @see #CAPITAL
     * @see WeaponType
     */
    public boolean canAttack() {
        return arsenal.length > 0;
    }
    /**
     * Determines if the UnitType has the capability to launch a SMALL_BOMB or LARGE_BOMB.
     * @return true if the UnitType is a DESTROYER or CAPITAL.
     * @see #DESTROYER
     * @see #CAPITAL
     * @see WeaponType#SMALL_BOMB
     * @see WeaponType#LARGE_BOMB
     */
    public boolean canBomb() {
        return this == UnitType.DESTROYER || this == UnitType.CAPITAL;
    }
    /**
     * Determines if the UnitType has the capability to deploy a MINE.
     * @return true if the UnitType is a DESTROYER or a CAPITAL.
     * @see #DESTROYER
     * @see #CAPITAL
     * @see WeaponType#MINE
     */
    public boolean canDeployMine() {
        return this == UnitType.DESTROYER || this == UnitType.CAPITAL;
    }
    /**
     * Determines if the UnitType has the capability to deploy PLANET_BOMBARDMENT.
     * @return true if the UnitType is a SIEGE or CAPITAL.
     * @see #SIEGE
     * @see #CAPITAL
     * @see WeaponType#PLANET_BOMBARDMENT
     */
    public boolean canPlanetBombardment() {
        return this == SIEGE || this == CAPITAL;
    }
    /**
     * Determines if the UnitType can construct a UnitType that is a structure.
     * @return true if the UnitType is a BUILDER, HARVESTER, or REFUELER.
     * @see #BUILDER
     * @see #HARVESTER
     * @see #REFUELER
     */
    public boolean canBuildStructure() {
        return this == BUILDER || this == HARVESTER || this == REFUELER;
    }
    /**
     * Determines if the UnitType can construct a UnitType that is a ship.
     * @return true if the UnitType is a SMALL_DOCK, LARGE_DOCK, CAPITAL_DOCK, or HOME_STATION.
     * @see #SMALL_DOCK
     * @see #LARGE_DOCK
     * @see #CAPITAL_DOCK
     * @see #HOME_STATION
     */
    public boolean canBuildShip() {
        return this == SMALL_DOCK || this == LARGE_DOCK || this == CAPITAL_DOCK
               || this == HOME_STATION;
    }
    /**
     * Determines if the UnitType can harvest directly from a SMALL_METEOR, LARGE_METEOR, SMALL_ASTEROID, or LARGE_ASTEROID.
     * Returns true for HARVESTER and MINING_FACILITY.
     * @return true if the UnitType is a HARVESTER or a MINING_FACILITY.
     * @see #HARVESTER
     * @see #MINING_FACILITY
     */
    public boolean canHarvest() {
        return this == HARVESTER || this == MINING_FACILITY;
    }
    /**
     * Determines if the UnitType can refuel another UnitType directly.
     * @return true if the UnitType is a REFUELER or FUEL_STATION.
     * @see #REFUELER
     * @see #FUEL_STATION
     */
    public boolean canRefuel() {
        return this == REFUELER || this == FUEL_STATION;
    }
    /**
     * Returns an array of UnitTypes that may spawn a given UnitType.
     * @return UnitType array of spawn sources for a given UnitType. Null if there are no spawn sources.
     */
    public UnitType[] getSpawnSources() {
        return this.spawnSources;
    }
    /**
     * Returns an array of UnitTypes that may be spawned by a given UnitType.
     * @return UnitType array that may be spawned by a given UnitType. Null if there are no spawn units.
     * @see #canBuildShip()
     * @see #canBuildStructure() 
     */
    public UnitType[] getSpawnUnits() {
        return this.spawnUnits;
    }
    /**
     * Returns an array of EnvironmentTypes where a given UnitType may be spawned.
     * @return EnvironmentType array where a given UnitType may be spawned. Null if unit can be spawned anywhere.
     */
    public EnvironmentType[] getSpawnLocations() {
        return spawnLocations;
    }
    /**
     * Returns an array of WeaponTypes that a given UnitType may fire or deploy.
     * @return WeaponType array that a given UnitType may fire or deploy. Null if UnitType can not attack.
     * @see #canAttack() 
     * @see #canBomb()
     * @see #canDeployMine()
     * @see #canPlanetBombardment()
     * @see EnvironmentType
     */
    public WeaponType[] getArsenal() {
        return arsenal;
    }
    /**
     * Returns the number of turns a unit must wait after spawning a given UnitType.
     * @return int number of turns required cooldown after spawning UnitType.
     * @see #spawnCooldown
     * @see WeaponType
     */
    public int getSpawnCooldown() {
        return spawnCooldown;
    }
    /**
     * Returns the max health (not current) of a given UnitType.
     * @return int max health of a given UnitType.
     * @see #maxHealth
     */
    public int getMaxHealth() {
        return maxHealth;
    }
    /**
     * Returns the mineral cost of a given UnitType
     * @return int mineral cost of a given UnitType
     * @see #mineralCost
     */
    public int getMineralCost() {
        return mineralCost;
    }
    /**
     * Returns the body radius of a given UnitType.
     * @return int body radius of a given UnitType.
     * @see #bodyRadius
     */
    public int getBodyRadius() {
        return bodyRadius;
    }
    /**
     * Returns the radius at which a given UnitType can detect other units.
     * @return int radius at which a given UnitType can detect other units.
     * @see #sensorRadius
     */
    public int getSensorRadius() {
        return sensorRadius;
    }
    /**
     * Returns the radius at which a given UnitType can detect incoming weapons.
     * @return int radius at which a given UnitType can detect incoming weapons.
     * @see #incomingDetectionRadius
     */
    public int getIncomingDetectionRadius() {
        return incomingDetectionRadius;
    }
    /**
     * Returns the distance a UnitType can move in one turn.
     * @return int distance a UnitType can move in one turn.
     * @see #flightRadius
     */
    public int getFlightRadius() {
        return flightRadius;
    }
    /**
     * Returns the max (not current) fuel of a given UnitType.
     * @return int max (not current) fuel of a given UnitType.
     * @see #fuelMax
     */
    public int getFuelMax() {
        return fuelMax;
    }
    /**
     * Returns the rate at which a given UnitType will burn fuel while moving per turn.
     * @return int rate at which a given UnitType will burn fuel while moving per turn.
     * @see #fuelBurnRate
     */
    public int getFuelBurnRate() {
        return fuelBurnRate;
    }
    /**
     * Returns the radius at which a UnitType can refuel a unit.
     * @return int radius at which a UnitType can refuel a unit.
     * @see #refuelRadius
     */
    public int getRefuelRadius() {
        return refuelRadius;
    }
    /**
     * Returns the rate at which a UnitType refuels a unit.
     * @return int rate at which a UnitType refuels a unit.
     * @see #refuelRate
     */
    public int getRefuelRate() {
        return refuelRate;
    }
    /**
     * Returns the radius at which a UnitType can harvest minerals from an appropriate EnvironmentType.
     * @return int radius at which a UnitType can harvest minerals from an appropriate EnvironmentType.
     * @see #harvestingRadius
     */
    public int getHarvestingRadius(){
        return harvestingRadius;
    }
    /**
     * Returns the rate at which a UnitType harvests minerals from an appropriate EnvironmentType.
     * @return int rate at which a UnitType harvests minerals from an appropriate EnvironmentType.
     * @see #harvestingRate
     */
    public int getHarvestingRate() {
        return harvestingRate;
    }
    /**
     * Returns whether or not this UnitType is a ship.
     * @return true if UnitType is a ship.
     * @see #ship
     */
    public boolean isShip() {
        return ship;
    }
    /**
     * Returns whether or not this UnitType is a structure.
     * @return true if UnitType is a structure.
     * @see #structure
     */
    public boolean isStructure() {
        return structure;
    }
    /**
     * Returns the bytecode limit per turn for a given UnitType.
     * @return int bytecode limit per turn for a given UnitType.
     * @see #bytecodeLimit
     */
    int getBytecodeLimit() {
        return bytecodeLimit;
    }
    /**
     * Returns the sprite image for a given UnitType.
     * @return Image sprite image for a given UnitType.
     * @see #spriteImage
     */
    Image getSpriteImage() {
        return spriteImage;
    }    
}