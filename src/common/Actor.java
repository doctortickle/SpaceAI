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
    protected int ID, health, radius, x, y;
    protected Team team;
    protected ImageView spriteFrame;
    protected Image spriteImage;

    public Actor(int ID, int health, int radius, int x, int y, Team team, Image spriteImage) {
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean isCommandable();
    
    public abstract boolean isWeapon();

    public abstract boolean isEnvironment();
    
    public abstract boolean collide(Actor object);
}
