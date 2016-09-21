package cellsociety_team13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xmlparser.GameInfoHandler;

public abstract class Rule {

	Map<String, Integer> cellTypeMap;
	Map<String, Integer> parameterMap;
	Cell myTopCell;
	Cell myBottomCell;
	Cell myRightCell;
	Cell myLeftCell;

	abstract void evaluateCell(Cell myCell, CellGrid myGrid);

	ArrayList<Cell> getNonDiagNeighbours(Cell myCell, CellGrid myGrid){
		ArrayList<Cell> nonDiagNeighbours = new ArrayList<Cell>();
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY()) == null) {
			myRightCell = myGrid.getCell(myCell.getX() + 1, myCell.getY());
			nonDiagNeighbours.add(myRightCell);
		}
		if (!myGrid.getCell(myCell.getX() - 1, myCell.getY()) == null) {
			nonDiagNeighbours.add(myGrid.getCell(myCell.getX() - 1, myCell.getY()));
		}
		if (!myGrid.getCell(myCell.getX(), myCell.getY() + 1) == null) {
			nonDiagNeighbours.add(myGrid.getCell(myCell.getX(), myCell.getY() + 1));
		}
		if (!myGrid.getCell(myCell.getX(), myCell.getY() - 1) == null) {
			nonDiagNeighbours.add(myGrid.getCell(myCell.getX(), myCell.getY() - 1));
		}
		return nonDiagNeighbours;
	}
	
	ArrayList<Cell> getNeighbours(Cell myCell, CellGrid myGrid) {
		ArrayList<Cell> myNeighbours = new ArrayList<Cell>(getNonDiagNeighbours(myCell, myGrid));
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY()+1) == null){
			myNeighbours.add(myGrid.getCell(myCell.getX() + 1, myCell.getY() +1) == null);
		}
		if (!myGrid.getCell(myCell.getX() + 1, myCell.getY()-1) == null){
			myNeighbours.add(myGrid.getCell(myCell.getX() + 1, myCell.getY() -1) == null);
		}
		if (!myGrid.getCell(myCell.getX() -1, myCell.getY()-1) == null){
			myNeighbours.add(myGrid.getCell(myCell.getX() - 1, myCell.getY() -1) == null);
		}		
		if (!myGrid.getCell(myCell.getX() - 1, myCell.getY()+1) == null){
			myNeighbours.add(myGrid.getCell(myCell.getX() - 1, myCell.getY() +1) == null);
		}
		return myNeighbours;
	}
	

	void getHashMaps() {
		cellTypeMap = GameInfoReader.getCellTypeMap();
		parameterMap = GameInfoReader.getParameterMap();
	}
	

}
