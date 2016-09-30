package xmlparser;

import java.util.List;

public class LocationParser implements Parser {
    private static final String ID_SECTION = "ID";
    private static final String ROW_SECTION = "ROW";
    private static final String COL_SECTION = "COL";

    private int nextCellRow, nextCellCol, nextCellID;

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
    }

    public void initializeGridInfo(List<Integer> cellTypeIDLocations, int gWidth, int gHeight, String method) {
        if (initialized) {
            return;
        }
        initialized = true;
        initialCellTypeIDLocations = cellTypeIDLocations;
        gridWidth = gWidth;
        gridHeight = gHeight;
        fillMethod = method;
    }

    @Override
    public void reset() {
        nextCellCol = -1;
        nextCellRow = -1;
        nextCellID = -1;
    }

    @Override
    public void update() {
        if (!initialized || nextCellRow == -1 || nextCellCol == -1 || nextCellID == -1) {
            return;
        }
        int pos = gridWidth * nextCellRow + nextCellCol;
        initialCellTypeIDLocations.remove(0);
        initialCellTypeIDLocations.add(pos, nextCellID);
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(ID_SECTION)) {
            nextCellID = Integer.parseInt(infoValue);
        } else if (infoName.equals(ROW_SECTION)) {
            nextCellRow = Integer.parseInt(infoValue);
        } else if (infoName.equals(COL_SECTION)) {
            nextCellCol = Integer.parseInt(infoValue);
        }
    }
}
