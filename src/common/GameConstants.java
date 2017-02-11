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
package common;

/**
 *
 * @author Dylan Russell
 * 
 * GameConstants not contained in other classes (i.e. ShipType, WeaponType, 
 * etc.) is stored here.
 */
public enum GameConstants {
    
    /**
     * All ShipTypes.
     */
    FIGHTER,
    SIEGE,
    DESTROYER,
    CAPITAL,
    HARVESTER,
    REFUELER,
    BUILDER, 
    /**
     * All WeaponTypes.
     */
    SMALL_LASER,
    LARGE_LASER,
    SMALL_BOMB,
    LARGE_BOMB,
    MINE,
    PLANET_BOMBARDMENT,
    /**
     * All StructureTypes.
     */
    HOME_STATION,
    SMALL_DOCK,
    LARGE_DOCK,
    CAPITAL_DOCK,
    MINING_FACILITY,
    FUEL_STATION,
    /**
     * All EnvironmentTypes.
     */
    SMALL_METEOR,
    LARGE_METEOR,
    SMALL_ASTEROID,
    LARGE_ASTEROID,
    SMALL_PLANET,
    LARGE_PLANET;
    
}
