package xmlparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocationParser implements Parser {
    private static final String ID_SECTION = "ID";
    private static final String ROW_SECTION = "ROW";
    private static final String COL_SECTION = "COL";
    private static final String PERCENTAGE_SECTION = "PERCENT";
    private static final String MANUAL_METHOD = "manual";
    private static final String PERCENTAGE_METHOD = "percentage";

    private int nextCellRow, nextCellCol, nextCellID, nextCellPercent;
    private int currentProbabilityPosition;
    private List<Integer> probabilities;

    private boolean initialized;
    private List<Integer> initialCellTypeIDLocations;
    private int gridWidth, gridHeight;
    private String fillMethod;

    public LocationParser() {
        reset();
        initialized =false;
        initialCellTypeIDLocations = null;

        gridWidth = -1;
        gridHeight = -1;
        fillMethod = null;

        currentProbabilityPosition = 0;
        probabilities = null;
    }

    public void initializeGridInfo(List<Integer> cellTypeIDLocations, int gWidth, int gHeight, String method) {
        if (initialized) {
            return;
        }
        initialized = true;
        initialCellTypeIDLocations = cellTypeIDLocations;
        probabilities = new ArrayList<>();
        for (int i = 0; i < cellTypeIDLocations.size(); i++) {
            probabilities.add((int)(Math.random()*100));
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
    public void update() {
        if (fillMethod.equals(MANUAL_METHOD)) {
            updateManual();
        } else if (fillMethod.equals(PERCENTAGE_METHOD)) {
            updatePercentage();
        }
    }

    private void updateManual() {
        if (!initialized || nextCellRow == -1 || nextCellCol == -1 || nextCellID == -1) {
            return;
        }
        int pos = gridWidth * nextCellRow + nextCellCol;
        initialCellTypeIDLocations.remove(pos);
        initialCellTypeIDLocations.add(pos, nextCellID);
    }

    private void updatePercentage() {
        if (!initialized || nextCellPercent == -1 || nextCellID == -1) {
            return;
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
        if (infoName.equals(ID_SECTION)) {
            nextCellID = Integer.parseInt(infoValue);
        } else if (infoName.equals(ROW_SECTION)) {
            nextCellRow = Integer.parseInt(infoValue);
        } else if (infoName.equals(COL_SECTION)) {
            nextCellCol = Integer.parseInt(infoValue);
        } else if (infoName.equals(PERCENTAGE_SECTION)) {
            nextCellPercent = Integer.parseInt(infoValue);
        }
    }
}
