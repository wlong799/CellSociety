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
//	NESTFOOD: int count of food at nest

	void evaluateCell(Cell myCell, CellGrid myGrid) { // Pseudo Ant-Forage
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		BackgroundCell currentBGCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		if (myCell.getCurrentState("ANT") == 1 && myCell.getCurrentState("FOOD") == 1 && currentBGCell.getCurrentBGState("NEST") == 0){
			this.returnToNest(myCell, myGrid);
		} else if (myCell.getCurrentState("ANT") == 1 && myCell.getCurrentState("FOOD") == 1 && currentBGCell.getCurrentBGState("NEST") == 1){
			dropFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid);
		} else if(myCell.getCurrentState("ANT") == 1 && myCell.getCurrentState("FOOD") == 0 && currentBGCell.getCurrentBGState("FOOD") == 1){
			pickUpFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid); 
		} else if(myCell.getCurrentState("ANT") == 1){
			this.findFoodSource(myCell, myGrid); 
		}
	}

	private void returnToNest(Cell myCell, CellGrid myGrid) {
		int atFoodSource = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState("FOODSOURCE");
		if (atFoodSource == 1){
			setHomeOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState("XOrientation");
		int nextRow = myCell.getMyRow() + myCell.getCurrentState("YOrientation");
		Cell nextCell = myGrid.getCell(nextCol, nextRow); // At the moment just identifying next cell, not doing anything with it
		if (nextCell == null || nextCell.getNextState("ANT") != 0){
			setHomeOrientation(myCell, myGrid);
		}
		if (nextCell != null){
			dropFoodPheromones(myCell, myGrid);
			setNextCellStates(myCell, nextCell);
			// Set nextCells next states
//			Move to nextCell
		}
		if (myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState("NEST") == 1){
			dropFood(myCell, myGrid);
		}
	}
	
	private void findFoodSource(Cell myCell, CellGrid myGrid) {
		int atNest = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState("NEST");
		if (atNest == 1){
			setFoodOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState("XOrientation");
		int nextRow = myCell.getMyRow() + myCell.getCurrentState("YOrientation");
		Cell nextCell = myGrid.getCell(nextCol, nextRow); // At the moment just identifying next cell, not doing anything with it
		if (nextCell == null || nextCell.getNextState("ANT") != 0){
			setHomeOrientation(myCell, myGrid);
			nextCol = myCell.getMyCol() + myCell.getCurrentState("XOrientation");
			nextRow = myCell.getMyRow() + myCell.getCurrentState("YOrientation");
			nextCell = myGrid.getCell(nextCol, nextRow);
		}
		if (nextCell != null){
			dropHomePheromones(myCell, myGrid);
			setNextCellStates(myCell, nextCell);
		}
//		Drop-Home-Pheromones
//		Orientation ← heading to X from current location
//		Move to X
//		If ant located at food source
//		Pick up food item
//		HasFoodItem ← TRUE
	}
	
	private void setNextCellStates(Cell myCell, Cell nextCell){
		nextCell.setNextState("ANT", 1);
		nextCell.setNextState("FOOD", myCell.getCurrentState("FOOD"));
		nextCell.setNextState("XOrientation", myCell.getCurrentState("XOrientation"));
		nextCell.setNextState("YOrientation", myCell.getCurrentState("YOrientation"));
		
		myCell.setNextState("ANT", 0);
		myCell.setNextState("FOOD", 0);
		myCell.setNextState("XOrientation", 0);
		myCell.setNextState("YOrientation", 0);
	}
	
	private void setHomeOrientation(Cell myCell, CellGrid myGrid){
		Cell homeNeighbour = findHomePheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int homeCol = homeNeighbour.getMyCol(); int homeRow = homeNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setNextState("XOrientation", xDir);
		myCell.setNextState("YOrientation", yDir);
	}
	
	private void setFoodOrientation(Cell myCell, CellGrid myGrid){
		Cell foodNeighbour = findFoodPheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int homeCol = foodNeighbour.getMyCol(); int homeRow = foodNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setNextState("XOrientation", xDir);
		myCell.setNextState("YOrientation", yDir);
	}
	
	private Cell findHomePheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
			if (testBG.getCurrentBGState("HOMEPHERO") >= mostPheroNum && testCell.getNextState("ANT") == 0){
				mostPheroCell = testCell;
			}
		}
		return mostPheroCell;
	}
	
	private Cell findFoodPheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
			if (testBG.getCurrentBGState("FOODPHERO") >= mostPheroNum && testCell.getNextState("ANT") == 0){
				mostPheroCell = testCell;
			}
		}
		return mostPheroCell;
	}
	
	private void dropHomePheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = bgCell.getCurrentBGState("HOMEPHERO") + 1;
		bgCell.setNextBGState("HOMEPHERO", nextPhero);
	}
	
	private void dropFoodPheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = bgCell.getCurrentBGState("FOODPHERO") + 1;
		bgCell.setNextBGState("FOODPHERO", nextPhero);
	}
	
	private void LocSet(Cell myCell) {
		
	}
	
	private void dropFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState("NESTFOOD", bgCell.getCurrentBGState("NESTFOOD") + 1);
		myCell.setNextState("FOOD", 0);
	}
	
	private void pickUpFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState("FOOD", 0);
		myCell.setNextState("FOOD", 1);
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

	public void setColor(Cell myCell, CellGrid myGrid) {
		if (myCell.getCurrentType().equals("ANT")) {
			myCell.setFill(Color.BROWN);
		} else if (myCell.getCurrentType().equals("NEST")) {
			myCell.setFill(Color.GRAY);
		} else if (myCell.getCurrentType().equals("FOOD")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("PATCH") && myCell.getCurrentState("FOODPHERO") > 3) {
			myCell.setFill(Color.BLUE);
		} else if (myCell.getCurrentType().equals("PATCH")) {
			myCell.setFill(Color.GREEN);
		}
	}
	
	void setStatesInMap(Cell myCell) {	
	}
}
