package xmlparser;

import cellsociety_team13.AppResources;
import cellsociety_team13.GameParameter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.List;

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
        return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_TITLE.getResource());
    }

    public String getRuleClassName() {
        return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_RULE.getResource());
    }

    public String getAuthor() {
        return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_AUTHOR.getResource());
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

    public List<String> getInitialCellTypeLocations() {
        return gameInfoHandler.getInitialCellTypeLocations();
    }
}
