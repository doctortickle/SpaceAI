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
    
    FIGHTER,
    SIEGE,
    DESTROYER,
    CAPITAL,
    HARVESTER,
    REFUELER,
    BUILDER,
    
    HOME_STATION,
    SMALL_DOCK,
    LARGE_DOCK,
    CAPITAL_DOCK,
    MINING_FACILITY,
    FUEL_STATION;
    
    /**
     * The UnitTypes that may spawn a given UnitType.
     */
    public final UnitType[] spawnSources;
    /**
     * The UnitTypes a given UnitType can spawn.
     */
    public final UnitType[] spawnUnits;
    /**
     * The EnvironmentTypes upon which a given UnitType must be built.
     */
    public final EnvironmentType[] spawnLocations;
    /**
     * The WeaponTypes available to a given UnitType.
     */
    public final WeaponType[] arsenal;
    /**
     * The turns after producing a given UnitType that a spawn source must wait to produce again.
     */
    public final int spawnCooldownTurns;
    /**
     * The maximum (not current) health of a given UnitType.
     */
    public final int maxHealth;
    /**
     * The cost in minerals to produce a given UnitType.
     */
    public final int mineralCost;
    /**
     * The body radius of a given UnitType.
     */
    public final float bodyRadius;
    /**
     * The radius at which a given UnitType can sense enemy UnitTypes.
     */
    public final float enemySensorRadius;
    /**
     * The radius at which a given UnitType can detect incoming WeaponTypes.
     */
    public final float incomingDetectionRadius;
    /**
     * The distance a given UnitType can move in one turn.
     */
    public final float flightRadius;
    /**
     * The maximum (not current) fuel capacity of a given UnitType.
     */
    public final int fuelMax;
    /**
     * The rate at which a given UnitType will burn fuel while moving per turn.
     */
    public final int fuelBurnRate;
    /**
     * The radius at which a FUEL_STATION can refuel ships.
     */
    public final float refuelRadius;
    /**
     * The rate at which a MINING_FACILITY can mine minerals from a SMALL_METEOR or LARGE_METEOR. 
     */
    public final float miningRate;
    /**
     * The bytecode usage limit per turn for a given UnitType.
     */
    public final int bytecodeLimit;
    /**
     * Contains the sprite data for a given UnitType.
     */
    public final Image spriteImage;

    
}