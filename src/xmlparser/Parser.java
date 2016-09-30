package xmlparser;

public interface Parser {

    public abstract void reset();

    public abstract void update();

    public abstract void parseInfo(String infoName, String infoValue);

}
