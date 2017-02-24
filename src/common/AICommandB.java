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
public class AICommandB {
    
    static AIController ac;
    
    public static void run(AIController ac) {
        
        AICommandB.ac = ac;
        
        System.out.println("\n"+ac.getType() + " " + ac.getTeam() + ac.getID());
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
    
    public static void runFighter() {
            ac.getCurrentLocation();
            ac.move(new Location(-100, -100));
    }
    
    public static void runSiege() {
           // Siege code here.
    }
    public static void runDestroyer() {
           // Destroyer code here.
    }
    public static void runCapital() {
           // Capital code here.
    }
    public static void runBuilder() {
           // Builder code here.
    }
    public static void runHarvester() {
           // Harvester code here.
    }
    public static void runRefueler() {
           // Refueler code here.
    }
    public static void runHomeStation() {
           ac.build(UnitType.FIGHTER, Direction.NORTH);
    }
    public static void runSmallDock() {
           // Small Dock code here.
    }
    public static void runLargeDock() {
           // Large Dock code here.
    }
    public static void runCapitalDock() {
           // Capital Dock code here.
    }
    public static void runMiningFacility() {
           // Mining Facility code here.
    }
    public static void runFuelStation() {
           // Fuel Station code here.
    }
    
}
