package rule;

import java.util.Random;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

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

		Cell myMaxSUGARCell = getCellWithMaxSUGAR(myCell, myGrid);
		myMaxSUGARCell.setNextType("AGENT");
		// give the SUGAR of the path to the agent - SUGARMetabolism
		myMaxSUGARCell.setNextState("SUGAR",
				myCell.getCurrentState("SUGAR")
						+ myGrid.getBGCellofCell(myMaxSUGARCell).getCurrentBGState("SUGAR")
						- getParameter("sugarMetabolism"));
		// remove the SUGAR from the patch
		myGrid.getBGCellofCell(myMaxSUGARCell).setNextBGState("SUGAR", 0);
		// change the current cell to empty
		myCell.setNextType("EMPTY");
		myCell.setNextState("SUGAR", 0);

	}

	private Cell getCellWithMaxSUGAR(Cell myCell, CellGrid myGrid) {
		int maxSUGAR = 0;
		Cell myMaxSUGARCell = null;
		for (Cell myNeighbour : nonDiagNeighbours) {
			if (myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("SUGAR") > maxSUGAR) {
				maxSUGAR = myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("SUGAR");
				myMaxSUGARCell = myNeighbour;
			}
		}
		return myMaxSUGARCell;
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
		}
		else{
			myBackgroundCell.setNextBGState("SUGAR",
					myBackgroundCell.getCurrentBGState("SUGAR"));
		}
	}

	@Override
	void setBGStatesInMap(BackgroundCell myBGCell) {
		Random rn = new Random();
		int myMaxSugar = rn.nextInt(MAX_SUGAR);
		myBGCell.setCurrentBGState("MAX_SUGAR", myMaxSugar);
		myBGCell.setCurrentBGState("SUGAR",rn.nextInt(myMaxSugar));
		
	}

}
