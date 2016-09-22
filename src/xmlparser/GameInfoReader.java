package xmlparser;

import cellsociety_team13.Cell;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.Map;

/**
 * Parses XML files containing initial Cell Society game information. Stores the
 * information from the file, to be easily accessible by other classes that need
 * it.
 *
 * @author Will Long
 */
public class GameInfoReader {
    private String filename;
    GameInfoHandler gameInfoHandler;

    public GameInfoReader(String fname) {
        filename = fname;
        gameInfoHandler = new GameInfoHandler();
    }

    public void readGameInfoFile() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser;
        try {
            saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new File(filename), gameInfoHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return gameInfoHandler.getTitle();
    }

    public String getRule() {
        return gameInfoHandler.getRule();
    }

    public String getAuthor() {
        return gameInfoHandler.getAuthor();
    }

    public Map<String, Integer> getParameterMap() {
        return gameInfoHandler.getParameterMap();
    }

    public Cell[][] getCellGrid() {
        return gameInfoHandler.getCellGrid();
    }
}
