package xmlparser;

import java.util.ArrayList;
import java.util.List;

public class GridParser implements Parser {
    private static final String WIDTH_SECTION = "WIDTH";
    private static final String HEIGHT_SECTION = "HEIGHT";
    private static final String DEFAULT_TYPE_SECTION = "DEFAULTID";

    private int gridWidth, gridHeight, defaultID;
    private List<Integer> initialCellTypeIDLocations;

    public GridParser() {
        reset();
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
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(WIDTH_SECTION)) {
            gridWidth = Integer.parseInt(infoValue);
        } else if (infoName.equals(HEIGHT_SECTION)) {
            gridHeight = Integer.parseInt(infoValue);
        } else if (infoName.equals(DEFAULT_TYPE_SECTION)) {
            defaultID = Integer.parseInt(infoValue);
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
}
