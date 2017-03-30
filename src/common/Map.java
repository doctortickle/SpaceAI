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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dr4ur
 */
strictfp class Map {
    
    private String mapName;
    private double sizeFactor;
    private static double pixelToCoordinate, coordinateToPixel;
    private final List<Object[]> environmentList;
    private Location homeStationAStartPosition;
    private Location homeStationBStartPosition;
    private int startingMineralCount;
    private FileReader fr;
    private BufferedReader br;
    private final File mapFile;  

    Map(File mapFile) throws IOException {
        this.mapFile = mapFile;
        this.mapName = null;
        this.sizeFactor = 2;
        this.environmentList = new ArrayList<>();
        this.homeStationAStartPosition = null;
        this.homeStationBStartPosition = null;
        this.startingMineralCount = 0;
        Map.pixelToCoordinate = 1d/this.sizeFactor;
        Map.coordinateToPixel = this.sizeFactor/1d;       
        fileReader();
    }
    
    private void fileReader() throws FileNotFoundException, IOException {
        fr = new FileReader(mapFile);
        br = new BufferedReader(fr);
        String line;
        line = null;
        while ( (line = br.readLine()) != null) {
            String[] array = line.split(":");
            switch(array[0]) {
                case "mapName" : setMapName(array[1]); break;
                case "sizeFactor" : setSizeFactor(array[1]); break;
                case "environment" : setInitialEnvironment(array[1]); break;
                case "homeStationA" : setHomeStationAStartPosition(array[1]); break;
                case "homeStationB" : setHomeStationBStartPosition(array[1]); break;
                case "initialMineralCount" : setInitialMineralCount(array[1]); break;
            }
        } 
    }
    private void setMapName(String mapName) {
        this.mapName = mapName;
    }
    private void setSizeFactor(String sizeFactor) {
        this.sizeFactor = Integer.parseInt(sizeFactor);
        Map.pixelToCoordinate = 1d/this.sizeFactor;
        Map.coordinateToPixel = this.sizeFactor/1d;   
    }
    private void setInitialEnvironment(String environment) {
        EnvironmentType environmentType;
        double x;
        double y;
        
        String[] environmentParameters = environment.split("/");
        switch(environmentParameters[0]) {
            case "SMALL_ASTEROID" : environmentType = EnvironmentType.SMALL_ASTEROID; break;
            case "LARGE_ASTEROID" : environmentType = EnvironmentType.LARGE_ASTEROID; break;
            case "SMALL_METEOR" : environmentType = EnvironmentType.SMALL_METEOR; break;
            case "LARGE_METEOR" : environmentType = EnvironmentType.LARGE_METEOR; break;
            case "SMALL_PLANET" : environmentType = EnvironmentType.SMALL_PLANET; break;
            case "LARGE_PLANET" : environmentType = EnvironmentType.LARGE_PLANET; break;
            default : environmentType = null;
        }
        x = Double.parseDouble(environmentParameters[1]);
        y = Double.parseDouble(environmentParameters[2]);
        
        Object[] environmentInfo = {environmentType, x, y};
        environmentList.add(environmentInfo);
    }
    private void setHomeStationAStartPosition(String homeStationA) {
        String[] locationCoordinates = homeStationA.split("/");
        this.homeStationAStartPosition = new Location(
                Double.parseDouble(locationCoordinates[0]),
                Double.parseDouble(locationCoordinates[1])
        );
    }
    private void setHomeStationBStartPosition(String homeStationB) {
        String[] locationCoordinates = homeStationB.split("/");
        this.homeStationBStartPosition = new Location(
                Double.parseDouble(locationCoordinates[0]),
                Double.parseDouble(locationCoordinates[1])
        );
    }
    private void setInitialMineralCount(String mineralCount) {
        this.startingMineralCount = Integer.parseInt(mineralCount);
    }  

    String getMapName() {
        return mapName;
    }
    double getSizeFactor() {
        return sizeFactor;
    }
    static double getPixelToCoordinate() {
        return pixelToCoordinate;
    }
    static double getCoordinateToPixel() {
        return coordinateToPixel;
    }
    static double getMinXCoordinate() {
        return -((GameConstants.CENTER_WIDTH/2d)*getPixelToCoordinate());
    }// MOVE TO MAP 
    static double getMaxXCoordinate() {
        return (GameConstants.CENTER_WIDTH/2d)*getPixelToCoordinate();
    }// MOVE TO MAP 
    static double getMinYCoordinate() {
        return -((GameConstants.CENTER_HEIGHT/2d)*getPixelToCoordinate());
    }// MOVE TO MAP 
    static double getMaxYCoordinate() {
        return (GameConstants.CENTER_HEIGHT/2d)*getPixelToCoordinate();
    }// MOVE TO MAP 
    List<Object[]> getEnvironmentList() {
        return environmentList;
    }
    Location getHomeStationAStartPosition() {
        return homeStationAStartPosition;
    }
    Location getHomeStationBStartPosition() {
        return homeStationBStartPosition;
    }
    int getStartingMineralCount() {
        return startingMineralCount;
    }
}
