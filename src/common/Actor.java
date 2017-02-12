/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 */
public abstract class Actor {
    private int ID, health, radius;
    private double x, y;
    private Team team;
    private ImageView spriteFrame;
    private Image spriteImage;

    public Actor(int ID, int health, int radius, double x, double y, Team team, Image spriteImage) {
        this.ID = ID;
        this.health = health;
        this.radius = radius;
        this.team = team;
        this.x  = x;
        this.y  = y;
        this.spriteFrame = new ImageView(spriteImage);
    }
    
    public abstract void update();
    
    public int getID() {
        return ID;
    }

    public int getHealth() {
        return health;
    }

    public float getRadius() {
        return radius;
    }

    public Team getTeam() {
        return team;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    void setX(double x) {
        this.x = x;
    }

    void setY(double y) {
        this.y = y;
    }
    
    protected ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public abstract boolean isCommandable();
    
    public abstract boolean isWeapon();

    public abstract boolean isEnvironment();
    
    public abstract boolean collide(Actor object);
}
