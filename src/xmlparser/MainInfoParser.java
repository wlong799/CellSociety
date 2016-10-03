package xmlparser;

import java.util.HashMap;
import java.util.Map;

public class MainInfoParser implements Parser {

    private Map<String, String> mainInfoMap;

    public MainInfoParser() {
        mainInfoMap = new HashMap<>();
    }

    @Override
    public void reset() {
        return;
    }

    @Override
    public void update() {
        return;
    }

    public void parseInfo(String infoName, String infoValue) {
        mainInfoMap.put(infoName, infoValue);
    }

    public String getMainInfo(String infoName) throws XMLGameInfoException{
        if (mainInfoMap.containsKey(infoName)) {
            return mainInfoMap.get(infoName);
        }
        else {
            throw new XMLGameInfoException("Information not provided in main");
        }
    }
}
