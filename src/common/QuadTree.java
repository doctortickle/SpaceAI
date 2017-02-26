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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dyaln Russell
 */
public class QuadTree {

    private int MAX_OBJECTS = 10;
    private int MAX_LEVELS = 5;
    private int level;
    private List actors;
    private double width, height, x, y;
    private QuadTree[] nodes;
    
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public QuadTree(int level, double width, double height, double x, double y) {
        this.level = level;
        this.actors = new ArrayList();
        this.width = width;
        this.height = height;
        this.x = x; //Top left x
        this.y = y; //Top left y
        nodes = new QuadTree[4];
     }
    public void clear() {
        actors.clear();

        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }
    private void split() {
        double subWidth = width / 2;
        double subHeight = height / 2;
 
        nodes[0] = new QuadTree(level+1, subWidth, subHeight, x + subWidth, y);
        nodes[1] = new QuadTree(level+1, subWidth, subHeight, x + subWidth, y + subHeight);
        nodes[2] = new QuadTree(level+1, subWidth, subHeight, x, y + subHeight);
        nodes[3] = new QuadTree(level+1, subWidth, subHeight, x, y);
    }
    private int getIndex(Actor actor) {
        int index = -1;
        double xMidpoint = x + (width / 2);
        double yMidpoint = y + (height / 2);
        // Object can completely fit within the top quadrants
        boolean topQuadrant = (actor.getLocation().getPixelY() < yMidpoint && actor.getLocation().getPixelY() + (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) < yMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (actor.getLocation().getPixelY() > yMidpoint && actor.getLocation().getPixelY() - (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) > yMidpoint);
        // Object can completely fit within the left quadrants
        if (actor.getLocation().getPixelX() < xMidpoint && actor.getLocation().getPixelX() + (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) < xMidpoint) {
            if (topQuadrant) {
                index = 3;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
         // Object can completely fit within the right quadrants
        else if (actor.getLocation().getPixelX() > xMidpoint && actor.getLocation().getPixelX() - (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) > xMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 1;
            }
        }
        return index;
    }
    public void insert(Actor actor) {
        if(nodes[0] != null) {
            int index = getIndex(actor);
            if (index != -1) {
                nodes[index].insert(actor);
                return;
            }
        }
        actors.add(actor);
        if(actors.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) { 
                split(); 
            }
            int i = 0;
            while (i < actors.size()) {
                int index = getIndex((Actor) actors.get(i));
                if (index != -1) {
                    nodes[index].insert((Actor) actors.get(i));
                    actors.remove(i);
                }
                else {
                    i++;
                }
            }
        }
    }
    public List retrieve(List returnActors, Actor actor) {
        int index = getIndex(actor);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnActors, actor);
        }
        else if (index == -1 && nodes[0] != null) {
            double xMidpoint = x + (width / 2);
            double yMidpoint = y + (height / 2);
            boolean topHalf = (actor.getLocation().getPixelY() < yMidpoint && actor.getLocation().getPixelY() + (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) < yMidpoint);
            boolean bottomHalf = (actor.getLocation().getPixelY() > yMidpoint && actor.getLocation().getPixelY() - (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) > yMidpoint);
            boolean rightHalf = (actor.getLocation().getPixelX() > xMidpoint && actor.getLocation().getPixelX() - (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) > xMidpoint);
            boolean leftHalf = (actor.getLocation().getPixelX() < xMidpoint && actor.getLocation().getPixelX() + (actor.getRadius()*GameConstants.COORDINATE_TO_PIXEL) < xMidpoint);
            if(topHalf) {
                nodes[0].retrieve(returnActors, actor); 
                nodes[3].retrieve(returnActors, actor);
            }
            else if(bottomHalf) {
                nodes[1].retrieve(returnActors, actor); 
                nodes[2].retrieve(returnActors, actor);
            }
            else if(rightHalf) {
                nodes[0].retrieve(returnActors, actor); 
                nodes[1].retrieve(returnActors, actor);
            }
            else if(leftHalf) {
                nodes[2].retrieve(returnActors, actor); 
                nodes[3].retrieve(returnActors, actor);
            }
            else {
                nodes[0].retrieve(returnActors, actor); 
                nodes[1].retrieve(returnActors, actor);
                nodes[2].retrieve(returnActors, actor);
                nodes[3].retrieve(returnActors, actor);
            }
        }
        returnActors.addAll(actors);
        return returnActors;
    }
}