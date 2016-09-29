package xmlparser;

public interface Parser {

    public abstract void addInfo(String infoName, String infoValue);

    public abstract String getInfo(String infoName);

}
