package xmlparser;

import cellsociety_team13.AppResources;

import java.util.HashMap;
import java.util.Map;

public class CellTypeParser implements Parser {
    private Map<Integer, String> cellTypeMap;
    private int nextCellTypeID;
    private String nextCellTypeName;

    public CellTypeParser() {
        reset();
        cellTypeMap = new HashMap<>();
    }

    @Override
    public void reset() {
        nextCellTypeID = -1;
        nextCellTypeName = null;
    }

    @Override
    public void update() {
        if (nextCellTypeID == -1 || nextCellTypeName == null) {
            return;
        }
        if (cellTypeMap.containsKey(nextCellTypeID)) {
            return;
        }
        cellTypeMap.put(nextCellTypeID, nextCellTypeName);
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(AppResources.XML_CELLTYPE_ID.getResource())) {
            nextCellTypeID = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_CELLTYPE_NAME.getResource())) {
            nextCellTypeName = infoValue;
        }
    }

    public String getCellType(int id) {
        if (!cellTypeMap.containsKey(id)) {
            return null;
        }
        return cellTypeMap.get(id);
    }
}
