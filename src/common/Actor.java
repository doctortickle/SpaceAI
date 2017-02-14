/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import javafx.scene.CacheHint;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

/**
 *
 * @author Dylan Russell
 */
public abstract class Actor {
    private int ID, health, radius;
    private Location location;
    private final Team team;
    private final ImageView spriteFrame;

    public Actor(int ID, int health, int radius, Location location, Team team, Image spriteImage) {
        this.ID = ID;
        this.health = health;
        this.radius = radius;
        this.team = team;
        this.location  = location;
        this.spriteFrame = new ImageView(spriteImage);
        teamColorize(spriteFrame);
    }
    
    public abstract void update();
    
    public int getID() {
        return ID;
    }

    public int getHealth() {
        return health;
    }

    public int getRadius() {
        return radius;
    }

    public Team getTeam() {
        return team;
    }
    
    public Location getLocation() {
        return location;
    }

    void setX(double x) {
        this.location = new Location(x, this.location.getY());
    }

    void setY(double y) {
        this.location = new Location(this.location.getX(), y);
    }
    
    protected ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public abstract boolean isCommandable();
    
    public abstract boolean isWeapon();

    public abstract boolean isEnvironment();
    
    public abstract boolean collide(Actor object);
    
    private void teamColorize(ImageView spriteFrame) {         
        Lighting lighting = new Lighting();
        lighting.setDiffuseConstant(1.0);
        lighting.setSpecularConstant(5.0);
        lighting.setSpecularExponent(0.0);
        lighting.setSurfaceScale(0.0);
        if(this.team==Team.A){
            lighting.setLight(new Light.Distant(45, 45, Color.RED));
        }
        if(this.team==Team.B) {
            lighting.setLight(new Light.Distant(45, 45, Color.BLUE));
        }
        if(this.team==Team.NEUTRAL) {
            lighting.setLight(new Light.Distant(45, 45, Color.BROWN));
        }
        spriteFrame.setEffect(lighting);
        spriteFrame.setCache(true);
        spriteFrame.setCacheHint(CacheHint.SPEED);
    }
}
