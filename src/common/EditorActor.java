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

import javafx.scene.CacheHint;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author dr4ur
 */
public class EditorActor {
    Object type;
    int radius;
    ImageView spriteFrame;

    public EditorActor(Object type) {
        this.type = type;
        if(type instanceof UnitType) {
            this.radius = ((UnitType) type).getBodyRadius();
            this.spriteFrame = new ImageView(((UnitType) type).getSpriteImage()); 
        }
        else if(type instanceof EnvironmentType) {
            this.radius = ((EnvironmentType) type).getBodyRadius();
            this.spriteFrame = new ImageView(((EnvironmentType) type).getSpriteImage()); 
        }
        colorize(type);
    }

    public Object getType() {
        return type;
    }
    public void setType(Object type) {
        this.type = type;
        if(type instanceof UnitType) {
            this.radius = ((UnitType) type).getBodyRadius();
            this.spriteFrame = new ImageView(((UnitType) type).getSpriteImage());
        }
        else if(type instanceof EnvironmentType) {
            this.radius = ((EnvironmentType) type).getBodyRadius();
            this.spriteFrame = new ImageView(((EnvironmentType) type).getSpriteImage()); 
        }
        colorize(type);
    }
    public int getRadius() {
        return radius;
    }
    public ImageView getSpriteFrame() {
        return spriteFrame;
    }
    private void colorize(Object type) {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(0.0);
        light.setElevation(70);
        if(type==UnitType.HOME_STATION){
            light.setColor(Color.RED);
        }
        else {
            light.setColor(Color.GOLD);
        }
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5);
        lighting.setSpecularExponent(5);

        this.spriteFrame.setEffect(lighting);
        this.spriteFrame.setCache(true);
        this.spriteFrame.setCacheHint(CacheHint.SPEED);
    }
    
}
