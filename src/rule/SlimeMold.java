package rule;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

//TODO diffusion of the chemical in cellgrid class?
public class SlimeMold extends Rule {
	
	public SlimeMold() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		// TODO Auto-generated method stub
		myNeighbours = myGrid.getNeighbours(myCell);
		myBackgroundNeighbours = myGrid.getNeighbours(myBackgroundCell);
		
		//if you are a turtle and you sniff chemical move towards the highest conc cell of chemical 
		if(myCell.getCurrentType().equals("TURTLE") && myBackgroundCell.getCurrentBackgroundState("CHEMICAL") > getParameter("pheromone") ){
			int[] nextLocationCordinates = findHighestConcCell(myCell);
			Cell nextLocation = myGrid.getCell(nextLocationCordinates[0], nextLocationCordinates[1]);
			nextLocation.setNextType("TURTLE");
			myCell.setNextType("EMPTY");
		}
		
		//if you are a turtle and you dont sniff chemical simply put the chemical in the current cell and the neighbour 
		else if(myCell.getCurrentType().equals("TURTLE")){
			spreadChemicalToCellAndNeighbours(myBackgroundCell);
		}
			
	}

	private void spreadChemicalToCellAndNeighbours(BackgroundCell myBackgroundCell) {
		myBackgroundCell.setNextBackgroundState("CHEMICAL",myBackgroundCell.getNextBackgroundState("CHEMICAL") +1);
		for(BackgroundCell myBackgroundNeighbour:myBackgroundNeighbours){
			myBackgroundNeighbour.setNextBackgroundState("CHEMICAL",myBackgroundNeighbour.getNextBackgroundState("CHEMICAL") +1);
		}
	}

	private int[] findHighestConcCell(Cell myCell) {
		
		Cell nextPosition = null;
		int maxPheromoneLevel = 0;
		int myCol = myCell.getMyCol();
		int myRow = myCell.getMyRow();
		
		for(BackgroundCell myBackgroundNeighbour: myBackgroundNeighbours){
			if(myBackgroundNeighbour.getCurrentBackgroundState("pheromone") > maxPheromoneLevel){
				myCol = myBackgroundNeighbour.getMyCol();
				myRow = myBackgroundNeighbour.getMyRow();
				maxPheromoneLevel = myBackgroundNeighbour.getCurrentBackgroundState("pheromone");
			}
		}
		return new int[]{myCol, myRow};
	}

	@Override
	public void setColor(Cell myCell, CellGrid myCellGrid) {
		if (myCell.getCurrentType().equals("TURTLE")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			BackgroundCell myBackgroundCell= myCellGrid.getBackgroundCell(myCell.getMyCol(), myCell.getMyRow());
			if(myBackgroundCell.getCurrentBackgroundState("CHEMICAL") > 0 && myBackgroundCell.getCurrentBackgroundState("CHEMICAL") < 5){
				myCell.setFill(Color.GREEN);
			}
			else if(myBackgroundCell.getCurrentBackgroundState("CHEMICAL") > 5){
				myCell.setFill(Color.WHITE);
			}
			else{
				myCell.setFill(Color.BLACK);
			}
		}
	}

	@Override
	void setStatesInMap(Cell myCell) {
		// TODO Auto-generated method stub
		
	}

}
