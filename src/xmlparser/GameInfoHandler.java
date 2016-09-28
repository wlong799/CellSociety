package xmlparser;

import cellsociety_team13.GameParameter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;


/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store CellSociety game information.
 */
class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;

    private Stack<String> elementStack;
    private String nextParameterName;
    private int[] nextParameterVals;
    private Map<Integer, String> cellTypeMap;
    private int nextCellTypeID;
    private String nextCellTypeName;

    private Map<String, String> metadataMap;
    private List<GameParameter> gameParameterList;

    private List<String> initialCellTypes;
    private int gridWidth, gridHeight;
    private String nextCellType;
    private int nextCellRow, nextCellCol;

    GameInfoHandler() {
        elementStack = new Stack<>();
        nextParameterName = null;
        nextParameterVals = new int[]{-1, -1, -1};
        nextCellTypeID = -1;
        nextCellTypeName = null;

        metadataMap = new HashMap<>();
        gameParameterList = new ArrayList<>();
        cellTypeMap = new HashMap<>();
        initialCellTypes = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        updateCurrentSection(qName, ADD);
        if (qName.equalsIgnoreCase("PARAMETER")) {
            nextParameterName = null;
            nextParameterVals = new int[]{-1, -1, -1};
        } else if (qName.equalsIgnoreCase("CELLTYPE")) {
            nextCellTypeID = -1;
            nextCellTypeName = null;
        }
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        updateCurrentSection(qName, REMOVE);
        if (qName.equalsIgnoreCase("PARAMETER")) {
            addParameter();
        } else if (qName.equalsIgnoreCase("CELLTYPE")) {
            addCellType();
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String information = new String(ch, start, length);

        if (elementStack.contains("MAIN")) {
            parseMetadata(information);
        } else if (elementStack.contains("PARAMETER")) {
            parseParameter(information);
        } else if (elementStack.contains("CELLTYPE")) {
            parseCellType(information);
        } else if (elementStack.contains("GRID")) {
            getGridInfo(information);
        }
    }

    private void updateCurrentSection(String sectionName, int operation) {
        if (operation == ADD) {
            elementStack.push(sectionName.toUpperCase());
        } else if (operation == REMOVE) {
            elementStack.pop();
        }
    }

    private void parseMetadata(String information) {
        String metadataName = elementStack.peek();
        metadataMap.put(metadataName, information);
    }

    private void parseParameter(String information) {
        if (elementStack.peek().equals("NAME")) {
            nextParameterName = information;
        } else if (elementStack.peek().equals("MIN")) {
            int val = Integer.parseInt(information);
            nextParameterVals[0] = val;
        } else if (elementStack.peek().equals("MAX")) {
            int val = Integer.parseInt(information);
            nextParameterVals[1] = val;
        } else if (elementStack.peek().equals("CURRENT")) {
            int val = Integer.parseInt(information);
            nextParameterVals[2] = val;
        }
    }

    private void addParameter() {
        String name = nextParameterName;
        int min = nextParameterVals[0];
        int max = nextParameterVals[1];
        int current = nextParameterVals[2];
        if (name == null) {
            return;
        }
        if (min == -1 || max == -1 || min > max) {
            return;
        }
        if (current != -1 && current >= min && current <= max) {
            gameParameterList.add(new GameParameter(name, min, max, current));
        } else {
            gameParameterList.add(new GameParameter(name, min, max));
        }
    }

    private void parseCellType(String information) {
        if (elementStack.peek().equals("ID")) {
            int id = Integer.parseInt(information);
            nextCellTypeID = id;
        } else if (elementStack.peek().equals("NAME")) {
            nextCellTypeName = information;
        }
    }

    private void addCellType() {
        if (nextCellTypeID == -1 || nextCellTypeName == null) {

            return;
        }
        if (cellTypeMap.containsKey(nextCellTypeID)) {
            return;
        }
        cellTypeMap.put(nextCellTypeID, nextCellTypeName);
    }

    private void getGridInfo(String information) {
        if (elementStack.peek().equals("WIDTH")) {
            int width = Integer.parseInt(information);
            gridWidth = width;
        } else if (elementStack.peek().equals("HEIGHT")) {
            int height = Integer.parseInt(information);
            gridHeight = height;
        } else if (elementStack.peek().equals("DEFAULTID")) {
            int defaultID = Integer.parseInt(information);
            String defaultCellType = cellTypeMap.get(defaultID);
            for (int i = 0; i < gridHeight * gridWidth; i++) {
                initialCellTypes.add(defaultCellType);
            }
        } else if (elementStack.contains("CELL")) {
            addCell(information);
        }
    }

    private void addCell(String information) {
        if (elementStack.peek().equals("ID")) {
            int id = Integer.parseInt(information);
            String cellType = cellTypeMap.get(id);
            nextCellType = cellType;
        } else if (elementStack.peek().equals("ROW")) {
            int row = Integer.parseInt(information);
            nextCellRow = row;
        } else if (elementStack.peek().equals("COL")) {
            int col = Integer.parseInt(information);
            nextCellCol = col;
            int pos = nextCellRow * gridWidth + nextCellCol;
            initialCellTypes.add(pos, nextCellType);
        }
    }

    String getMetadata(String metadataName) {
        return metadataMap.get(metadataName);
    }

    List<GameParameter> getGameParameters() {
        return gameParameterList;
    }

    int getGridWidth() {
        return gridWidth;
    }

    int getGridHeight() {
        return gridHeight;
    }

    List<String> getInitialCellTypes() {
        return initialCellTypes;
    }
}
