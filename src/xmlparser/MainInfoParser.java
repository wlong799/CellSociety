package xmlparser;

import java.util.HashMap;
import java.util.Map;

public class MainInfoParser implements Parser {

    private Map<String, String> mainInfoMap;

    public MainInfoParser() {
        mainInfoMap = new HashMap<>();
    }

    public void addInfo(String infoName, String infoValue) {
        mainInfoMap.put(infoName, infoValue);
    }

    public String getInfo(String infoName) {
        if (mainInfoMap.containsKey(infoName)) {
            return mainInfoMap.get(infoName);
        }
        return null;
    }
}
