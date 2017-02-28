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
 * Stores basic information about a WeaponType object.
 */
public final class Weapon extends Actor {
    
    /**
     * The WeaponType of this weapon.
     */
    private final WeaponType type;
    private final Direction direction;
    private boolean spent;
    private boolean exploded;
    private final WeaponController wc;

    @Override
    public boolean isCommandable() {
        return false;
    }

    @Override
    public boolean isWeapon() {
        return true;
    }

    @Override
    public boolean isEnvironment() {
        return false;
    }

    public Weapon(SpaceAI spaceAI, WeaponType type, int ID, Location location, Team team, Direction direction) {
        super(ID, Integer.MAX_VALUE, type.getCollisionRadius(type), location, team, type.getSpriteImage());
        this.type = type;
        this.spent = false;
        this.exploded = false;
        if(type == WeaponType.MINE) {
            this.direction = null;
        }
        else {
            this.direction = direction;
        }
        this.wc = new WeaponController(this, spaceAI.gameWorld);
    }
  
    /**
     * Get the WeaponType of this weapon.
     * @return the WeaponType of this weapon.
     */
    public WeaponType getType() {
        return type;
    }
    
    public Direction getDirection() {
        return direction;
    }

    public boolean isSpent() {
        return spent;
    }

    public void setSpent(boolean spent) {
        this.spent = spent;
    }

    public boolean isExploded() {
        return exploded;
    }

    public void setExploded(boolean exploded) {
        this.exploded = exploded;
    }

    @Override
    public void update() {
        if(!isSpent() && (getType() != WeaponType.MINE)) {
            wc.move(direction);
        }
    }

    @Override
    public boolean collide(Actor actor) {
        switch(this.type) {
            case SMALL_LASER        : return laserCollision(actor);
            case LARGE_LASER        : return laserCollision(actor); 
            case SMALL_BOMB         : return bombCollision(actor);  
            case LARGE_BOMB         : return bombCollision(actor);  
            case MINE               : return mineCollision(actor);  
            case PLANET_BOMBARDMENT : return laserCollision(actor);
            default                 : return laserCollision(actor);
        }
    }   
    public void damageApplication(Actor actor) {
        switch(this.type) {
            case SMALL_LASER        : laserDamageApplication(actor); return;
            case LARGE_LASER        : laserDamageApplication(actor); return; 
            case SMALL_BOMB         : bombDamageApplication(actor); return;  
            case LARGE_BOMB         : bombDamageApplication(actor); return;   
            case MINE               : mineDamageApplication(actor); return;   
            case PLANET_BOMBARDMENT : laserDamageApplication(actor);         
        }
    }   
    private boolean laserCollision(Actor actor) {
        return this.getLocation().distanceTo(actor.getLocation()) < this.type.getWeaponRadius() + actor.getRadius();
    }
    private void laserDamageApplication(Actor actor) {
        if(actor.isCommandable()) {
            actor.setHealth(this.getType().getUnitDamage());
        }
        else if(actor.isEnvironment()) {
            actor.setHealth(this.getType().getEnvironmentDamage());
        }
        this.setSpent(true);
        this.setExploded(true);
    }
    private boolean bombCollision(Actor actor) {
        if(!this.isSpent()) {
            if(this.getLocation().distanceTo(actor.getLocation()) < this.type.getWeaponRadius() + actor.getRadius()) {
                return true;
            }
        }
        else if(this.isSpent()) {
            if(this.getLocation().distanceTo(actor.getLocation()) < this.type.getExplosionRadius() + actor.getRadius()) {
                return true;
            }
        }
        return false;
    }
    private void bombDamageApplication(Actor actor) {
        if(!this.isSpent()) {
            this.setSpent(true);
            return;
        }
        if(this.isSpent() && !this.isExploded()) {
            if(actor.isCommandable()) {
                actor.setHealth(this.getType().getUnitDamage());
            }
            else if(actor.isEnvironment()) {
                actor.setHealth(this.getType().getEnvironmentDamage());
            }
            this.setExploded(true);
        }
    }
    private boolean mineCollision(Actor actor) {
        if(!this.isSpent()) {
            if(this.getLocation().distanceTo(actor.getLocation()) < this.type.getDetectionRadius() + actor.getRadius()
                && actor.getTeam() == getTeam().opponent()) {
                return true;
            }
        }
        else if(this.isSpent()) {
            if(this.getLocation().distanceTo(actor.getLocation()) < this.type.getExplosionRadius() + actor.getRadius()) {
                return true;
            }
        }
        return false;
    }
    private void mineDamageApplication(Actor actor) {
        if(!this.isSpent()) {
            this.setSpent(true);
            return;
        }
        if(this.isSpent() && !this.isExploded()) {
            if(actor.isCommandable()) {
                actor.setHealth(this.getType().getUnitDamage());
            }
            else if(actor.isEnvironment()) {
                actor.setHealth(this.getType().getEnvironmentDamage());
            }
            this.setExploded(true);
        }
    }
}
