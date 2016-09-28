package rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import cellsociety_team13.GameParameter;

/**
 * Rule is an abstract class from which all the models are derived from
 *
 * @author Lucia Martos
 */

public abstract class Rule {
    protected Map<String, GameParameter> parameterMap;
    protected List<Cell> nonDiagNeighbours;
    protected List<Cell> myNeighbours;

    abstract void evaluateCell(Cell myCell, CellGrid myGrid);

    public void initialize(CellGrid myGrid, List<GameParameter> initialParameters) {
        for (int i = 0; i < myGrid.getGridHeight(); i++) {
            for (int j = 0; j < myGrid.getGridWidth(); j++) {
                setColor(myGrid.getCell(i, j));
                setStatesInMap(myGrid.getCell(i, j));
            }
        }
        initializeParameters(initialParameters);
    }

    public abstract void setColor(Cell myCell);

    abstract void setStatesInMap(Cell myCell);

    public void evaluateGrid(CellGrid myGrid) {
        for (int i = 0; i < myGrid.getGridHeight(); i++) {
            for (int j = 0; j < myGrid.getGridWidth(); j++) {
                if (myGrid.getCell(i, j).getNextType() == null) {
                    evaluateCell(myGrid.getCell(i, j), myGrid);
                }
            }
        }
    }

    protected void initializeParameters(List<GameParameter> initialParameters) {
        parameterMap = new HashMap<>();
        for (GameParameter parameter : initialParameters) {
            String parameterName = parameter.getName();
            parameterMap.put(parameterName, parameter);
        }
    }

    public void setParameter(String parameterName, int value) {
        parameterMap.get(parameterName).setCurrentVal(value);
    }

    protected int getParameter(String parameterName) {
        return parameterMap.get(parameterName).getCurrentVal();
    }
}
