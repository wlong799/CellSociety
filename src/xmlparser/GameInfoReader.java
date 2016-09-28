package xmlparser;

import cellsociety_team13.Cell;
import cellsociety_team13.GameParameter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.List;
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
        return gameInfoHandler.getMetadata("TITLE");
    }

    public String getRuleClassName() {
        return gameInfoHandler.getMetadata("RULE");
    }

    public String getAuthor() {
        return gameInfoHandler.getMetadata("AUTHOR");
    }

    public List<GameParameter> getGameParameters() {
        return gameInfoHandler.getGameParameters();
    }

    public int getGridWidth() {
        return gameInfoHandler.getGridWidth();
    }

    public int getGridHeight() {
        return gameInfoHandler.getGridHeight();
    }

    public List<String> getInitialCellTypes() {
        return gameInfoHandler.getInitialCellTypes();
    }
}
