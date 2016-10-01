package rule;

import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

public class SugarScape extends Rule {

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

		Cell myMaxSugarCell = getCellWithMaxSugar(myCell);
		myMaxSugarCell.setNextType("AGENT");
		// give the sugar of the path to the agent - sugarMetabolism
		myMaxSugarCell.setNextState("sugar",
				myCell.getCurrentState("sugar")
						+ myMaxSugarCell.getBackgroundCellofCell(myMaxSugarCell).getCurrentBackgroundState("sugar")
						- getParameter("sugarMetabolism"));
		// remove the sugar from the patch
		myMaxSugarCell.getBackgroundCellofCell(myMaxSugarCell).setNextBackgroundState("sugar", 0);
		// change the current cell to empty
		myCell.setNextType("EMPTY");
		myCell.setNextState("sugar", 0);

	}

	private Cell getCellWithMaxSugar(Cell myCell) {
		int maxSugar = 0;
		Cell myMaxSugarCell = null;
		for (Cell myNeighbour : nonDiagNeighbours) {
			if (myNeighbour.getBackgroundCellofCell(myNeighbour).getCurrentBackgroundState("sugar") > maxSugar) {
				// ASK WHY DO I NEED TO SAY MYCELL CHECKCHEKCHCEKC
				maxSugar = myNeighbour.getBackgroundCellofCell(myNeighbour).getCurrentBackgroundState("sugar");
				myMaxSugarCell = myNeighbour;
			}
		}
		return myMaxSugarCell;
	}

	@Override
	public void setColor(Cell myCell, BackgroundCell myBackgroundCell) {
		if (myCell.getCurrentType().equals("AGENT")) {
			myCell.setFill(Color.RED);
		} else if (myCell.getCurrentType().equals("EMPTY")) {
			if (myBackgroundCell.getCurrentBackgroundState("sugar") == 0) {
				myCell.setFill(Color.WHITE);
			} else if (myBackgroundCell.getCurrentBackgroundState("sugar") <= 2) {
				myCell.setFill(Color.ANTIQUEWHITE);
			} else if (myBackgroundCell.getCurrentBackgroundState("sugar") <= 3) {
				myCell.setFill(Color.BISQUE);
			} else if (myBackgroundCell.getCurrentBackgroundState("sugar") <= 4) {
				myCell.setFill(Color.ORANGE);
			} else {
				myCell.setFill(Color.BLACK);
			}
		}

	}

	@Override
	void setStatesInMap(Cell myCell) {
		// TODO Auto-generated method stub

	}

	void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
		if (intervalNumber > getParameter("sugarGrowBackInterval")
				&& getParameter("sugar") < getParameter("maxSugar")) {
			myBackgroundCell.setNextBackgroundState("sugar",
					myBackgroundCell.getNextBackgroundState("sugar") + getParameter("sugarGrowBackRate"));
		}
	}

}
