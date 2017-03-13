/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Objects;
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
    private ImageView spriteFrame;

    Actor(int ID, int health, int radius, Location location, Team team, Image spriteImage) {
        this.ID = ID;
        this.health = health;
        this.radius = radius;
        this.team = team;
        this.location  = location;
        this.spriteFrame = new ImageView(spriteImage);
        teamColorize(spriteFrame);
    }
    
    abstract void update();
    
    public int getID() {
        return ID;
    }

    public int getHealth() {
        return health;
    }
    
    void decreaseHealth(int damage) {
        this.health -= damage;
    }

    int getRadius() {
        return radius;
    }

    public Team getTeam() {
        return team;
    }
    
    public Location getLocation() {
        return location;
    }
    
    void updateLocation(double x, double y) {
        setX(x);
        setY(y);
    }
    
    void updateLocation(Location location) {
        this.location = location;
    }

    private void setX(double x) {
        this.location = new Location(x, this.location.getY());
    }

    private void setY(double y) {
        this.location = new Location(this.location.getX(), y);
    }
    
    ImageView getSpriteFrame() {
        return spriteFrame;
    }

    public abstract boolean isCommandable();
    
    public abstract boolean isWeapon();

    public abstract boolean isEnvironment();
    
    public abstract boolean isShip();
    
    public abstract boolean isStructure();
    
    abstract boolean collide(Actor object);
    
    private void teamColorize(ImageView spriteFrame) {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(0.0);
        light.setElevation(70);
        if(this.team==Team.A){
            light.setColor(Color.RED);
        }
        if(this.team==Team.B) {
            light.setColor(Color.BLUE);
        }
        if(this.team==Team.NEUTRAL) {
            light.setColor(Color.GOLD);
        }

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5);
        lighting.setSpecularExponent(5);

        spriteFrame.setEffect(lighting);
        spriteFrame.setCache(true);
        spriteFrame.setCacheHint(CacheHint.SPEED);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + this.ID;
        hash = 23 * hash + this.health;
        hash = 23 * hash + this.radius;
        hash = 23 * hash + Objects.hashCode(this.location);
        hash = 23 * hash + Objects.hashCode(this.team);
        hash = 23 * hash + Objects.hashCode(this.spriteFrame);
        return hash;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        if (this.ID != other.ID) {
            return false;
        }
        if (this.health != other.health) {
            return false;
        }
        if (this.radius != other.radius) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (this.team != other.team) {
            return false;
        }
        if (!Objects.equals(this.spriteFrame, other.spriteFrame)) {
            return false;
        }
        return true;
    } 
}
