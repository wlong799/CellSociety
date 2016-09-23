package rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import xmlparser.GameInfoHandler;

public abstract class Rule {

	Map<String, Integer> cellTypeMap;
	Map<String, Integer> parameterMap;

	abstract void evaluateCell(Cell myCell, CellGrid myGrid);

	void initialize(CellGrid myGrid) {
		for (int i = 0; i < myGrid.getGridWidth(); i++) {
			for (int j = 0; j < myGrid.getGridHeight(); j++) {
				setColor(myGrid.getCell(i, j));
				setStatesInMap(myGrid.getCell(i, j));

			}
		}
	}

	abstract void setColor(Cell myCell);

	abstract void setStatesInMap(Cell myCell);

	public void evaluateGrid(CellGrid myGrid) {
		for (int i = 0; i < myGrid.getGridWidth(); i++) {
			for (int j = 0; j < myGrid.getGridHeight(); j++) {
				if (myGrid.getCell(i, j).getNextType() != null) {
					evaluateCell(myGrid.getCell(i, j), myGrid);
				}
			}
		}
	}

	void getHashMaps() {
		cellTypeMap = GameInfoReader.getCellTypeMap();
		parameterMap = GameInfoReader.getParameterMap();
	}

}
