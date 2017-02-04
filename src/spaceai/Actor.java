/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceai;

import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 */
public abstract class Actor {
    protected int ID, health, damage, radius;
    protected double iX, iY, vX, vY;
    protected Team team;
    protected boolean isUnit, isObject;
    protected ImageView spriteFrame;
    protected List<Image> imageStates;
    
    public Actor(double xLocation, double yLocation, Image... spriteCels) { //Used to instantiate an actor object
        spriteFrame = new ImageView(spriteCels[0]);
        imageStates.addAll(Arrays.asList(spriteCels));
        iX = xLocation;
        iY = yLocation;
        vX = vY = 0;
        isUnit = true;
        isObject = false;
    }
    
    public Actor(double xLocation, double yLocation, boolean unit, boolean object, Image... spriteCels) { //Used to instantiate an actor object
        spriteFrame = new ImageView(spriteCels[0]);
        imageStates.addAll(Arrays.asList(spriteCels));
        iX = xLocation;
        iY = yLocation;
        vX = vY = 0;
        isUnit = unit;
        isObject = object;
    }
    
    public abstract void update(); // Used to update the sprites every pulse.

}
