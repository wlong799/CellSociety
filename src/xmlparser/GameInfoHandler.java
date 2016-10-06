package xmlparser;

import cellsociety_team13.AppResources;
import cellsociety_team13.GameParameter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import sun.applet.Main;

import java.util.*;


/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store CellSociety game information. At each start element, end element,
 * or character event, it sends information to the correct Parser, depending
 * on the current section it is in. Due to the Parser interface, it doesn't
 * need to know how the various Parsers are actually implemented.
 */
class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;

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

    /**
     * Maps sections of the XML document to the Parser that should be called.
     */
    private void initializeParsers() {
        parserMap = new HashMap<>();
        mainInfoParser = new MainInfoParser();
        parameterParser = new ParameterParser();
        cellTypeParser = new CellTypeParser();
        gridParser = new GridParser();

        parserMap.put(AppResources.XML_MAIN.getResource(), mainInfoParser);
        parserMap.put(AppResources.XML_PARAMETER.getResource(), parameterParser);
        parserMap.put(AppResources.XML_CELLTYPE.getResource(), cellTypeParser);
        parserMap.put(AppResources.XML_GRID.getResource(), gridParser);
        parserMap.put(AppResources.XML_LOCATION.getResource(), gridParser.getLocationParser());
    }

    @Override
    /**
     * Sets the current section and tells the current parser to reset its
     * information if necessary.
     */
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        String section = qName.toUpperCase();
        updateCurrentSection(section, ADD);
    }

    @Override
    /**
     * Removes the current section, and triggers the current parser to update
     * its current values if necessary.
     */
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        String section = qName.toUpperCase();
        updateCurrentSection(section, REMOVE);
    }

    @Override
    /**
     * Sends information on the section to the appropriate parser.
     */
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
                try {
                    currentParser.update();
                } catch (XMLGameInfoException e) {
                    System.out.println("Error when updating parser" + currentParser);
                    e.printStackTrace();
                }
                currentParser = null;
            }
        }
    }

    String getMainInfo(String mainInfoName) throws XMLGameInfoException {
        return mainInfoParser.getMainInfo(mainInfoName);
    }

    List<GameParameter> getGameParameters() {
        return parameterParser.getGameParameterList();
    }

    int getGridWidth() throws XMLGameInfoException {
        return gridParser.getGridWidth();
    }

    int getGridHeight() throws XMLGameInfoException {
        return gridParser.getGridHeight();
    }

    String getGridTiling() throws XMLGameInfoException {
        return gridParser.getTiling();
    }

    boolean isToroidal() {
        return gridParser.isToroidal();
    }

    List<String> getInitialCellTypeLocations() throws XMLGameInfoException {
        List<Integer> idLocations = gridParser.getInitialCellTypeIDLocations();
        List<String> result = new ArrayList<>();
        for (int id : idLocations) {
            result.add(cellTypeParser.getCellType(id));
        }
        return result;
    }
}
