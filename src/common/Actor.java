/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.Arrays;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dylan Russell
 */
public class Actor {
    protected GameConstants actorType;
    protected int ID, health;
    protected float radius;
    protected Team team;
    protected MapLocation location;
    protected ImageView spriteFrame;
    protected Image spriteImage;

    public Actor(GameConstants actorType, int ID, int health, float radius, Team team, MapLocation location, Image spriteImage) {
        this.actorType = actorType;
        this.ID = ID;
        this.health = health;
        this.radius = radius;
        this.team = team;
        this.location = location;
        spriteFrame = new ImageView(spriteImage);
    }
    
    public void update() {
        // Used to update the sprites every pulse.
    } 

    public GameConstants getActorType() {
        return actorType;
    }
    
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

    public MapLocation getLocation() {
        return location;
    }
    
    public boolean isShip() { return false; };

    public boolean isStructure() { return false; };
    
    public boolean isWeapon() { return false; };

    public boolean isEnvironment() { return false; };
    
    public boolean collide(Actor object) { return false; };
}
