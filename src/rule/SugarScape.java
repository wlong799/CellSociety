package rule;

import java.util.Random;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/*
 *Sugar scape: patch grows back sugar at sugarGrowBackRate every sugarGrowBackInterval ticks
 *Agent movement: look at vacant neighbours with the highest sugar values then when you move subtract sugarMetabolism from sugar
 * of the agent. If the sugar of the agent is less than zero the agent dies.
 */
public class SugarScape extends Rule {

	private static final int MAX_SUGAR = 4;
	int intervalNumber;

	public SugarScape() {
		// TODO Auto-generated constructor stub

	}

	@Override
	void evaluateCell(Cell myCell, CellGrid myGrid) {
		// TODO implement the "VISION component" - maybe (or just forget about
		// it...?)
		myNeighbours = myGrid.getNeighbours(myCell);
		nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);

		Cell myMaxSugarCell = getCellWithMaxSugar(myCell, myGrid);
		// give the SUGAR of the path to the agent - SugarMetabolism
		myMaxSugarCell.setNextState("SUGAR", myCell.getCurrentState("SUGAR")
				+ myGrid.getBGCellofCell(myMaxSugarCell).getCurrentBGState("SUGAR") - getParameter("sugarMetabolism"));

		// check if the sugar levels are low (death)
		if (myMaxSugarCell.getNextState("SUGAR") <= 0) {
			myMaxSugarCell.setNextType("EMPTY");
		} else {
			myMaxSugarCell.setNextType("AGENT");
		}
		// remove the SUGAR from the patch
		myGrid.getBGCellofCell(myMaxSugarCell).setNextBGState("SUGAR", 0);
		// change the current cell to empty
		myCell.setNextType("EMPTY");
		myCell.setNextState("SUGAR", 0);

	}

	private Cell getCellWithMaxSugar(Cell myCell, CellGrid myGrid) {
		int maxSugar = 0;
		Cell myMaxSugarCell = null;
		for (Cell myNeighbour : nonDiagNeighbours) {
			if (myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("SUGAR") > maxSugar) {
				maxSugar = myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("SUGAR");
				myMaxSugarCell = myNeighbour;
			}
		}
		return myMaxSugarCell;
	}

	@Override
	public void setColor(Cell myCell, CellGrid myGrid) {
		BackgroundCell myBackgroundCell = myGrid.getBGCellofCell(myCell);
		if (myCell.getCurrentType().equals("AGENT")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			if (myBackgroundCell.getCurrentBGState("SUGAR") == 0) {
				myCell.setFill(Color.WHITE);
			} else if (myBackgroundCell.getCurrentBGState("SUGAR") <= 2) {
				myCell.setFill(Color.ANTIQUEWHITE);
			} else if (myBackgroundCell.getCurrentBGState("SUGAR") <= 3) {
				myCell.setFill(Color.BISQUE);
			} else if (myBackgroundCell.getCurrentBGState("SUGAR") <= 4) {
				myCell.setFill(Color.ORANGE);
			} else {
				myCell.setFill(Color.BLACK);
			}
		}

	}

	@Override
	void setStatesInMap(Cell myCell) {
		myCell.setCurrentState("SUGAR", getParameter("initialsugar"));

	}

	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		if (intervalNumber > getParameter("sugarGrowBackInterval")
				&& getParameter("SUGAR") < myBackgroundCell.getCurrentBGState("MAX_SUGAR")) {
			myBackgroundCell.setNextBGState("SUGAR",
					myBackgroundCell.getCurrentBGState("SUGAR") + getParameter("sugarGrowBackRate"));
		} else {
			myBackgroundCell.setNextBGState("SUGAR", myBackgroundCell.getCurrentBGState("SUGAR"));
		}
	}

	@Override
	void setBGStatesInMap(BackgroundCell myBGCell) {
		Random rn = new Random();
		int myMaxSugar = rn.nextInt(MAX_SUGAR);
		myBGCell.setCurrentBGState("MAX_SUGAR", myMaxSugar);
		myBGCell.setCurrentBGState("SUGAR", rn.nextInt(myMaxSugar));

	}

}
