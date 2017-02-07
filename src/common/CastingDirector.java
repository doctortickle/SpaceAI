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
    
    private final List<Actor> CURRENT_CAST;
    private final List<Actor> COLLIDE_CHECKLIST;
    private final Set<Actor> REMOVED_ACTORS;

    public CastingDirector() {
        this.REMOVED_ACTORS = new HashSet<>();
        this.CURRENT_CAST = new ArrayList<>();
        this.COLLIDE_CHECKLIST = new ArrayList<>();
    }

    public List<Actor> getCurrentCast() {
        return CURRENT_CAST;
    }
    
    public void addCurrentCast(Actor...actors) {
        CURRENT_CAST.addAll( Arrays.asList(actors) );
    }
    
    public void removeCurrentCast(Actor...actors) {
        CURRENT_CAST.removeAll( Arrays.asList(actors) );
    }
    
    public void resetCurrentCast() {
        CURRENT_CAST.clear();
    }
    
    public List getCollideCheckList() {
        return COLLIDE_CHECKLIST;
    }
    
    public void resetCollideCheckList() {
        COLLIDE_CHECKLIST.clear();
        COLLIDE_CHECKLIST.addAll(CURRENT_CAST);
    }
    
    public Set getRemovedActors() {
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
        CURRENT_CAST.removeAll(REMOVED_ACTORS);
        REMOVED_ACTORS.clear();
    }
}
