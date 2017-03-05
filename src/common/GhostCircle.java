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
class GhostCircle extends Actor{
    
    private int radius;
    private Location center;

    GhostCircle(int radius, Location center) {
        super(-1, Integer.MAX_VALUE, radius, center, Team.NEUTRAL, null);
        this.radius = radius;
        this.center = center;
    }

    @Override
    public int getRadius() {
        return this.radius;
    }

    void setRadius(int radius) {
        this.radius = radius;
    }

    Location getCenter() {
        return center;
    }

    void setCenter(Location center) {
        this.center = center;
    }

    @Override
    void update() {
        //Does not need updating.
    }

    @Override
    public boolean isCommandable() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return false;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }
    
    @Override
    public boolean isShip() {
        return false;
    }

    @Override
    public boolean isStructure() {
        return false;
    }
    
    @Override
    boolean collide(Actor actor) {
        return this.getLocation().distanceTo(actor.getLocation()) < this.getRadius() + actor.getRadius();
    }   
}
