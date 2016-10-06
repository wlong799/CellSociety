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
 * it. Basically just tells GameInfoHandler to read the specified file, and then
 * provides the public interface that other classes will access the XML info from.
 */
public class GameInfoReader {
    private String filename;
    GameInfoHandler gameInfoHandler;

    /**
     * Creates a GameInfoReader object and parses the file specified by the given
     * filename. At completion, all information should be stored and ready to be
     * accessed.
     * @param fname is filename of an XML document containing game info.
     */
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

    /**
     * Returns the title of the current game.
     * @return XML-specified title or NO TITLE if an error occurs.
     */
    public String getTitle() {
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_TITLE.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game title.");
            e.printStackTrace();
            return "NO TITLE";
        }
    }

    /**
     * Returns the name of the class of the Rule to be used for evaluating Cells in this game.
     * @return XML-specified Rule class or GameOfLife if error occurs.
     */
    public String getRuleClassName() {
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_RULE.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game rule.");
            e.printStackTrace();
            return "GameOfLife";
        }
    }

    /**
     * Returns name of author of current game.
     * @return XML-specified author, or NO AUTHOR if error occurs.
     */
    public String getAuthor() {
        try {
            return gameInfoHandler.getMainInfo(AppResources.XML_MAIN_AUTHOR.getResource());
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML game author.");
            e.printStackTrace();
            return "NO AUTHOR";
        }
    }

    /**
     * Returns a list of the parameters to be used in this game.
     * @return list of GameParameters.
     */
    public List<GameParameter> getGameParameters() {
        return gameInfoHandler.getGameParameters();
    }

    /**
     * Returns width of grid in game.
     * @return XML-specified width or 10 if error occurs.
     */
    public int getGridWidth() {
        try {
            return gameInfoHandler.getGridWidth();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML grid width.");
            e.printStackTrace();
            return 10;

        }
    }

    /**
     * Returns height of grid in game.
     * @return XML-specified height or 10 if error occurs.
     */
    public int getGridHeight() {
        try {
            return gameInfoHandler.getGridHeight();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing XML grid height.");
            e.printStackTrace();
            return 10;
        }
    }

    /**
     * Returns tiling method to use (e.g. Square, Hexagon).
     * @return XML-specified tiling
     */
    public String getGridTiling() {
        try {
            return gameInfoHandler.getGridTiling();
        } catch (XMLGameInfoException e) {
            System.out.println("Error when parsing tiling method.");
            e.printStackTrace();
            return AppResources.XML_TILING_SQUARE.getResource();
        }
    }

    /**
     * Returns whether grid should be toroidal (i.e. sides wrap around when considering neighbors).
     * @return boolean specifying whether or not grid is toroidal.
     */
    public boolean isToroidal() {
        return gameInfoHandler.isToroidal();
    }

    /**
     * Returns a list of Strings specifying the initial locations of the types of Cells in the grid.
     * This will be used by CellGrid when creating its initial state. Should be of size gridWidth * gridHeight.
     * @return List of strings, where each String is a name of a type of Cell in the initial grid.
     */
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
