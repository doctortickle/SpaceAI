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
public strictfp class WeaponController {
    private Weapon weapon;
    private GameWorld gameWorld;
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
        }
    }
}
