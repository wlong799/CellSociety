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
 */
public class GameInfoReader {
    private String filename;
    GameInfoHandler gameInfoHandler;

    public GameInfoReader(String filename) {
        this.filename = filename;
        gameInfoHandler = new GameInfoHandler();
        readGameInfoFile();
    }

    private void readGameInfoFile() {
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

    public String getRuleClassName() {
        return gameInfoHandler.getRuleClassName();
    }

    public String getAuthor() {
        return gameInfoHandler.getAuthor();
    }

    public Map<String, Integer> getParameterMap() {
        return gameInfoHandler.getParameterMap();
    }

    public String[][] getCellTypeGrid() {
        return gameInfoHandler.getCellTypeGrid();
    }
}
