package xmlparser;

/**
 * Parser provides a generic interface for a class to parse a section of an XML
 * document containing information on a CellSociety game. Adding a new section
 * to an XML document just requires a new class that implements this interface.
 * Providing a common interface allows for GameInfoHandler to easily pass
 * information where it needs to go, in a generic way.
 */
public interface Parser {

    /**
     * Resets the variables within a parser in preparation to store new information.
     */
    public abstract void reset();

    /**
     * Uses the information that has been parsed to store new variables or information
     * about the game.
     * @throws XMLGameInfoException if all necessary variables haven't been parsed.
     */
    public abstract void update() throws XMLGameInfoException;

    /**
     * Parses information and stores it in preparation for creating new game variables.
     * @param infoName is the name/section of the information being parsed.
     * @param infoValue is the value specified for the information.
     */
    public abstract void parseInfo(String infoName, String infoValue);

}
