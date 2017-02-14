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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dylan Russell
 */
public class CastingDirector {
    
    private final List<Actor> TO_BE_ADDED;
    private final List<Unit> CURRENT_UNITS;
    private final List<Weapon> CURRENT_WEAPONS;
    private final List<Environment> CURRENT_ENVIRONMENT;
    private final List<Actor> COLLIDE_CHECKLIST;
    private final Set<Actor> REMOVED_ACTORS;

    public CastingDirector() {
        this.REMOVED_ACTORS = new HashSet<>();
        this.CURRENT_UNITS = new ArrayList<>();
        this.CURRENT_WEAPONS = new ArrayList<>();
        this.CURRENT_ENVIRONMENT = new ArrayList<>();
        this.COLLIDE_CHECKLIST = new ArrayList<>();
        this.TO_BE_ADDED = new ArrayList<>();
    }
    public List<Actor> getToBeAdded() {
        return TO_BE_ADDED;
    }
    public void addToBeAdded(Actor... actor) {
        TO_BE_ADDED.addAll( Arrays.asList(actor));
    }
    public void clearToBeAdded() {
        TO_BE_ADDED.clear();
    }
    public List<Unit> getCurrentUnits() {
        return CURRENT_UNITS;
    }
    public List<Weapon> getCurrentWeaponss() {
        return CURRENT_WEAPONS;
    }
    public List<Environment> getCurrentEnvironment() {
        return CURRENT_ENVIRONMENT;
    }
    public void addCurrentUnit(Unit...units) {
        CURRENT_UNITS.addAll( Arrays.asList(units) );
    }
    public void addCurrentWeapon(Weapon...weapons) {
        CURRENT_WEAPONS.addAll( Arrays.asList(weapons) );
    }
    public void addCurrentEnvironment(Environment...environment) {
        CURRENT_ENVIRONMENT.addAll( Arrays.asList(environment) );
    }
    public void removeCurrentUnit(Unit...units) {
        CURRENT_UNITS.removeAll( Arrays.asList(units) );
    }
    public void removeCurrentWeapon(Weapon...weapons) {
        CURRENT_WEAPONS.removeAll( Arrays.asList(weapons) );
    }
    public void removeCurrentEnvironment(Environment...environment) {
        CURRENT_ENVIRONMENT.removeAll( Arrays.asList(environment) );
    }   
    public void resetCurrentUnits() {
        CURRENT_UNITS.clear();
    }
    public void resetCurrentWeapons() {
        CURRENT_WEAPONS.clear();
    }
    public void resetCurrentEnvironment() {
        CURRENT_ENVIRONMENT.clear();
    }
    public void resetAllActors() {
        CURRENT_UNITS.clear();
        CURRENT_WEAPONS.clear();
        CURRENT_ENVIRONMENT.clear();
    }
    public List getCollideCheckList() {
        return COLLIDE_CHECKLIST;
    }
    public void resetCollideCheckList() {
        COLLIDE_CHECKLIST.clear();
        COLLIDE_CHECKLIST.addAll(CURRENT_UNITS);
        COLLIDE_CHECKLIST.addAll(CURRENT_WEAPONS);
        COLLIDE_CHECKLIST.addAll(CURRENT_ENVIRONMENT);
    }
    public Set<Actor> getRemovedActors() {
        return REMOVED_ACTORS;
    }
    public void addToRemovedActors(Actor...actors) {
        if(actors.length > 1) {
            REMOVED_ACTORS.addAll(Arrays.asList((Actor[]) actors));
        }
        else {
            REMOVED_ACTORS.add(actors[0]);
        }
    }
    public void resetRemovedActors() {
        REMOVED_ACTORS.stream().map((actor) -> {
            if(actor instanceof Unit) {
                CURRENT_UNITS.remove((Unit)actor);
            }
            return actor;
        }).map((actor) -> {
            if(actor instanceof Weapon) {
                CURRENT_WEAPONS.remove((Weapon)actor);
            }
            return actor;
        }).filter((actor) -> (actor instanceof Environment)).forEachOrdered((actor) -> {
            CURRENT_ENVIRONMENT.remove((Environment)actor);
        });
        REMOVED_ACTORS.clear();
    }
}
