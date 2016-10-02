package rule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cellsociety_team13.BackgroundCell;
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
    protected List<BackgroundCell> myBGNeighbours;


    abstract void evaluateCell(Cell myCell, CellGrid myGrid);

    public void initialize(CellGrid myGrid, List<GameParameter> initialParameters) {
    	/*for (int i = 0; i < myGrid.getGridHeight(); i++) {
            for (int j = 0; j < myGrid.getGridWidth(); j++) {
                setStatesInMap(myGrid.getCell(i, j));
                setBGStatesInMap(myGrid.getBGCell(i, j));
                setColor(myGrid.getCell(i, j), myGrid);
            }
        }*/
    	for (Cell cell : myGrid.getCells()) {
            setStatesInMap(cell);
            setBGStatesInMap(myGrid.getBGCellofCell(cell));
            setColor(cell, myGrid);
        }
    	initializeParameters(initialParameters);
    }

    public abstract void setColor(Cell myCell, CellGrid myGrid);

    abstract void setStatesInMap(Cell myCell);
    
    abstract void setBGStatesInMap(BackgroundCell myBGCell);

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
