package xmlparser;

import cellsociety_team13.AppResources;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses basic information about the initial setup of the grid. Also passes this
 * information to a LocationParser to set the initial locations of Cells in the grid.
 */
public class GridParser implements Parser {
    private int gridWidth, gridHeight, defaultID;
    private LocationParser locationParser;
    private String fillMethod;
    private String tiling;
    private boolean toroidal;
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
        fillMethod = null;
        tiling = null;
        toroidal = false;
        initialCellTypeIDLocations = new ArrayList<>();
    }

    @Override
    public void update() throws XMLGameInfoException{
        if (gridWidth == -1 || gridHeight == -1 || defaultID == -1) {
            throw new XMLGameInfoException("Grid parameters not all provided.");
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
        } else if (infoName.equals(AppResources.XML_GRID_TILING.getResource())) {
            tiling = infoValue;
        } else if (infoName.equals(AppResources.XML_GRID_TOROIDAL.getResource())) {
            if (infoValue.equalsIgnoreCase("true")) {
                toroidal = true;
            }
        }
    }

    public int getGridWidth() throws XMLGameInfoException{
        if (gridWidth == -1) {
            throw new XMLGameInfoException("Grid width not specified");
        }
        return gridWidth;
    }

    public int getGridHeight()throws XMLGameInfoException {
        if (gridHeight == -1) {
            throw new XMLGameInfoException("Grid height not specified");
        }
        return gridHeight;
    }

    public String getTiling()throws XMLGameInfoException {
        if (tiling == null) {
            throw new XMLGameInfoException("Tiling method not provided");
        }
        return tiling;
    }

    public boolean isToroidal() {
        return toroidal;
    }

    public List<Integer> getInitialCellTypeIDLocations() {
        return initialCellTypeIDLocations;
    }

    public LocationParser getLocationParser() {
        return locationParser;
    }
}
