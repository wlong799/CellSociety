package xmlparser;

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

    private Map<String, String> metadataMap;

    private Map<String, Integer> parameterMap;
    private String nextParameterName;

    private Map<Integer, String> cellTypeMap;
    private int nextCellTypeID;

    private List<String> initialCellTypes;
    private int gridWidth, gridHeight;
    private String nextCellType;
    private int nextCellRow, nextCellCol;

    GameInfoHandler() {
        elementStack = new Stack<>();

        metadataMap = new HashMap<>();
        parameterMap = new HashMap<>();
        cellTypeMap = new HashMap<>();
        initialCellTypes = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        updateCurrentSection(qName, ADD);
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        updateCurrentSection(qName, REMOVE);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        String information = new String(ch, start, length);

        if (elementStack.contains("METADATA")) {
            parseMetadata(information);
        } else if (elementStack.contains("PARAMETER")) {
            getParameterInfo(information);
        } else if (elementStack.contains("CELLTYPE")) {
            getCellTypeInfo(information);
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

    private void getParameterInfo(String information) {
        if (elementStack.peek().equals("NAME")) {
            nextParameterName = information;
        } else if (elementStack.peek().equals("VALUE")) {
            int val = Integer.parseInt(information);
            parameterMap.put(nextParameterName, val);
        }
    }

    private void getCellTypeInfo(String information) {
        if (elementStack.peek().equals("ID")) {
            int id = Integer.parseInt(information);
            nextCellTypeID = id;
        } else if (elementStack.peek().equals("NAME")) {
            cellTypeMap.put(nextCellTypeID, information);
        }
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

    Map<String, Integer> getParameterMap() {
        return parameterMap;
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
