/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceai;

import javafx.scene.image.Image;

/**
 *
 * @author Dylan Russell
 */
public abstract class Unit extends Actor{
    
    protected double maxHealth, topSpeed, fuel, attackPower;
    protected boolean isMilitary, isCivilian;
    
    public Unit(double xLocation, double yLocation, Image... spriteCels) {
        super(xLocation, yLocation, spriteCels);
    }
    
}
