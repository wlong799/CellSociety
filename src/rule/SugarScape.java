package rule;

import java.util.Random;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * Sugar scape: patch grows back sugar at sugarGrowBackRate every
 * sugarGrowBackInterval ticks Agent movement: look at vacant neighbours with
 * the highest sugar values then when you move subtract sugarMetabolism from
 * sugar of the agent. If the sugar of the agent is less than zero the agent
 * dies.
 * 
 * @author Lucia Martos
 */
public class SugarScape extends Rule {

	private static final int MAX_SUGAR = 5;
	int intervalNumber;

	public SugarScape() {
		// TODO Auto-generated constructor stub

	}

	@Override
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		getMapsOfNeighbours(myCell, myGrid);

		if (myCell.getCurrentType().equals("AGENT")) {
			handleAgentCell(myCell, myGrid);
		} else {
			handleEmptyCell(myCell);
		}

	}

	private void getMapsOfNeighbours(Cell myCell, CellGrid myGrid) {
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
		myBGNeighbours = myGrid.getNeighbours(myCell, myGrid);
	}

	private void handleEmptyCell(Cell myCell) {
		myCell.setNextType(myCell.getCurrentType());
		myCell.setNextState("SUGAR", 0);
	}

	private void handleAgentCell(Cell myCell, CellGrid myGrid) {
		BackgroundCell myMaxSugarBGCell = getBGCellWithMaxSugar(myCell, myGrid);
		transferSugarFromBGCellToCell(myCell, myGrid, myMaxSugarBGCell);			
		checkForLowSugarResultingInDeath(myGrid, myMaxSugarBGCell);
		
		// change the current cell to empty
		myCell.setNextType("EMPTY");
		myCell.setNextState("SUGAR", getParameter("initialsugar"));
	}

	private void transferSugarFromBGCellToCell(Cell myCell, CellGrid myGrid, BackgroundCell myMaxSugarBGCell) {
		// give the SUGAR of the path to the agent - SugarMetabolism
		myGrid.getCellofBG(myMaxSugarBGCell).setNextState("SUGAR", myCell.getCurrentState("SUGAR")
				+ myMaxSugarBGCell.getCurrentBGState("SUGAR") - getParameter("sugarMetabolism"));
		// remove the SUGAR from the patch
		myMaxSugarBGCell.setNextBGState("SUGAR", 0);
		myMaxSugarBGCell.setNextBGState("intervalNumber", myMaxSugarBGCell.getCurrentBGState("intervalNumber")+1);
	}

	private void checkForLowSugarResultingInDeath(CellGrid myGrid, BackgroundCell myMaxSugarBGCell) {
		if (myGrid.getCellofBG(myMaxSugarBGCell).getNextState("SUGAR") <= 0) {
			myGrid.getCellofBG(myMaxSugarBGCell).setNextType("EMPTY");
		} else {
			myGrid.getCellofBG(myMaxSugarBGCell).setNextType("AGENT");
		}
	}

	private BackgroundCell getBGCellWithMaxSugar(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBGCell = myBGNeighbours.get(0);
		int maxSugar = myBGCell.getCurrentBGState("SUGAR");
		for (BackgroundCell myNeighbour : myBGNeighbours) {
			// doesnt take into account if there is already an agent there...
			if (myNeighbour.getCurrentBGState("SUGAR") > maxSugar) { 
				maxSugar = myNeighbour.getCurrentBGState("SUGAR");
				myBGCell = myNeighbour;
			}
		}
		return myBGCell;
	}

	@Override
	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);
		if (myCell.getCurrentType().equals("AGENT")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			setFillsAccordingToBG(myCell, myBackgroundCell);
		}

	}

	private void setFillsAccordingToBG(Cell myCell, BackgroundCell myBackgroundCell) {
		if (myBackgroundCell.getCurrentBGState("SUGAR") == 0) {
			myCell.setFill(Color.WHITE);
		} else if (myBackgroundCell.getCurrentBGState("SUGAR") <= 2) {
			myCell.setFill(Color.KHAKI);
		} else if (myBackgroundCell.getCurrentBGState("SUGAR") <= 3) {
			myCell.setFill(Color.ORANGE);
		} else if (myBackgroundCell.getCurrentBGState("SUGAR") > 3) {
			myCell.setFill(Color.ORANGE);
		} else {
			myCell.setFill(Color.BLACK);
		}
	}

	@Override
	void setStatesInMap(Cell myCell) {
		myCell.setCurrentState("SUGAR", getParameter("initialsugar"));

	}

	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		//if the interval number is greater than the growbackrate and lower than max suggar add sugar to it
		if (myBackgroundCell.getCurrentBGState("intervalNumber") > getParameter("sugarGrowBackInterval")
				&& myBackgroundCell.getCurrentBGState("SUGAR") < myBackgroundCell.getCurrentBGState("MAX_SUGAR")) {
			myBackgroundCell.setNextBGState("SUGAR",
					myBackgroundCell.getCurrentBGState("SUGAR") + getParameter("sugarGrowBackRate"));
			myBackgroundCell.setNextBGState("intervalNumber",0);
		} 
		//otherwise add to the interval number and keep the same amount of sugar
		else {
			myBackgroundCell.setNextBGState("SUGAR", myBackgroundCell.getCurrentBGState("SUGAR"));
			myBackgroundCell.setNextBGState("intervalNumber", myBackgroundCell.getCurrentBGState("intervalNumber") +1);
		}
	}

	@Override
	void setBGStatesInMap(BackgroundCell myBGCell) {
		myBGCell.setCurrentBGState("intervalNumber", 0);
		addRandomSugarToCellBoundedByMaxSugar(myBGCell);

	}

	private void addRandomSugarToCellBoundedByMaxSugar(BackgroundCell myBGCell) {
		Random rn = new Random();
		int myMaxSugar = (int) rn.nextInt(MAX_SUGAR);
		myBGCell.setCurrentBGState("MAX_SUGAR", myMaxSugar);
		if (myMaxSugar > 0) {
			myBGCell.setCurrentBGState("SUGAR", (int) rn.nextInt(myMaxSugar));
		} else {
			myBGCell.setCurrentBGState("SUGAR", 0);
		}
	}

}
