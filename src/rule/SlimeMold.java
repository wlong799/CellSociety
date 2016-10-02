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
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);
		myBGNeighbours = myGrid.getNeighbours(myBackgroundCell);
		
		//if you are a turtle and you sniff chemical move towards the highest conc cell of chemical 
		if(myCell.getCurrentType().equals("TURTLE") && myBackgroundCell.getCurrentBGState("CHEMICAL") > getParameter("pheromone") ){
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
		myBackgroundCell.setNextBGState("CHEMICAL",myBackgroundCell.getNextBGState("CHEMICAL") +getParameter("diffusion"));
		for(BackgroundCell myBackgroundNeighbour:myBGNeighbours){
			myBackgroundNeighbour.setNextBGState("CHEMICAL",myBackgroundNeighbour.getNextBGState("CHEMICAL") +getParameter("diffusion"));
		}
	}

	private int[] findHighestConcCell(Cell myCell) {
		
		Cell nextPosition = null;
		int maxPheromoneLevel = 0;
		int myCol = myCell.getMyCol();
		int myRow = myCell.getMyRow();
		
		for(BackgroundCell myBackgroundNeighbour: myBGNeighbours){
			if(myBackgroundNeighbour.getCurrentBGState("pheromone") > maxPheromoneLevel){
				myCol = myBackgroundNeighbour.getMyCol();
				myRow = myBackgroundNeighbour.getMyRow();
				maxPheromoneLevel = myBackgroundNeighbour.getCurrentBGState("pheromone");
			}
		}
		return new int[]{myCol, myRow};
	}

	@Override
	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);
		if (myCell.getCurrentType().equals("TURTLE")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			if(myBackgroundCell.getCurrentBGState("CHEMICAL") > 0 && myBackgroundCell.getCurrentBGState("CHEMICAL") < 5){
				myCell.setFill(Color.GREEN);
			}
			else if(myBackgroundCell.getCurrentBGState("CHEMICAL") > 5){
				myCell.setFill(Color.WHITE);
			}
			else{
				myCell.setFill(Color.BLACK);
			}
		}
	}
	
	void evaluateBackgroundCell(BackgroundCell myBackgroundCell){
		//account for diffusion
		//TODO CHANGE MAP TO DOUBLE! 
		myBackgroundCell.setNextBGState("CHEMICAL", myBackgroundCell.getNextBGState("CHEMICAL")*getParameter("evaporation"));
	}

	@Override
	void setStatesInMap(Cell myCell) {
		// TODO Auto-generated method stub
		
	}

}
