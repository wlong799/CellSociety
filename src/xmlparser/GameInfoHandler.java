package xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store Cell Society game information.
 *
 * @author Will Long
 */
public class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;
    private LinkedList<String> currentSection;

    private String title, rule, author;

    private Map<String, Integer> parameterMap;
    private String nextParameter;

    private Map<Integer, String> cellTypeMap;
    private int nextCellTypeID;

    private int[][] initialCellGrid;
    private int gridWidth, gridHeight;
    private int nextCellID, nextCellRow, nextCellCol;

    GameInfoHandler() {
        currentSection = new LinkedList<>();
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
        if (currentSection.size() == 0) {
            return;
        }
        String information = new String(ch, start, length);
        if (currentSection.getFirst().equals("MAIN")) {
            getMainInfo(information);
        } else if (currentSection.getFirst().equals("PARAMETER")) {
            getParameterInfo(information);
        } else if (currentSection.getFirst().equals("CELLTYPE")) {
            getCellTypeInfo(information);
        } else if (currentSection.getFirst().equals("GRID")) {
            getGridInfo(information);
        }
    }

    private void updateCurrentSection(String sectionName, int operation) {
        if (sectionName.equalsIgnoreCase("GAME")) {
            return;
        } else if (operation == ADD) {
            currentSection.addLast(sectionName.toUpperCase());
        } else if (operation == REMOVE) {
            currentSection.removeLast();
        }
    }

    private void getMainInfo(String information) {
        if (currentSection.getLast().equals("TITLE")) {
            title = information;
        } else if (currentSection.getLast().equals("RULE")) {
            rule = information;
        } else if (currentSection.getLast().equals("AUTHOR")) {
            author = information;
        }
    }

    private void getParameterInfo(String information) {
        if (currentSection.getLast().equals("NAME")) {
            nextParameter = information;
        } else if (currentSection.getLast().equals("VALUE")) {
            int val = Integer.parseInt(information);
            parameterMap.put(nextParameter, val);
        }
    }

    private void getCellTypeInfo(String information) {
        if (currentSection.getLast().equals("ID")) {
            int id = Integer.parseInt(information);
            nextCellTypeID = id;
        } else if (currentSection.getLast().equals("NAME")) {
            cellTypeMap.put(nextCellTypeID, information);
        }
    }

    private void getGridInfo(String information) {
        if (currentSection.getLast().equals("WIDTH")) {
            int width = Integer.parseInt(information);
            gridWidth = width;
        } else if (currentSection.getLast().equals("HEIGHT")) {
            int height = Integer.parseInt(information);
            gridHeight = height;
        } else if (currentSection.getLast().equals("DEFAULTID")) {
            int defaultID = Integer.parseInt(information);
            initialCellGrid = new int[gridHeight][gridWidth];
            for (int r = 0; r < gridHeight; r++) {
                for (int c = 0; c < gridWidth; c++) {
                    initialCellGrid[r][c] = defaultID;
                }
            }
        } else if (currentSection.contains("CELL")) {
            addCell(information);
        }
    }

    private void addCell(String information) {
        if (currentSection.getLast().equals("ID")) {
            int id = Integer.parseInt(information);
            nextCellID = id;
        } else if (currentSection.getLast().equals("ROW")) {
            int row = Integer.parseInt(information);
            nextCellRow = row;
        } else if (currentSection.getLast().equals("COL")) {
            int col = Integer.parseInt(information);
            nextCellCol = col;
            initialCellGrid[nextCellRow][nextCellCol] = nextCellID;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getRule() {
        return rule;
    }

    public String getAuthor() {
        return author;
    }

    public Map<String, Integer> getParameterMap() {
        return parameterMap;
    }

    public Map<Integer, String> getCellTypeMap() {
        return cellTypeMap;
    }

    public int[][] getInitialCellGrid() {
        return initialCellGrid;
    }
}
