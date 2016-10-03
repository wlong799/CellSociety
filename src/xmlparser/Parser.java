package xmlparser;

public interface Parser {

    public abstract void reset();

    public abstract void update() throws XMLGameInfoException;

    public abstract void parseInfo(String infoName, String infoValue) throws XMLGameInfoException;

}
