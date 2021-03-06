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
import common.Environment;
import common.UnitType;
import common.WeaponType;
import java.util.List;

/**
 *
 * @author Dylan Russell
 */
public class AICommandB {
    
    static AIController ac;
    
    public void run(AIController ac) {
        
        AICommandB.ac = ac;

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
            case HARVESTING_FACILITY: runHarvestingFacility(); break;
            case FUEL_STATION: runFuelStation(); break;
        }    
    }    
    // *********************************
    // ************ FIGHTER ************
    // *********************************
    private void runFighter() {
        // This code will be run every round.
        ac.getLocation();
        ac.move(Direction.getRandomDirection());
    }
    // *********************************
    // ************ SIEGE **************
    // *********************************
    private void runSiege() {
        // This code will be run every round.
    }
    // *********************************
    // *********** DESTROYER ***********
    // *********************************
    private void runDestroyer() {
        // This code will be run every round.
    }
    // *********************************
    // ************ CAPITAL ************
    // *********************************
    private void runCapital() {
        // This code will be run every round.
        ac.fire(WeaponType.LARGE_BOMB, ac.getLocation().directionTo(ac.getInitialHomeStationLocation(ac.getTeam().opponent())));
    }
    // *********************************
    // ************ BUILDER ************
    // *********************************
    int dockCount = 0;
    private void runBuilder() {
        // This code will be run every round.
        List<Environment> nearbyEnvironment = ac.senseEnvironment();
        if(nearbyEnvironment.size() > 0) {
            Environment environment = nearbyEnvironment.get(0);
            if(ac.canOrbit(environment)) {
                ac.orbitClockwise(environment);
                if(ac.canConstruct(UnitType.SMALL_DOCK, ac.getLocation().directionTo(environment.getLocation()).opposite()) ) {
                    ac.construct(UnitType.SMALL_DOCK, ac.getLocation().directionTo(environment.getLocation()).opposite());
                }
            }
            else {
                ac.move(ac.getLocation().directionTo(environment.getLocation()));
            }
            if(ac.isReadyToMove()) {
                ac.move(Direction.getRandomDirection());
            }
        }
        else {
            ac.move(Direction.getRandomDirection());
        }
    }
    
    // *********************************
    // ********** HARVESTER ************
    // *********************************
    private void runHarvester() {
        // This code will be run every round.
    }
    // *********************************
    // *********** REFUELER ************
    // *********************************
    private void runRefueler() {
        // This code will be run every round.
    } 
    // *********************************
    // ********* HOME STATION **********
    // *********************************
    private int builderCount;
    private void runHomeStation() {
        // This code will be run every round.
            if(ac.canConstruct(UnitType.BUILDER, Direction.SOUTH) && builderCount < 5) {
                ac.construct(UnitType.BUILDER, Direction.SOUTH);
                builderCount++;
            }
    }
    // *********************************
    // ********** SMALL DOCK ***********
    // *********************************
    private void runSmallDock() {
        // This code will be run every round.
    }
    // *********************************
    // ********* LARGE DOCK ************
    // *********************************
    private void runLargeDock() {
        // This code will be run every round.
    }
    // *********************************
    // ********* CAPITAL DOCK **********
    // *********************************
    private int capitalCount;
    private void runCapitalDock() {
        // This code will be run every round.
        if(capitalCount == 0) {
            ac.construct(UnitType.CAPITAL, Direction.NORTH);
        }
    }
    // *********************************
    // ******* MINING FACILITY *********
    // *********************************
    private void runHarvestingFacility() {
        // This code will be run every round.
    }
    // *********************************
    // ******** FUEL STATION ***********
    // *********************************
    private void runFuelStation() {
        // This code will be run every round.
    }  
}
