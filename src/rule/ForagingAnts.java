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
		BackgroundCell myBGCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		if (myCell.getCurrentType().equals(ANT) && myCell.getCurrentState(FOOD) == 1 && myBGCell.getCurrentBGState(NEST) == 0){
			this.returnToNest(myCell, myGrid);
		} else if (myCell.getCurrentType().equals(ANT) && myCell.getCurrentState(FOOD) == 1 && myBGCell.getCurrentBGState(NEST) == 1){
			dropFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid);
		} else if(myCell.getCurrentType().equals(ANT) && myCell.getCurrentState(FOOD) == 0 && myBGCell.getCurrentBGState(FOOD) == 1){
			pickUpFood(myCell, myGrid);
			this.returnToNest(myCell, myGrid); 
		} else if(myCell.getCurrentType().equals(ANT)){
			this.findFoodSource(myCell, myGrid); 
		}
		if (myCell.getNextType() == null){
			myCell.setNextType(myCell.getCurrentType());
		}
		transferPheromones(myBGCell);
	}
	
	private void transferPheromones(BackgroundCell myBGCell){
		if (myBGCell.getNextBGState(FOODPHERO) == null){
			myBGCell.setNextBGState(FOODPHERO, myBGCell.getCurrentBGState(FOODPHERO));
		}
		if (myBGCell.getCurrentBGState(HOMEPHERO) == null){
			myBGCell.setNextBGState(HOMEPHERO, myBGCell.getCurrentBGState(HOMEPHERO));
		}
	}

	private void returnToNest(Cell myCell, CellGrid myGrid) {
		int atFoodSource = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState(FOODSOURCE);
		if (atFoodSource == 1){
			setHomeOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = null;
		try { if (nextCell == null || nextCell.getNextType().equals(ANT)){
			setHomeOrientation(myCell, myGrid);
			nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
			nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
			nextCell = myGrid.getCell(nextRow, nextCol);
			}
		} catch (NullPointerException npe){		}
		if (nextCol >= 0 && nextRow >= 0){
			nextCell = myGrid.getCell(nextRow, nextCol);
		} else if (nextCol >= 0 && nextRow < 0){
			nextCell = myGrid.getCell(myCell.getMyRow(), nextCol);
		} else if (nextCol < 0 && nextRow >= 0){
			nextCell = myGrid.getCell(nextRow, myCell.getMyCol());
		} else {
			nextCell = myCell;
		} if (nextCell == null){
			System.out.println("Shit");
			nextCell = myCell;
		}
		if (nextCell != myCell){
			dropFoodPheromones(myCell, myGrid);
		}
		setNextCellStates(myCell, nextCell);
	}
	
	private void findFoodSource(Cell myCell, CellGrid myGrid) {
		int atNest = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol()).getCurrentBGState(NEST);
		if (atNest == 1 || true){
			setFoodOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = null;
		if (nextCol >= 0 && nextRow >= 0){
			nextCell = myGrid.getCell(nextRow, nextCol);
		} else if (nextCol >= 0 && nextRow < 0){
			nextCell = myGrid.getCell(myCell.getMyRow(), nextCol);
		} else if (nextCol < 0 && nextRow >= 0){
			nextCell = myGrid.getCell(nextRow, myCell.getMyCol());
		} else {
			nextCell = myCell;
		}
		if (nextCell == null || (nextCell.getNextType() != null && nextCell.getNextType().equals(ANT))){
			setFoodOrientation(myCell, myGrid);
			nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
			nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
			nextCell = myGrid.getCell(nextRow, nextCol);
		} if(nextCell == null){
			nextCell = myCell;
		}
		if (nextCell != myCell){
			dropFoodPheromones(myCell, myGrid);
		}
		setNextCellStates(myCell, nextCell);
	}
	
	private void setNextCellStates(Cell myCell, Cell nextCell){
		nextCell.setNextType(ANT);
		nextCell.setNextState(FOOD, nextCell.getCurrentState(FOOD) + myCell.getCurrentState(FOOD));
		nextCell.setNextState(XOrientation, myCell.getCurrentState(XOrientation));
		nextCell.setNextState(YOrientation, myCell.getCurrentState(YOrientation));
		if (myCell.getNextType() == null){
			myCell.setNextType(PATCH);
		}
		myCell.setNextState(FOOD, myCell.getCurrentState(FOOD) - 1); // NEED TO HANDLE WHEN AN ANT WALKS OVER FOOD BUT ALREADY CARRIES FOOD
		myCell.setNextState(XOrientation, 0);
		myCell.setNextState(YOrientation, 0);
	}
	
	private void setNextBGStates(Cell myCell, CellGrid myGrid){
		BackgroundCell myBGCell = myGrid.getBGCellofCell(myCell);
		myBGCell.setCurrentBGState(FOOD, myBGCell.getCurrentBGState(FOOD));
		myBGCell.setCurrentBGState(FOODSOURCE, myBGCell.getCurrentBGState(FOODSOURCE));
		myBGCell.setCurrentBGState(HOMEPHERO, myBGCell.getCurrentBGState(HOMEPHERO));
		myBGCell.setCurrentBGState(FOODPHERO, myBGCell.getCurrentBGState(FOODPHERO));
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
		myCell.setCurrentState(XOrientation, xDir);
		myCell.setCurrentState(YOrientation, yDir);
	}
	
	private Cell findHomePheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
			try { if (testBG.getCurrentBGState(HOMEPHERO) >= mostPheroNum && !testCell.getNextType().equals(ANT)){
				mostPheroCell = testCell;
				}
			} catch(NullPointerException npe){
				mostPheroCell = testCell;
			}
		} for(Cell testCell : nonDiagNeighbours){
			BackgroundCell testBG = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
			try { if (testBG.getCurrentBGState(HOMEPHERO) >= mostPheroNum && !(testCell.getNextType().equals(ANT))){
				mostPheroCell = testCell;
				} 
			} catch(NullPointerException npe){
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
			if (testBG.getCurrentBGState(FOODPHERO) >= mostPheroNum && (testCell.getNextType() == null) || testCell.getNextType().equals(ANT)){
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

	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		try { if (myCell.getCurrentType().equals(ANT)) {
				myCell.setFill(Color.MAROON);
			} else if (myCell.getCurrentType().equals(NEST)) {
				myCell.setFill(Color.GRAY);
			} else if (myCell.getCurrentType().equals(FOOD)) {
				myCell.setFill(Color.RED);
			} else if (bgCell.getCurrentBGState(FOODPHERO) != null && bgCell.getCurrentBGState(FOODPHERO) > 3) {
				myCell.setFill(Color.BLUE);
			} else if (bgCell.getCurrentBGState(HOMEPHERO) != null && bgCell.getCurrentBGState(HOMEPHERO) > 3) {
				myCell.setFill(Color.PINK);
			} else {
				myCell.setFill(Color.BISQUE);
			} 
		} catch (NullPointerException npe){
			myCell.setFill(Color.BLACK);
		}
	}

	
	void setStatesInMap(Cell myCell) {
		myCell.setCurrentState(FOOD, 0);
		if (myCell.getCurrentType().equals(FOOD)){
			myCell.setCurrentState(FOOD, 1);
		}
		myCell.setCurrentState(XOrientation, 0);
		myCell.setCurrentState(YOrientation, 0);
	}

	void setBGStatesInMap(BackgroundCell myBGCell) {
		if (myBGCell.getMyRow() == 0 && myBGCell.getMyCol() == 0){
			myBGCell.setCurrentBGState(NEST, 1);
			myBGCell.setCurrentBGState(NESTFOOD, 0);
		} else {
			myBGCell.setCurrentBGState(NEST, 0);
		}
		myBGCell.setCurrentBGState(FOOD, 0);
		myBGCell.setCurrentBGState(FOODSOURCE, 0);
		myBGCell.setCurrentBGState(HOMEPHERO, 0);
		myBGCell.setCurrentBGState(FOODPHERO, 0);
	}

	@Override
	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		
	}

}
