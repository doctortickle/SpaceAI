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

import java.awt.Rectangle;
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
    private List<Actor> objects;
    private Rectangle bounds;
    private QuadTree[] nodes;

    public QuadTree(int pLevel, Rectangle pBounds) {
     this.level = pLevel;
     this.objects = new ArrayList();
     this.bounds = pBounds;
     this.nodes = new QuadTree[4];
    }
    public void clear() {
        objects.clear();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] != null) {
                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }
    private void split() {
        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();
   
        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }
    /*
    * Determine which node the object belongs to. -1 means
    * object cannot completely fit within a child node and is part
    * of the parent node
    */
    private int getIndex(Actor actor) {
        int index = -1;
        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);
        // Object can completely fit within the top quadrants
        boolean topQuadrant = (actor.getLocation().getPixelY() < horizontalMidpoint && actor.getLocation().getPixelY() + actor.getRadius() < horizontalMidpoint);
        // Object can completely fit within the bottom quadrants
        boolean bottomQuadrant = (actor.getLocation().getPixelY() > horizontalMidpoint);

        // Object can completely fit within the left quadrants
        if (actor.getLocation().getPixelX() < verticalMidpoint && actor.getLocation().getPixelX() + actor.getRadius() < verticalMidpoint) {
            if (topQuadrant) {
              index = 1;
            }
            else if (bottomQuadrant) {
              index = 2;
            }
        }
        // Object can completely fit within the right quadrants
        else if (actor.getLocation().getPixelX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }
        return index;
    }
   /*
    * Insert the object into the quadtree. If the node
    * exceeds the capacity, it will split and add all
    * objects to their corresponding nodes.
    */
    public void insert(Actor actor) {
        if (nodes[0] != null) {
            int index = getIndex(actor);

            if (index != -1) {
              nodes[index].insert(actor);

              return;
            }
       }
       objects.add(actor);
       if (objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
            if (nodes[0] == null) { 
                split(); 
            }
            int i = 0;
            while (i < objects.size()) {
                int index = getIndex(objects.get(i));
                if (index != -1) {
                    nodes[index].insert(objects.remove(i));
                }
                else {
                    i++;
                }
            }
        }
    }
   /*
    * Return all objects that could collide with the given object
    */
    public List retrieve(List returnObjects, Actor actor) {
        int index = getIndex(actor);
        if (index != -1 && nodes[0] != null) {
            nodes[index].retrieve(returnObjects, actor);
        }
        returnObjects.addAll(objects);
        return returnObjects;
    }
}
