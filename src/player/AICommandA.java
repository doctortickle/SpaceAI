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
package player;

import common.AIController;
import common.Direction;
import common.Location;
import common.Unit;
import common.UnitType;


/**
 *
 * @author Dylan Russell
 */
public class AICommandA {
    
    static AIController ac;
    
    public static void run(AIController ac) {
        
        AICommandA.ac = ac;
        
        switch(ac.getType()) {
            case FIGHTER : runFighter(); break;
            case SIEGE : runSiege(); break;
            case DESTROYER : runDestroyer(); break;
            case CAPITAL : runCapital(); break;
            case BUILDER : runBuilder(); break;
            case HARVESTER : runHarvester(); break;
            case REFUELER : runRefueler(); break;
            case HOME_STATION: runHomeStation(); break;
            case SMALL_DOCK: runSmallDock(); break;
            case LARGE_DOCK: runLargeDock(); break;
            case CAPITAL_DOCK: runCapitalDock(); break;
            case MINING_FACILITY: runMiningFacility(); break;
            case FUEL_STATION: runFuelStation(); break;
        }    
    }    
    // *********************************
    // ************ FIGHTER ************
    // *********************************
    static boolean reachedOne = false;
    static boolean reachedTwo = false;
    static boolean reachedThree = false;
    static boolean reachedFour = false;
    private static void runFighter() {
        // This code will be run every round.
        System.out.print("Start: " + ac.getLocation().getX() + ", " + ac.getLocation().getY());
        Location one = new Location(0,50);
        Location two = new Location(0,-50);
        Location three = new Location(-50,50);
        Location four = new Location(50,-50);
        if(ac.canMove(ac.getLocation().directionTo(one)) && !reachedOne) {
            ac.move(one);
            if(ac.getLocation().equals(one)){
                reachedOne = true;
            }
        }
        if(ac.canMove(ac.getLocation().directionTo(two)) && !reachedTwo) {
            ac.move(two);
            if(ac.getLocation().equals(two)){
                reachedTwo = true;
            }
        }
        if(ac.canMove(ac.getLocation().directionTo(three)) && !reachedThree) {
            ac.move(three);
            if(ac.getLocation().equals(three)){
                reachedThree = true;
            }
        }
        if(ac.canMove(ac.getLocation().directionTo(four)) && !reachedFour) {
            ac.move(four);
            if(ac.getLocation().equals(four)){
                reachedFour = true;
            }
        }
        System.out.print("/nEnd: " + ac.getLocation().getX() + ", " + ac.getLocation().getY());
        //ac.fire(WeaponType.SMALL_LASER,ac.getLocation().directionTo(ac.getInitialHomeStationLocation(ac.getTeam().opponent())));
    }
    // *********************************
    // ************ SIEGE **************
    // *********************************
    private static void runSiege() {
        // This code will be run every round.
    }
    // *********************************
    // *********** DESTROYER ***********
    // *********************************
    private static void runDestroyer() {
        // This code will be run every round.
    }
    // *********************************
    // ************ CAPITAL ************
    // *********************************
    private static void runCapital() {
        // This code will be run every round.
        ac.move(Direction.getRandom());
    }
    // *********************************
    // ************ BUILDER ************
    // *********************************
    private static void runBuilder() {
        // This code will be run every round.
        ac.move(Direction.getRandom());
        ac.construct(UnitType.CAPITAL_DOCK, Direction.SOUTH);
    }
    // *********************************
    // ********** HARVESTER ************
    // *********************************
    private static void runHarvester() {
        // This code will be run every round.
    }
    // *********************************
    // *********** REFUELER ************
    // *********************************
    private static void runRefueler() {
        // This code will be run every round.
    } 
    // *********************************
    // ********* HOME STATION **********
    // *********************************
    static int fighterCount = 0;
    private static void runHomeStation() {
        // This code will be run every round.
        if(fighterCount < 1) {
            if(ac.canBuildShip(UnitType.FIGHTER, Direction.SOUTH)) {
                ac.buildShip(UnitType.FIGHTER, Direction.SOUTH);
                fighterCount++;
            }
        }
    }
    // *********************************
    // ********** SMALL DOCK ***********
    // *********************************
    private static void runSmallDock() {
        // This code will be run every round.
    }
    // *********************************
    // ********* LARGE DOCK ************
    // *********************************
    private static void runLargeDock() {
        // This code will be run every round.
    }
    // *********************************
    // ********* CAPITAL DOCK **********
    // *********************************
    private static int capitalCount;
    private static void runCapitalDock() {
        // This code will be run every round.
        if(capitalCount == 0) {
            ac.buildShip(UnitType.CAPITAL, Direction.NORTH);
        }
    }
    // *********************************
    // ******* MINING FACILITY *********
    // *********************************
    private static void runMiningFacility() {
        // This code will be run every round.
    }
    // *********************************
    // ******** FUEL STATION ***********
    // *********************************
    private static void runFuelStation() {
        // This code will be run every round.
    }  
}
