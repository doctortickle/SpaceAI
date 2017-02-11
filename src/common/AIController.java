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
public class AIController {
    
    private final Actor actor;
    
    public AIController(Actor actor) {
        this.actor = actor;
    }
    
    public GameConstants getType() {
        return actor.actorType;
    }
    
    public int getID() {
        return actor.getID();
    }

    public int getHealth() {
        return actor.getHealth();
    }

    public float getRadius() {
        return actor.getRadius();
    }

    public Team getTeam() {
        return actor.getTeam();
    }

    public MapLocation getLocation() {
        return actor.getLocation();
    }

    public boolean isShip() { return actor.isShip(); };

    public boolean isStructure() { return actor.isStructure(); };

    public boolean isWeapon() { return actor.isWeapon(); };

    public boolean isEnvironment() { return actor.isEnvironment(); };
    
}
