package xmlparser;

import cellsociety_team13.AppResources;
import cellsociety_team13.GameParameter;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses XML files containing initial Cell Society game information. Stores the
 * information from the file, to be easily accessible by other classes that need
 * it.
 */
public class GameInfoReader {
    private String filename;
    GameInfoHandler gameInfoHandler;

    public GameInfoReader(String fname) {
        filename = fname;
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
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_TITLE.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game title.");
            e.printStackTrace();
            return "NO TITLE";
        }
    }

    public String getRuleClassName() {
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_RULE.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game rule.");
            e.printStackTrace();
            return "GameOfLife";
        }
    }

    public String getAuthor() {
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_AUTHOR.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game author.");
            e.printStackTrace();
            return "NO AUTHOR";
        }
    }

    public List<GameParameter> getGameParameters() {
        return gameInfoHandler.getGameParameters();
    }

    public int getGridWidth() {
        try {
            return gameInfoHandler.getGridWidth();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML grid width.");
            e.printStackTrace();
            return 10;

        }
    }

    public int getGridHeight() {
        try {
            return gameInfoHandler.getGridHeight();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML grid height.");
            e.printStackTrace();
            return 10;
        }
    }

    public String getGridTiling() {
        try {
            return gameInfoHandler.getGridTiling();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing tiling method.");
            e.printStackTrace();
            return AppResources.XML_TILING_SQUARE.getResource();
        }
    }

    public boolean isToroidal() {
        return gameInfoHandler.isToroidal();
    }

    public List<String> getInitialCellTypeLocations() {
        try {
            return gameInfoHandler.getInitialCellTypeLocations();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when loading initial locations");
            e.printStackTrace();
            List<String> result = new ArrayList<>(getGridHeight() * getGridWidth());
            for (int i = 0; i < getGridWidth()*getGridHeight(); i++) {
                result.add("DEAD");
            }
            return result;
        }
    }
}
