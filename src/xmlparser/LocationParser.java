package xmlparser;

import cellsociety_team13.AppResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parses information on initial cell locations from the XML document. Locations
 * can either be specified based on manual row, col locations, or by percentage
 * fill of the entire grid.
 */
public class LocationParser implements Parser {
    private int nextCellRow, nextCellCol, nextCellID, nextCellPercent;
    private int currentProbabilityPosition;
    private List<Integer> probabilities;

    private boolean initialized;
    private List<Integer> initialCellTypeIDLocations;
    private int gridWidth, gridHeight;
    private String fillMethod;

    public LocationParser() {
        reset();
        initialized = false;
        initialCellTypeIDLocations = null;

        gridWidth = -1;
        gridHeight = -1;
        fillMethod = null;

        currentProbabilityPosition = 0;
        probabilities = null;
    }

    /**
     * Location parser must be initialized using information from a grid parser before it can correctly
     * interpret the data it receives.
     * @param cellTypeIDLocations is list to store cell positions in.
     * @param gWidth is width of the grid.
     * @param gHeight is height of the grid.
     * @param method is method to use to set locations (manually or percent-based).
     */
    public void initializeGridInfo(List<Integer> cellTypeIDLocations, int gWidth, int gHeight, String method) {
        if (initialized) {
            return;
        }
        initialized = true;
        initialCellTypeIDLocations = cellTypeIDLocations;
        probabilities = new ArrayList<>();
        for (int i = 0; i < cellTypeIDLocations.size(); i++) {
            probabilities.add((int) (Math.random() * 100));
        }
        gridWidth = gWidth;
        gridHeight = gHeight;
        fillMethod = method;
    }

    @Override
    public void reset() {
        nextCellCol = -1;
        nextCellRow = -1;
        nextCellID = -1;
        nextCellPercent = -1;
    }

    @Override
    public void update() throws XMLGameInfoException {
        if (fillMethod.equals(AppResources.XML_LOCATION_MANUAL_METHOD.getResource())) {
            updateManual();
        } else if (fillMethod.equals(AppResources.XML_LOCATION_PERCENTAGE_METHOD.getResource())) {
            updatePercentage();
        } else {
            throw new XMLGameInfoException("Fill method not specified");
        }
    }

    private void updateManual() throws XMLGameInfoException {
        if (!initialized || nextCellRow == -1 || nextCellCol == -1 || nextCellID == -1) {
            throw new XMLGameInfoException("Manual cell parameters not provided");
        }
        int pos = gridWidth * nextCellRow + nextCellCol;
        initialCellTypeIDLocations.remove(pos);
        initialCellTypeIDLocations.add(pos, nextCellID);
    }

    private void updatePercentage() throws XMLGameInfoException {
        if (!initialized || nextCellPercent == -1 || nextCellID == -1) {
            throw new XMLGameInfoException("Percentage fill parameters not provided");
        }
        int nextProbabilityPosition = currentProbabilityPosition + nextCellPercent;
        for (int i = 0; i < initialCellTypeIDLocations.size(); i++) {
            int prob = probabilities.get(i);
            if (prob >= currentProbabilityPosition && prob < nextProbabilityPosition) {
                initialCellTypeIDLocations.remove(i);
                initialCellTypeIDLocations.add(i, nextCellID);
            }
        }
        currentProbabilityPosition = nextProbabilityPosition;
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(AppResources.XML_LOCATION_ID.getResource())) {
            nextCellID = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_LOCATION_ROW.getResource())) {
            nextCellRow = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_LOCATION_COL.getResource())) {
            nextCellCol = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_LOCATION_PERCENT.getResource())) {
            nextCellPercent = Integer.parseInt(infoValue);
        }
    }
}
