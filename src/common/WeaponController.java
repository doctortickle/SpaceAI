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

import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author dr4ur
 */
public strictfp class WeaponController {
    private final Weapon weapon;
    private final GameWorld gameWorld;
    private final double leftBoundary;
    private final double rightBoundary;
    private final double bottomBoundary;
    private final double topBoundary;    
    
    public WeaponController(Weapon weapon, GameWorld gameWorld) {
        this.weapon = weapon;
        this.gameWorld = gameWorld;
        this.leftBoundary = GameConstants.MIN_X_COORDINATE;
        this.rightBoundary = GameConstants.MAX_X_COORDINATE;
        this.bottomBoundary = GameConstants.MIN_Y_COORDINATE;
        this.topBoundary = GameConstants.MAX_Y_COORDINATE;
    }
    
    // *********************************
    // ******** WEAPON UPDATES *********
    // *********************************
    
    private void updateSpriteAndLocation(Location location) {
        weapon.updateLocation(location.getX(), location.getY());
        weapon.getSpriteFrame().setTranslateX(location.getPixelX());
        weapon.getSpriteFrame().setTranslateY(location.getPixelY());
    }
    private boolean checkBoundaries(Location location) {
        if(location.getY() >= topBoundary - weapon.getType().getWeaponRadius()) { return false; }
        if(location.getY() <= bottomBoundary + weapon.getType().getWeaponRadius()) { return false; }
        if(location.getX() >= rightBoundary - weapon.getType().getWeaponRadius()) { return false; }
        if(location.getX() <= leftBoundary + weapon.getType().getWeaponRadius()) { return false; }
        return true;
    }    
    public final void move(Direction direction) {
        Location movePoint = weapon.getLocation().add(weapon.getType().getLaunchSpeed(), direction);
        if  (checkBoundaries(movePoint)) {
            updateSpriteAndLocation(movePoint);   
        }
        else if (!checkBoundaries(movePoint)) {
            weapon.setSpent(true);
            weapon.setExploded(true);
            weapon.setClearCountdown(GameConstants.WEAPON_CLEAR_COUNTDOWN);
        }
    }
    
    // *********************************
    // ***** COLLISION AND DAMAGE ******
    // *********************************
    
    public boolean collide(Actor actor) {
        switch(weapon.getType()) {
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
        switch(weapon.getType()) {
            case SMALL_LASER        : laserDamageApplication(actor); return;
            case LARGE_LASER        : laserDamageApplication(actor); return; 
            case SMALL_BOMB         : bombDamageApplication(); return;  
            case LARGE_BOMB         : bombDamageApplication(); return;   
            case MINE               : mineDamageApplication(); return;   
            case PLANET_BOMBARDMENT : laserDamageApplication(actor);         
        }
    }   
    private boolean laserCollision(Actor actor) {
        return weapon.getLocation().distanceTo(actor.getLocation()) < weapon.getType().getWeaponRadius() + actor.getRadius();
    }
    private void laserDamageApplication(Actor actor) {
        if(actor.isCommandable()) {
            actor.setHealth(weapon.getType().getUnitDamage());
        }
        else if(actor.isEnvironment() || actor.isStructure()) {
            actor.setHealth(weapon.getType().getEnvironmentDamage());
        }
        weapon.setSpent(true);
        weapon.setExploded(true);
        weapon.setClearCountdown(GameConstants.WEAPON_CLEAR_COUNTDOWN);
    }
    private boolean bombCollision(Actor actor) {
        if(!weapon.isSpent()) {
            if(weapon.getLocation().distanceTo(actor.getLocation()) < weapon.getType().getWeaponRadius() + actor.getRadius()) {
                weapon.setSpent(true);
                explodeAnimation();
                return true;
            }
        }
        return false;
    }
    private void bombDamageApplication() {
        if(weapon.isSpent() && !weapon.isExploded()) {        
            List<Actor> actorsHit = gameWorld.returnNonWeaponsInCircle(weapon.getLocation(), weapon.getType().getExplosionRadius());
            for(Actor actorHit : actorsHit) {
                if(actorHit.isCommandable()) {
                    actorHit.setHealth(weapon.getType().getUnitDamage());
                }
                else if(actorHit.isEnvironment() || actorHit.isStructure()) {
                    actorHit.setHealth(weapon.getType().getEnvironmentDamage());
                }
            }
            weapon.setExploded(true);
        }
    }
    private boolean mineCollision(Actor actor) {
        if(!weapon.isSpent()) {
            if(weapon.getLocation().distanceTo(actor.getLocation()) < weapon.getType().getDetectionRadius() + actor.getRadius()
                && actor.getTeam() == weapon.getTeam().opponent()) {
                weapon.setSpent(true);
                explodeAnimation();
                return true;
            }
        }
        return false;
    }
    private void mineDamageApplication() {
        if(weapon.isSpent() && !weapon.isExploded()) {
            List<Actor> actorsHit = gameWorld.returnNonWeaponsInCircle(weapon.getLocation(), weapon.getType().getExplosionRadius());
            for(Actor actorHit : actorsHit) {
                if(actorHit.isCommandable()) {
                    actorHit.setHealth(weapon.getType().getUnitDamage());
                }
                else if(actorHit.isEnvironment() || actorHit.isStructure()) {
                    actorHit.setHealth(weapon.getType().getEnvironmentDamage());
                }
            }
            weapon.setExploded(true);
        }
    }
    private void explodeAnimation() {
        Image newImage = null;
        switch(weapon.getType()) {
            case SMALL_BOMB : newImage = new Image("/SMALL_BOMB_EXPLOSION.png", 40, 40, true, false, true); break;
            case LARGE_BOMB : newImage = new Image("/LARGE_BOMB_EXPLOSION.png", 60, 60, true, false, true); break;
            case MINE : newImage = new Image("/MINE_EXPLOSION.png", 52, 52, true, false, true); break;
        }
        weapon.getSpriteFrame().setImage(newImage);
    }
}
