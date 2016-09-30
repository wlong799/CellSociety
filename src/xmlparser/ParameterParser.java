package xmlparser;

import cellsociety_team13.GameParameter;

import java.util.ArrayList;
import java.util.List;

public class ParameterParser implements Parser {
    private static final String NAME_SECTION = "NAME";
    private static final String MIN_SECTION = "MIN";
    private static final String MAX_SECTION = "MAX";
    private static final String VAL_SECTION = "CURRENT";

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
        if (infoName.equals(NAME_SECTION)) {
            nextParameterName = infoValue;
        } else if (infoName.equals(MIN_SECTION)) {
            nextParameterMin = Integer.parseInt(infoValue);
        } else if (infoName.equals(MAX_SECTION)) {
            nextParameterMax = Integer.parseInt(infoValue);
        } else if (infoName.equals(VAL_SECTION)) {
            nextParameterVal = Integer.parseInt(infoValue);
        }
    }

    public List<GameParameter> getGameParameterList() {
        return gameParameterList;
    }
}
