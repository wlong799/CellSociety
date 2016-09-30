package xmlparser;

import cellsociety_team13.AppResources;
import cellsociety_team13.GameParameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterParser implements Parser {
    private String nextParameterName;
    private int nextParameterMin, nextParameterMax, nextParameterVal;

    private List<GameParameter> gameParameterList;

    public ParameterParser() {
        reset();
        gameParameterList = new ArrayList<>();
    }

    @Override
    public void reset() {
        nextParameterName = null;
        nextParameterMin = -1;
        nextParameterMax = -1;
        nextParameterVal = -1;
    }

    @Override
    public void update() {
        if (nextParameterName == null) {
            return;
        }
        if (nextParameterMin == -1 || nextParameterMax == -1 || nextParameterMin > nextParameterMax) {
            return;
        }
        if (nextParameterVal != -1 && nextParameterVal >= nextParameterMin && nextParameterVal <= nextParameterMax) {
            gameParameterList.add(new GameParameter(nextParameterName, nextParameterMin, nextParameterMax, nextParameterVal));
        } else {
            gameParameterList.add(new GameParameter(nextParameterName, nextParameterMin, nextParameterMax));
        }
    }

    @Override
    public void parseInfo(String infoName, String infoValue) {
        if (infoName.equals(AppResources.XML_PARAMETER_NAME.getResource())) {
            nextParameterName = infoValue;
        } else if (infoName.equals(AppResources.XML_PARAMETER_MIN.getResource())) {
            nextParameterMin = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_PARAMETER_MAX.getResource())) {
            nextParameterMax = Integer.parseInt(infoValue);
        } else if (infoName.equals(AppResources.XML_PARAMETER_VAL.getResource())) {
            nextParameterVal = Integer.parseInt(infoValue);
        }
    }

    public List<GameParameter> getGameParameterList() {
        return gameParameterList;
    }
}
