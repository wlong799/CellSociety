package rule;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * Foraging Ants is a model to represent the phenomena associated with ant colony
 * foraging. The model works on the basis of two pheromones: home and food. An ant
 * not carrying food will follow the strongest food pheromones to find food, then whilst
 * carrying food will follow the strongest home pheromones to take food to the nest.
 * @author John Martin
 */

public class ForagingAnts extends Rule {
//	Currently using parameters:
//	FOOD = 1: ant has food, 0: ant does not have food
//	FOODSOURCE = 1: is food source, 0: isn't
//	HOMEPHERO = int value of home pheromone status
//	FOODPHERO = int value of food pheromone status
//	XOrientation = int (will be 1, 0 or -1)
//	YOrientation = int (will be 1, 0 or -1)
//	NEST = 1: is nest, 0: isn't
	
	
	
	private List<BackgroundCell> patchGrid;	
	public ForagingAnts(CellGrid myGrid){
		patchGrid = new ArrayList<BackgroundCell>();
		for (Cell cell : myGrid.copyCells()){
			patchGrid.add(new BackgroundCell());
		}
	}

	void evaluateCell(Cell myCell, CellGrid myGrid) { // Pseudo Ant-Forage
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		if (myCell.getCurrentState("FOOD") == 1){
			this.returnToNest(myCell, myGrid);
		} else {
			this.findFoodSource(myCell);
		}
		
	}

	private void returnToNest(Cell myCell, CellGrid myGrid) {
		int gridWidth = myGrid.getGridWidth();
		int myCellIndex = myCell.getMyRow() * gridWidth + myCell.getMyCol();
		int atFoodSource = patchGrid.get(myCellIndex).getCurrentBackgroundState("FOODSOURCE");
		if (atFoodSource == 1){
			setHomeOrientation(myCell);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState("XOrientation");
		int nextRow = myCell.getMyRow() + myCell.getCurrentState("YOrientation");
		Cell nextCell = myGrid.getCell(nextCol, nextRow);
		if (nextCell == null){
			setHomeOrientation(myCell);
		}
		if (nextCell != null){
			dropFoodPheromones(myCell);
//			Orientation ← heading to nextCell from current location
//			Move to nextCell
		}
		if (myCell.getCurrentState("NEST") == 1){
//			Drop food item
//			HasFoodItem ← FALSE
		}
	}
	
	private void findFoodSource(Cell myCell) {
	}
	
	private void setHomeOrientation(Cell myCell){
		Cell homeNeighbour = findFoodPheromones(myCell);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int homeCol = homeNeighbour.getMyCol(); int homeRow = homeNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setNextState("XOrientation", xDir);
		myCell.setNextState("YOrientation", yDir);
	}
	
	private Cell findHomePheromones(Cell myCell){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			if (testCell.getCurrentState("HOMEPHERO") > mostPheroNum){
				mostPheroCell = testCell;
			}
		}
		return mostPheroCell;
	}
	
	private Cell findFoodPheromones(Cell myCell){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			if (testCell.getCurrentState("FOODPHERO") > mostPheroNum){
				mostPheroCell = testCell;
			}
		}
		return mostPheroCell;
	}
	
	private void dropHomePheromones(Cell myCell) {
		int nextPhero = myCell.getCurrentState("HOMEPHERO") + 1;
		myCell.setNextState("HOMEPHERO", nextPhero);
	}
	
	private void dropFoodPheromones(Cell myCell) {
		int nextPhero = myCell.getCurrentState("FOODPHERO") + 1;
		myCell.setNextState("FOODPHERO", nextPhero);
	}
	
	private void LocSet(Cell myCell) {
		
	}
	
	int countNeigboursOfType(String type){
		int count = 0;
		for(Cell neighbour : myNeighbours){
			if(neighbour.getCurrentType().equals(type)){
				count ++;
			}
		}
		return count;
	}

	public void setColor(Cell myCell) {
		if (myCell.getCurrentType().equals("DEAD")) {
			myCell.setFill(Color.BLACK);
		} else if (myCell.getCurrentType().equals("LIVE")) {
			myCell.setFill(Color.WHITE);
		}
	}
	void setStatesInMap(Cell myCell) {	
	}	
}
