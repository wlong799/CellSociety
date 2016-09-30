package xmlparser;

import cellsociety_team13.GameParameter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.applet.Main;

import java.util.*;


/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store CellSociety game information.
 */
class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;
    private static final String MAIN_SECTION = "MAIN";
    private static final String PARAMETER_SECTION = "PARAMETER";
    private static final String CELL_TYPE_SECTION = "CELLTYPE";
    private static final String GRID_SECTION = "GRID";
    private static final String LOCATION_SECTION = "LOCATION";

    private String currentSection;
    private Parser currentParser;
    private Map<String, Parser> parserMap;
    private MainInfoParser mainInfoParser;
    private ParameterParser parameterParser;
    private CellTypeParser cellTypeParser;
    private GridParser gridParser;

    GameInfoHandler() {
        currentSection = null;
        currentParser = null;
        initializeParsers();
    }

    private void initializeParsers() {
        parserMap = new HashMap<>();
        mainInfoParser = new MainInfoParser();
        parameterParser = new ParameterParser();
        cellTypeParser = new CellTypeParser();
        gridParser = new GridParser();

        parserMap.put(MAIN_SECTION, mainInfoParser);
        parserMap.put(PARAMETER_SECTION, parameterParser);
        parserMap.put(CELL_TYPE_SECTION, cellTypeParser);
        parserMap.put(GRID_SECTION, gridParser);
        parserMap.put(LOCATION_SECTION, gridParser.getLocationParser());
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        String section = qName.toUpperCase();
        updateCurrentSection(section, ADD);
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        String section = qName.toUpperCase();
        updateCurrentSection(section, REMOVE);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentParser == null || currentSection == null || length == 0) {
            return;
        }
        String information = new String(ch, start, length);
        currentParser.parseInfo(currentSection, information);
    }

    private void updateCurrentSection(String sectionName, int operation) {
        if (operation == ADD) {
            currentSection = sectionName;
            if (parserMap.containsKey(sectionName)) {
                currentParser = parserMap.get(sectionName);
                currentParser.reset();
            }
        } else if (operation == REMOVE) {
            currentSection = null;
            if (parserMap.containsKey(sectionName)) {
                currentParser.update();
                currentParser = null;
            }
        }
    }

    String getMainInfo(String mainInfoName) {
        return mainInfoParser.getMainInfo(mainInfoName);
    }

    List<GameParameter> getGameParameters() {
        return parameterParser.getGameParameterList();
    }

    int getGridWidth() {
        return gridParser.getGridWidth();
    }

    int getGridHeight() {
        return gridParser.getGridHeight();
    }

    List<String> getInitialCellTypeLocations() {
        List<Integer> idLocations = gridParser.getInitialCellTypeIDLocations();
        List<String> result = new ArrayList<>();
        for (int id : idLocations) {
            result.add(cellTypeParser.getCellType(id));
        }
        return result;
    }
}
