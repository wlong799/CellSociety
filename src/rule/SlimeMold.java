package rule;

import java.util.List;
import java.util.Random;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * In this model, each turtle drops a chemical pheromone (shown in green). The
 * turtles also "sniff" ahead, trying to follow the gradient of other turtles'
 * chemicals. Meanwhile, the patches diffuse and evaporate the pheromone.
 * Following these simple, decentralized rules, the turtles aggregate into
 * clusters.
 * 
 * @author Lucia Martos
 */

public class SlimeMold extends Rule {
	
	@Override
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		myBGNeighbours = myGrid.getNeighbours(myCell, myGrid);
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);

		if (myCell.getCurrentType().equals("TURTLE")
				&& myBackgroundCell.getCurrentBGState("CHEMICAL") >= getParameter("SNIFF_THRESH")) {
			handleTurtleSniffer(myCell, myGrid, myBackgroundCell);
		}

		else if (myCell.getCurrentType().equals("TURTLE")) {
			handleTurtleNoSniff(myCell, myBackgroundCell);
		}
		else{
			myCell.setNextType(myCell.getCurrentType());
		}
	}

	private void handleTurtleNoSniff(Cell myCell, BackgroundCell myBackgroundCell) {
		//put the chemical in the current cell and the neighbour
		spreadChemicalToCellAndNeighbours(myBackgroundCell);
		myCell.setNextType(myCell.getCurrentType());
	}

	private void handleTurtleSniffer(Cell myCell, CellGrid myGrid, BackgroundCell myBackgroundCell) {
		//move towards the highest conc cell of chemical
		spreadChemicalToCellAndNeighbours(myBackgroundCell);
		BackgroundCell myMaxBGCell = findHighestConcCell(myCell,myGrid);
		Cell nextLocation = myGrid.getCellofBG(myMaxBGCell);
		nextLocation.setNextType("TURTLE");
		myCell.setNextType("EMPTY");
	}
	
	private BackgroundCell findHighestConcCell(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBGCell = myBGNeighbours.get(0);
		int maxChemical= myBGCell.getCurrentBGState("CHEMICAL");
		for (BackgroundCell myNeighbour : myBGNeighbours) {
			// doesnt take into account if there is already an agent there...
			if (myNeighbour.getCurrentBGState("CHEMICAL") > maxChemical) { 
				maxChemical = myNeighbour.getCurrentBGState("CHEMICAL");
				myBGCell = myNeighbour;
			}
		}
		return myBGCell;
	}

	private void spreadChemicalToCellAndNeighbours(BackgroundCell myBackgroundCell) {
		myBackgroundCell.setNextBGState("CHEMICAL",
				myBackgroundCell.getCurrentBGState("CHEMICAL") + getParameter("DIFFUSION") - getParameter("EVAPORATION"));
		for (BackgroundCell myBackgroundNeighbour : myBGNeighbours) {
			myBackgroundNeighbour.setNextBGState("CHEMICAL",
					myBackgroundNeighbour.getCurrentBGState("CHEMICAL") + getParameter("DIFFUSION")-getParameter("EVAPORATION"));
		}
	}


	@Override
	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);
		if (myCell.getCurrentType().equals("TURTLE")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			setColorOfBGCells(myCell, myBackgroundCell);
		}
	}

	private void setColorOfBGCells(Cell myCell, BackgroundCell myBackgroundCell) {
		if (myBackgroundCell.getCurrentBGState("CHEMICAL") > 0
				&& myBackgroundCell.getCurrentBGState("CHEMICAL") < 5) {
			myCell.setFill(Color.LIGHTGREEN);
		} else if (myBackgroundCell.getCurrentBGState("CHEMICAL") > 5) {
			myCell.setFill(Color.GREEN);
		} else {
			myCell.setFill(Color.BLACK);
		}
	}

	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		// account for diffusion on cells that have not been edited before only evaporate if there is some chemical there
		if (myBackgroundCell.getCurrentBGState("CHEMICAL") > 0) {
			myBackgroundCell.setNextBGState("CHEMICAL",
					myBackgroundCell.getCurrentBGState("CHEMICAL") - getParameter("EVAPORATION"));
		} else {
			myBackgroundCell.setNextBGState("CHEMICAL", myBackgroundCell.getCurrentBGState("CHEMICAL"));
		}
	}

	@Override
	void setStatesInMap(Cell myCell) {
		return;
	}

	@Override
	void setBGStatesInMap(BackgroundCell myBGCell) {
		//set the amount of chemical randomly
		myBGCell.setCurrentBGState("CHEMICAL", (int) (Math.random() * 6));
	}

}
