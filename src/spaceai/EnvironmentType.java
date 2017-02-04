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
package spaceai;

/**
 *
 * @author Dylan Russell
 */
public enum EnvironmentType {
    
    SMALL_METEOR    (1000,  10,     1,      5000,   (Math.PI)/6),
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    LARGE_METEOR    (3000,  20,     1,      12000,  (Math.PI)/9),
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    SMALL_ASTEROID  (700,   3,      0,      500,    (Math.PI)/6),
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    LARGE_ASTEROID  (1500,  6,      0,      1000,   (Math.PI)/9),
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    SMALL_PLANET    (10000, 30,     2,      0,      (Math.PI)/9),
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    LARGE_PLANET    (30000, 50,     4,      0,      (Math.PI)/12);
    /*              maxHP   Rad     Cap     MinMax      RotV    */
    
    public final int maxHealth;
    public final float bodyRadius;
    public final int structureCap;
    public final int mineralMax;
    public final double rotationV;
    
    public boolean canBeMined() {
        return mineralMax > 0;
    }
    
    public boolean canBuildOn() {
        return structureCap > 0;
    }

    private EnvironmentType(int maxHealth, float bodyRadius, int structureCap, int mineralMax, double rotationV) {
        this.maxHealth      = maxHealth;
        this.bodyRadius     = bodyRadius;
        this.structureCap   = structureCap;
        this.mineralMax     = mineralMax;
        this.rotationV      = rotationV;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public float getBodyRadius() {
        return bodyRadius;
    }

    public int getStructureCap() {
        return structureCap;
    }

    public int getMineralMax() {
        return mineralMax;
    }

    public double getRotationV() {
        return rotationV;
    }
    
    
     
}
