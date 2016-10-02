package rule;

import java.util.ArrayList;
import java.util.List;

import cellsociety_team13.AppResources;
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
	private static final String FOOD = AppResources.FA_FOOD.getResource(); //	FOOD = 1: ant has food, 0: ant does not have food
	private static final String FOODSOURCE = AppResources.FA_FOODSOURCE.getResource(); //	FOODSOURCE = 1: is food source, 0: isn't
	private static final String ANT = AppResources.FA_ANT.getResource(); //	FOODSOURCE = 1: is food source, 0: isn't
	private static final String HOMEPHERO = AppResources.FA_HOMEPHERO.getResource(); //	HOMEPHERO = int value of home pheromone status
	private static final String FOODPHERO = AppResources.FA_FOODPHERO.getResource(); //	FOODPHERO = int value of food pheromone status
	private static final String XOrientation = AppResources.FA_XOrientation.getResource(); //	XOrientation = int (will be 1, 0 or -1)
	private static final String YOrientation = AppResources.FA_YOrientation.getResource(); //	YOrientation = int (will be 1, 0 or -1)
	private static final String NEST = AppResources.FA_NEST.getResource(); //	NEST = 1: is nest, 0: isn't
	private static final String NESTFOOD = AppResources.FA_NESTFOOD.getResource(); //	NESTFOOD: int count of food at nest
	private static final String PATCH = AppResources.FA_PATCH.getResource();


	void evaluateCell(Cell myCell, CellGrid myGrid) { // Pseudo Ant-Forage
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		BackgroundCell currentBGCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		if (myCell.getCurrentState(ANT) == 1 && myCell.getCurrentState(FOOD) == 1 && currentBGCell.getCurrentBGState(NEST) == 0){
			this.returnToNest(myCell, myGrid);
		} else if (myCell.getCurrentState(ANT) == 1 && myCell.getCurrentState(FOOD) == 1 && currentBGCell.getCurrentBGState(NEST) == 1){
			dropFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid);
		} else if(myCell.getCurrentState(ANT) == 1 && myCell.getCurrentState(FOOD) == 0 && currentBGCell.getCurrentBGState(FOOD) == 1){
			pickUpFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid); 
		} else if(myCell.getCurrentState(ANT) == 1){
			this.findFoodSource(myCell, myGrid); 
		}
	}

	private void returnToNest(Cell myCell, CellGrid myGrid) {
		int atFoodSource = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState(FOODSOURCE);
		if (atFoodSource == 1){
			setHomeOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = myGrid.getCell(nextCol, nextRow);
		if (nextCell == null || nextCell.getNextState(ANT) != 0){
			setHomeOrientation(myCell, myGrid);
			nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
			nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
			nextCell = myGrid.getCell(nextCol, nextRow);
		}
		dropFoodPheromones(myCell, myGrid);
		setNextCellStates(myCell, nextCell);
	}
	
	private void findFoodSource(Cell myCell, CellGrid myGrid) {
		int atNest = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState(NEST);
		if (atNest == 1){
			setFoodOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = myGrid.getCell(nextCol, nextRow); // At the moment just identifying next cell, not doing anything with it
		if (nextCell == null || nextCell.getNextState(ANT) != 0){
			setHomeOrientation(myCell, myGrid);
			nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
			nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
			nextCell = myGrid.getCell(nextCol, nextRow);
		}
		dropHomePheromones(myCell, myGrid);
		setNextCellStates(myCell, nextCell);
	}
	
	private void setNextCellStates(Cell myCell, Cell nextCell){
		nextCell.setNextState(ANT, 1);
		nextCell.setNextState(FOOD, myCell.getCurrentState(FOOD));
		nextCell.setNextState(XOrientation, myCell.getCurrentState(XOrientation));
		nextCell.setNextState(YOrientation, myCell.getCurrentState(YOrientation));
		
		myCell.setNextState(ANT, 0);
		myCell.setNextState(FOOD, 0); // NEED TO HANDLE WHEN AN ANT WALKS OVER FOOD BUT ALREADY CARRIES FOOD
		myCell.setNextState(XOrientation, 0);
		myCell.setNextState(YOrientation, 0);
	}
	
	private void setHomeOrientation(Cell myCell, CellGrid myGrid){
		Cell homeNeighbour = findHomePheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int homeCol = homeNeighbour.getMyCol(); int homeRow = homeNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setNextState(XOrientation, xDir);
		myCell.setNextState(YOrientation, yDir);
	}
	
	private void setFoodOrientation(Cell myCell, CellGrid myGrid){
		Cell foodNeighbour = findFoodPheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int homeCol = foodNeighbour.getMyCol(); int homeRow = foodNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setNextState(XOrientation, xDir);
		myCell.setNextState(YOrientation, yDir);
	}
	
	private Cell findHomePheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
			if (testBG.getCurrentBGState(HOMEPHERO) >= mostPheroNum && testCell.getNextState(ANT) == 0){
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
			if (testBG.getCurrentBGState(FOODPHERO) >= mostPheroNum && testCell.getNextState(ANT) == 0){
				mostPheroCell = testCell;
			}
		}
		return mostPheroCell;
	}
	
	private void dropHomePheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = bgCell.getCurrentBGState(HOMEPHERO) + 1;
		bgCell.setNextBGState(HOMEPHERO, nextPhero);
	}
	
	private void dropFoodPheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = bgCell.getCurrentBGState(FOODPHERO) + 1;
		bgCell.setNextBGState(FOODPHERO, nextPhero);
	}
	
	private void dropFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState(NESTFOOD, bgCell.getCurrentBGState(NESTFOOD) + 1);
		myCell.setNextState(FOOD, 0);
	}
	
	private void pickUpFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState(FOOD, 0);
		myCell.setNextState(FOOD, 1);
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
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		if (myCell.getCurrentType().equals(ANT)) {
			myCell.setFill(Color.MAROON);
		} else if (myCell.getCurrentType().equals(NEST)) {
			myCell.setFill(Color.GRAY);
		} else if (myCell.getCurrentType().equals(FOOD)) {
			myCell.setFill(Color.RED);
		} else if (bgCell.getCurrentBGState(FOODPHERO) > 3) {
			myCell.setFill(Color.BLUE);
		} else if (bgCell.getCurrentBGState(HOMEPHERO) > 3) {
			myCell.setFill(Color.PINK);
		} else {
			myCell.setFill(Color.BISQUE);
		}
	}
	
	void setStatesInMap(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		myCell.setCurrentState(XOrientation, 0);
		myCell.setCurrentState(YOrientation, 0);
		if (myCell.getCurrentState(FOOD) == 1){
			bgCell.setCurrentBGState(FOOD, 1);
			bgCell.setCurrentBGState(FOODSOURCE, 1);
			myCell.setCurrentState(FOOD, 0);
		}
		bgCell.setCurrentBGState(HOMEPHERO, 0);
		bgCell.setCurrentBGState(FOODPHERO, 0);
		if (myCell.getCurrentState(NEST) == 1){
			bgCell.setCurrentBGState(NESTFOOD, 0);
		}
	}

	
	void setStatesInMap(Cell myCell) {
		// Bleh		
	}

}
