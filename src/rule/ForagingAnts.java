package rule;

import cellsociety_team13.AppResources;
import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

public class ForagingAnts extends Rule {
	private static final String FOOD = AppResources.FA_FOOD.getResource(); //	FOOD = 1: ant has food, 0: ant does not have food
	private static final String ANTS = AppResources.FA_ANTS.getResource(); //	FOODSOURCE = 1: is food source, 0: isn't
	private static final String HOMEPHERO = AppResources.FA_HOMEPHERO.getResource(); //	HOMEPHERO = int value of home pheromone status
	private static final String FOODPHERO = AppResources.FA_FOODPHERO.getResource(); //	FOODPHERO = int value of food pheromone status
	private static final String XOrientation = AppResources.FA_XOrientation.getResource(); //	XOrientation = int (will be 1, 0 or -1)
	private static final String YOrientation = AppResources.FA_YOrientation.getResource(); //	YOrientation = int (will be 1, 0 or -1)
	private static final String NEST = AppResources.FA_NEST.getResource(); //	NEST = 1: is nest, 0: isn't
	private static final String NESTFOOD = AppResources.FA_NESTFOOD.getResource(); //	NESTFOOD: int count of food at nest
	private static final String PATCH = AppResources.FA_PATCH.getResource();

	@Override
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		BackgroundCell myBGCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		transferBGStates(myBGCell);
		try { myCell.setNextState(ANTS, myCell.getNextState(ANTS)); 
		} catch (NullPointerException npe){
			myCell.setNextState(ANTS, myCell.getCurrentState(ANTS));
		} try { myCell.setNextState(FOOD, myCell.getNextState(FOOD)); 
		} catch (NullPointerException npe){
			myCell.setNextState(FOOD, myCell.getCurrentState(FOOD));
		}
		int numAnts = myCell.getCurrentState(ANTS);
		for (int ant = 0; ant < myCell.getCurrentState(ANTS); ant++){
			if (myCell.getCurrentState(FOOD) >= 1 && myCell.getCurrentType().equals(NEST)){
				System.out.println(1);
				dropFood(myCell, myGrid);
				findFoodSource(myCell, myGrid);
			} else if(myCell.getCurrentState(FOOD) >= 1){
				System.out.println(0);
				returnToNest(myCell, myGrid);
			} else if(myCell.getCurrentState(FOOD) < numAnts - ant && myBGCell.getCurrentBGState(FOOD) == 1){
				System.out.println(2);
				pickUpFood(myCell, myGrid);
				returnToNest(myCell, myGrid);
			} else {
				System.out.println(3);
				findFoodSource(myCell, myGrid);
			}
		}
		myCell.setNextType(myCell.getCurrentType());	
	}
	
	private void transferBGStates(BackgroundCell myBGCell){
		if (myBGCell.getNextBGState(FOODPHERO) == null){
			myBGCell.setNextBGState(FOODPHERO, myBGCell.getCurrentBGState(FOODPHERO));
		}
		if (myBGCell.getNextBGState(HOMEPHERO) == null){
			myBGCell.setNextBGState(HOMEPHERO, myBGCell.getCurrentBGState(HOMEPHERO));
		} 
		if (myBGCell.getNextBGState(FOOD) == null){
			myBGCell.setNextBGState(FOOD, myBGCell.getCurrentBGState(FOOD));
		} 
		if (myBGCell.getMyRow() == 0 && myBGCell.getMyCol() == 0){
			myBGCell.setNextBGState(FOOD, myBGCell.getCurrentBGState(FOOD));
		}
	}

	private void returnToNest(Cell myCell, CellGrid myGrid) {
		int atFoodSource = myGrid.getBGCellofCell(myCell).getCurrentBGState(FOOD);
		if (atFoodSource == 1){
			setHomeOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = myGrid.getCell(nextRow, nextCol);
		if (nextCell == null){
			nextCell = findHomePheromones(myCell, myGrid);
		}
		dropFoodPheromones(myCell, myGrid);
		setNextAntStates(myCell, nextCell, myGrid);
	}

	private void findFoodSource(Cell myCell, CellGrid myGrid) {
		if (myCell.getCurrentType().equals(NEST)){
			setFoodOrientation(myCell, myGrid);
		};
		int nextCol = myCell.getMyCol() + myCell.getCurrentState(XOrientation);
		int nextRow = myCell.getMyRow() + myCell.getCurrentState(YOrientation);
		Cell nextCell = null;
		nextCell = myGrid.getCell(nextRow, nextCol);
		nextCell = myGrid.getCell(nextRow, nextCol);
		if (nextCell == null){
			System.out.println("\n");
			nextCell = findFoodPheromones(myCell, myGrid);
		}
		dropHomePheromones(myCell, myGrid);
		setNextAntStates(myCell, nextCell, myGrid);
	}
	
	private void setHomeOrientation(Cell myCell, CellGrid myGrid){
		Cell homeNeighbour = findHomePheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); 
		int myRow = myCell.getMyRow();
		int homeCol = homeNeighbour.getMyCol(); 
		int homeRow = homeNeighbour.getMyRow();
		int xDir = myCol - homeCol;
		int yDir = myRow - homeRow;
		myCell.setCurrentState(XOrientation, xDir);
		myCell.setCurrentState(YOrientation, yDir);
	}
	
	private void setFoodOrientation(Cell myCell, CellGrid myGrid){
		Cell foodNeighbour = findFoodPheromones(myCell, myGrid);
		int myCol = myCell.getMyCol(); int myRow = myCell.getMyRow();
		int foodCol = foodNeighbour.getMyCol(); int foodRow = foodNeighbour.getMyRow();
		int xDir = myCol - foodCol;
		int yDir = myRow - foodRow;
		myCell.setCurrentState(XOrientation, xDir);
		myCell.setCurrentState(YOrientation, yDir);
	}
	
	private void setNextAntStates(Cell myCell, Cell nextCell, CellGrid myGrid){
		try { nextCell.setNextState(ANTS, nextCell.getNextState(ANTS) + 1); 
		} catch (NullPointerException npe){
			System.out.println("FIRST");
			nextCell.setNextState(ANTS, nextCell.getCurrentState(ANTS) + 1);
		}
		myCell.setNextState(ANTS, myCell.getNextState(ANTS) - 1);
		if (myCell.getCurrentState(FOOD) > 0){
			try {
				nextCell.setNextState(FOOD, nextCell.getNextState(FOOD) + 1);
			}
			catch (NullPointerException npe){
				nextCell.setNextState(FOOD, nextCell.getCurrentState(FOOD) + 1);
			}
			myCell.setNextState(FOOD, myCell.getNextState(FOOD) - 1);
			myCell.setCurrentState(FOOD, myCell.getCurrentState(FOOD) - 1);
		}
		nextCell.setNextState(XOrientation, myCell.getCurrentState(XOrientation));
		nextCell.setNextState(YOrientation, myCell.getCurrentState(YOrientation));		
		myCell.setNextState(XOrientation, 0);
		myCell.setNextState(YOrientation, 0);
	}
	
	private Cell findHomePheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		double probability = Math.random();
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCellofCell(testCell);
			if (testBG.getCurrentBGState(HOMEPHERO) > mostPheroNum){
				mostPheroCell = testCell;
			}
		} if (mostPheroCell == null || probability >= 0.75){
			int choice = (int) (Math.random()*myNeighbours.size());
			mostPheroCell = myNeighbours.get(choice);
		}
		return mostPheroCell;
	}
	
	private Cell findFoodPheromones(Cell myCell, CellGrid myGrid){
		int mostPheroNum = 0;
		double probability = Math.random();
		Cell mostPheroCell = null;
		for(Cell testCell : myNeighbours){
			BackgroundCell testBG = myGrid.getBGCellofCell(testCell);
			if (testBG.getCurrentBGState(FOODPHERO) > mostPheroNum){
				mostPheroCell = testCell;
			}
		} if (mostPheroCell == null || probability >= 0.75){
			int choice = (int) (Math.random()*myNeighbours.size());
			mostPheroCell = myNeighbours.get(choice);
		}
		return mostPheroCell;
	}
	
	private void dropHomePheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = 0;
		if (bgCell.getNextBGState(HOMEPHERO) == null){
			nextPhero = bgCell.getCurrentBGState(HOMEPHERO) + 1;
		} else {
			nextPhero = bgCell.getNextBGState(HOMEPHERO) + 1;
		}
		bgCell.setNextBGState(HOMEPHERO, nextPhero);
	}
	
	private void dropFoodPheromones(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		int nextPhero = 0;
		if (bgCell.getNextBGState(FOODPHERO) == null){
			nextPhero = bgCell.getCurrentBGState(FOODPHERO) + 1;
		} else {
			nextPhero = bgCell.getNextBGState(FOODPHERO) + 1;
		}
		bgCell.setNextBGState(FOODPHERO, nextPhero);
	}
	
	private void dropFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState(NESTFOOD, bgCell.getCurrentBGState(NESTFOOD) + 1);
		myCell.setCurrentState(FOOD, myCell.getCurrentState(FOOD) - 1);
	}
	
	private void pickUpFood(Cell myCell, CellGrid myGrid){
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		bgCell.setNextBGState(FOOD, 0);
		myCell.setCurrentState(FOOD, myCell.getCurrentState(FOOD) + 1);
	}
	
	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell bgCell = myGrid.getBGCell(myCell.getMyRow(), myCell.getMyCol());
		if (myCell.getCurrentType().equals(NEST)) {
				myCell.setFill(Color.BLACK);
		} else if (myCell.getCurrentState(FOOD) > 0) {
			myCell.setFill(Color.MAROON);
		} else if (myCell.getCurrentState(ANTS) > 0){
			myCell.setFill(Color.BLUE);
		} else if (bgCell.getCurrentBGState(FOOD) != 0){
				myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals(PATCH)) {
			myCell.setFill(Color.GREEN);
		} if (bgCell.getCurrentBGState(FOODPHERO) > 3) {
			myCell.setStroke(Color.LIGHTBLUE);
		} if (bgCell.getCurrentBGState(HOMEPHERO) > 3) {
			myCell.setStroke(Color.LIGHTGREEN);
		}	
	}
	
	void setStatesInMap(Cell myCell) {
		if (myCell.getCurrentType().equals(NEST)){
			myCell.setCurrentState(ANTS, 15);
		} else {
			myCell.setCurrentState(ANTS, 0);
		}
		myCell.setCurrentState(FOOD, 0);
		myCell.setCurrentState(XOrientation, 0);
		myCell.setCurrentState(YOrientation, 0);		
	}
	
	void setBGStatesInMap(BackgroundCell myBGCell) {
		if (myBGCell.getMyRow() == 0 && myBGCell.getMyCol() == 0){
			myBGCell.setCurrentBGState(NESTFOOD, 0);
		}
		if (Math.random() > 0.7){
			myBGCell.setCurrentBGState(FOOD, 1);
		} else {
			myBGCell.setCurrentBGState(FOOD, 0);
		}
		myBGCell.setCurrentBGState(HOMEPHERO, 0);
		myBGCell.setCurrentBGState(FOODPHERO, 0);		
	}
	
	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) { }
}
