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
    private static void runFighter() {
        // This code will be run every round.
        ac.getCurrentLocation();
        ac.move(Direction.getRandom());
        ac.fire(WeaponType.SMALL_LASER,Direction.SOUTH);
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
        int dockCount = 0;
        if(dockCount == 0) {
            ac.build(UnitType.CAPITAL_DOCK, Direction.EAST);
            dockCount++;
        }
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
        if(fighterCount < 5) {
            if(ac.canBuild(UnitType.FIGHTER, Direction.SOUTH)) {
                ac.build(UnitType.FIGHTER, Direction.SOUTH);
                fighterCount++;
                System.out.println(fighterCount);
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
            ac.build(UnitType.CAPITAL, Direction.NORTH);
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
