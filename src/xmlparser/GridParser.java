package xmlparser;

import cellsociety_team13.AppResources;

import java.util.ArrayList;
import java.util.List;

public class GridParser implements Parser {
    private int gridWidth, gridHeight, defaultID;
    private LocationParser locationParser;
    private String fillMethod;
    private List<Integer> initialCellTypeIDLocations;

    public GridParser() {
        reset();
        locationParser = new LocationParser();
    }

    @Override
    public void reset() {
        gridWidth = -1;
        gridHeight = -1;
        defaultID = -1;
        initialCellTypeIDLocations = new ArrayList<>();
    }

    @Override
    public void update() {
        if (gridWidth == -1 || gridHeight == -1 || defaultID == -1) {
            return;
        }
        for (int i = 0; i < gridHeight * gridWidth; i++) {
            initialCellTypeIDLocations.add(defaultID);
        }
        locationParser.initializeGridInfo(initialCellTypeIDLocations, gridWidth, gridHeight, fillMethod);
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(AppResources.XML_GRID_WIDTH.getResource())) {
            gridWidth = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_GRID_HEIGHT.getResource())) {
            gridHeight = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_GRID_DEFAULTID.getResource())) {
            defaultID = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_GRID_FILLMETHOD.getResource())) {
            fillMethod = infoValue;
        }
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public List<Integer> getInitialCellTypeIDLocations() {
        return initialCellTypeIDLocations;
    }

    public LocationParser getLocationParser() {
        return locationParser;
    }
}
