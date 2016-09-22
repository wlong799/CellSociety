package xmlparser;

import cellsociety_team13.Cell;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store CellSociety game information.
 *
 * @author Will Long
 */
class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;
    private Stack<String> elementStack;

    private String title, rule, author;

    private Map<String, Integer> parameterMap;
    private String nextParameterName;

    private Map<Integer, String> cellTypeMap;
    private int nextCellTypeID;

    private Cell[][] cellGrid;
    private int gridWidth, gridHeight;
    private Cell nextCell;
    private int nextCellRow, nextCellCol;

    GameInfoHandler() {
        elementStack = new Stack<>();
        parameterMap = new HashMap<>();
        cellTypeMap = new HashMap<>();
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

        if (elementStack.contains("MAIN")) {
            getMainInfo(information);
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

    private void getMainInfo(String information) {
        if (elementStack.peek().equals("TITLE")) {
            title = information;
        } else if (elementStack.peek().equals("RULE")) {
            rule = information;
        } else if (elementStack.peek().equals("AUTHOR")) {
            author = information;
        }
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
            cellGrid = new Cell[gridHeight][gridWidth];
            for (int r = 0; r < gridHeight; r++) {
                for (int c = 0; c < gridWidth; c++) {
                    cellGrid[r][c] = new Cell(defaultCellType);
                }
            }
        } else if (elementStack.contains("CELL")) {
            addCell(information);
        }
    }

    private void addCell(String information) {
        if (elementStack.peek().equals("ID")) {
            int id = Integer.parseInt(information);
            String cellType = cellTypeMap.get(id);
            nextCell = new Cell(cellType);
        } else if (elementStack.peek().equals("ROW")) {
            int row = Integer.parseInt(information);
            nextCellRow = row;
        } else if (elementStack.peek().equals("COL")) {
            int col = Integer.parseInt(information);
            nextCellCol = col;
            cellGrid[nextCellRow][nextCellCol] = nextCell;
        }
    }

    String getTitle() {
        return title;
    }

    String getRule() {
        return rule;
    }

    String getAuthor() {
        return author;
    }

    Map<String, Integer> getParameterMap() {
        return parameterMap;
    }

    Cell[][] getCellGrid() {
        return cellGrid;
    }
}
