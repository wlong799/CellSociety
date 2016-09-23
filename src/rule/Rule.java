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
	Cell myTopCell;
	Cell myBottomCell;
	Cell myRightCell;
	Cell myLeftCell;
	ArrayList<Cell> nonDiagNeighbours;
	ArrayList<Cell> allNeighbours;

	abstract void evaluateCell(Cell myCell, CellGrid myGrid);

	void initialize(CellGrid myGrid) {
		for (int i = 0; i < myGrid.getWidth(); i++) {
			for (int j = 0; j < myGrid.getHeight(); j++) {
				setColor(myGrid.getCell(i, j));
				setStatesInMap(myGrid.getCell(i, j));

			}
		}
	}

	abstract void setColor(Cell myCell);

	abstract void setStatesInMap(Cell myCell);

	void evaluateGrid(CellGrid myGrid) {
		for (int i = 0; i < myGrid.getWidth(); i++) {
			for (int j = 0; j < myGrid.getHeight(); j++) {
				if (myGrid.getCell(i, j).getNextType() != null) {
					evaluateCell(myGrid.getCell(i, j), myGrid);
				}
			}
		}
	}

	void getNonDiagNeighbours(Cell myCell, CellGrid myGrid) {
		ArrayList<Cell> nonDiagNeighbours = new ArrayList<Cell>();
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY()) == null) {
			myRightCell = myGrid.getCell(myCell.getX() + 1, myCell.getY());
			nonDiagNeighbours.add(myRightCell);
		}
		if (!myGrid.getCell(myCell.getX() - 1, myCell.getY()) == null) {
			myBottomCell = myGrid.getCell(myCell.getX() - 1, myCell.getY());
			nonDiagNeighbours.add(myBottomCell);
		}
		if (!myGrid.getCell(myCell.getX(), myCell.getY() + 1) == null) {
			myRightCell = myGrid.getCell(myCell.getX(), myCell.getY() + 1);
			nonDiagNeighbours.add(myRightCell);
		}
		if (!myGrid.getCell(myCell.getX(), myCell.getY() - 1) == null) {
			myLeftCell = myGrid.getCell(myCell.getX(), myCell.getY() - 1);
			nonDiagNeighbours.add(myLeftCell);
		}
		this.nonDiagNeighbours = nonDiagNeighbours;
	}

	void getNeighbours(Cell myCell, CellGrid myGrid) {
		ArrayList<Cell> myNeighbours = new ArrayList<Cell>(nonDiagNeighbours);
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY() + 1) == null) {
			myNeighbours.add(myGrid.getCell(myCell.getX() + 1, myCell.getY() + 1) == null);
		}
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY() - 1) == null) {
			myNeighbours.add(myGrid.getCell(myCell.getX() + 1, myCell.getY() - 1) == null);
		}
		if (!myGrid.getCell(myCell.getX() - 1, myCell.getY() - 1) == null) {
			myNeighbours.add(myGrid.getCell(myCell.getX() - 1, myCell.getY() - 1) == null);
		}
		if (!myGrid.getCell(myCell.getX() - 1, myCell.getY() + 1) == null) {
			myNeighbours.add(myGrid.getCell(myCell.getX() - 1, myCell.getY() + 1) == null);
		}
		this.allNeighbours = myNeighbours;
	}

	void getHashMaps() {
		cellTypeMap = GameInfoReader.getCellTypeMap();
		parameterMap = GameInfoReader.getParameterMap();
	}

}
