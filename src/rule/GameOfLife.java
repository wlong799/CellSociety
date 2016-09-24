package rule;

import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * GameOfLife is a model in which if any live cell with fewer than two live neigbours dies,
 * any live cell with two or three live neighbours lives on the next generation
 * Any live cell with more than three live neighbours dies
 * any dead cell with exactly three live neighbours becomes a live cell
 * @author Lucia Martos
 */

public class GameOfLife extends Rule {

	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		if (myCell.getCurrentType().equals("LIVE")) {
			handleLiveCell(myCell);
		}
		else{
			handleDeadCell(myCell);
		}
	}

	private void handleDeadCell(Cell myCell) {
		//dead cell with three life neighbours goes live
		if(countNeigboursOfType("DEAD") == 3){
			myCell.setNextType("LIVE");
		}
		else{
			myCell.setNextType("DEAD");
		}
	}

	private void handleLiveCell(Cell myCell) {
		//any live cell with fewer than two live neighbours of more than three live neighbour dies
		if(countNeigboursOfType("LIVE") < 2 || countNeigboursOfType("LIVE") > 3){
			myCell.setNextType("DEAD");
		}
		else{
			myCell.setNextType("LIVE");
		}
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
