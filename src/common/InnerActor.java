/*
 * Copyright (C) 2017 dr4ur
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General License for more details.
 *
 * You should have received a copy of the GNU General License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package common;

/**
 *
 * @author dr4ur
 */
class InnerActor {
    
    private final int r, i, h, f, m;
    private final Location l;
    private final Team t;
    private final String ty, s;

    InnerActor(int gameRound, Actor actor) {
        this.r = gameRound;
        this.i = actor.getID();
        this.h = discoverHealth(actor);
        this.l = actor.getLocation();
        this.t = actor.getTeam();
        this.f = discoverFuel(actor);
        this.m = discoverMineral(actor);
        this.ty = discoverType(actor);
        this.s = discoverSubtype(actor);
    }
    
    private int discoverHealth(Actor actor) {
        if(actor instanceof Unit || actor instanceof Environment) {
            return actor.getHealth();
        }
        else {
            return Integer.MAX_VALUE;
        }
    }
    private int discoverFuel(Actor actor) {
        if(actor instanceof Unit) {
            return ((Unit) actor).getFuel();
        }
        else {
            return Integer.MAX_VALUE;
        }
    }
    private int discoverMineral(Actor actor) {
        if(actor instanceof Environment) {
            return ((Environment) actor).getMineralCount();
        }
        else {
            return Integer.MAX_VALUE;
        }
    }
    private String discoverType(Actor actor) {
        if(actor instanceof Unit) {
            return "Unit";
        }
        else if(actor instanceof Weapon) {
            return "Weapon";
        }
        else if(actor instanceof Environment) {
            return "Environment";
        }
        return "Error in discoverType()";
    }
    private String discoverSubtype(Actor actor) {
        if(actor instanceof Unit) {
            return ((Unit) actor).getType().toString();
        }
        else if(actor instanceof Weapon) {
            return ((Weapon) actor).getType().toString();
        }
        else if(actor instanceof Environment) {
            return ((Environment) actor).getType().toString();
        }
        return "Error in discoverSubtype()";
    }

    int getR() {
        return r;
    }

    int getI() {
        return i;
    }

    int getH() {
        return h;
    }

    int getF() {
        return f;
    }

    int getM() {
        return m;
    }

    Location getL() {
        return l;
    }

    Team getT() {
        return t;
    }

    String getTy() {
        return ty;
    }

    String getS() {
        return s;
    }

}
