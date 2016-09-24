package rule;
import java.util.List;
import java.util.Map;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

/**
 * Rule is an abstract class from which all the models are derived from 
 * 
 * @author Lucia Martos
 */

public abstract class Rule {

	Map<String, Integer> cellTypeMap;
	Map<String, Integer> parameterMap;
	List<Cell> nonDiagNeighbours;
	List<Cell> myNeighbours; 

	abstract void evaluateCell(Cell myCell, CellGrid myGrid);

	public void initialize(CellGrid myGrid) {
		for (int i = 0; i < myGrid.getGridHeight(); i++) {
			for (int j = 0; j < myGrid.getGridWidth(); j++) {
				setColor(myGrid.getCell(i, j));
				setStatesInMap(myGrid.getCell(i, j));
			}
		}
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
	
	void addParameter(String parameter, int valueParam){
		parameterMap.put(parameter, valueParam);
	}

}
