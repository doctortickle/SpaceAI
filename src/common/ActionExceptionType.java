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

/**
 *
 * @author dr4ur
 */
public enum ActionExceptionType {
    /**
     * Internal error in the GameWorld engine.
     */
    INTERNAL_ERROR,
    /**
     * Indicates when a unit tries to perform an action for which it does not have enough minerals.
     */
    NOT_ENOUGH_MINERALS,
    /**
     * Indicates when a unit tries to move into a non-empty location.
     */
    CANT_MOVE_THERE,
    /**
     * Indicates when a unit tries to sense a unit, weapon, or environment that no longer exists or is no longer
     * in this unit's sensor range.
     */
    CANT_SENSE_THAT,
    /**
     * Indicates when a unit tries to perform an action on a location that is outside
     * its range.
     */
    OUT_OF_RANGE,
    /**
     * Indicates when a unit tries to perform an action it can't.
     */
    CANT_DO_THAT,
    /**
     * Indicates when a unit tries to perform an action on another unit, but there is
     * no suitable unit there.
     */
    NO_UNIT_THERE,
    /**
     * Indicates when a unit tries to perform an action on an environment object, but there is
     * no suitable environment object there.
     */
    NO_ENVIRONMENT_THERE,
}
