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

		Cell myMaxSugarCell = getCellWithMaxSugar(myCell, myGrid);
		myMaxSugarCell.setNextType("AGENT");
		// give the sugar of the path to the agent - sugarMetabolism
		myMaxSugarCell.setNextState("sugar",
				myCell.getCurrentState("sugar")
						+ myGrid.getBGCellofCell(myMaxSugarCell).getCurrentBGState("sugar")
						- getParameter("sugarMetabolism"));
		// remove the sugar from the patch
		myGrid.getBGCellofCell(myMaxSugarCell).setNextBGState("sugar", 0);
		// change the current cell to empty
		myCell.setNextType("EMPTY");
		myCell.setNextState("sugar", 0);

	}

	private Cell getCellWithMaxSugar(Cell myCell, CellGrid myGrid) {
		int maxSugar = 0;
		Cell myMaxSugarCell = null;
		for (Cell myNeighbour : nonDiagNeighbours) {
			if (myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("sugar") > maxSugar) {
				// ASK WHY DO I NEED TO SAY MYCELL CHECKCHEKCHCEKC
				maxSugar = myGrid.getBGCellofCell(myNeighbour).getCurrentBGState("sugar");
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
			if (myBackgroundCell.getCurrentBGState("sugar") == 0) {
				myCell.setFill(Color.WHITE);
			} else if (myBackgroundCell.getCurrentBGState("sugar") <= 2) {
				myCell.setFill(Color.ANTIQUEWHITE);
			} else if (myBackgroundCell.getCurrentBGState("sugar") <= 3) {
				myCell.setFill(Color.BISQUE);
			} else if (myBackgroundCell.getCurrentBGState("sugar") <= 4) {
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
			myBackgroundCell.setNextBGState("sugar",
					myBackgroundCell.getNextBGState("sugar") + getParameter("sugarGrowBackRate"));
		}
	}

}
