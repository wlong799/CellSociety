package rule;

import javafx.scene.paint.Color;
import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;

/**
 * SpreadingOfFire is a model in which the fire spreads to a tree in its
 * surroundings with the probability of probFire
 *
 * @author Lucia Martos
 */
public class SpreadingOfFire extends Rule {
    void evaluateCell(Cell myCell, CellGrid myGrid) {
        myNeighbours = myGrid.getNeighbours(myCell);
        nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
        double myRandomValue = Math.random() * 100;
        boolean hasFire = checkForFire();
        if (myCell.getCurrentType().equals("TREE") && hasFire) {
            if (myRandomValue < getParameter("probCatch")) {
                myCell.setNextType("FIRE");
            } else {
                myCell.setNextType("TREE");
            }
        } else if (myCell.getCurrentType().equals("FIRE")) {
            myCell.setNextType("EMPTY");
        } else {
            myCell.setNextType(myCell.getCurrentType());
        }
        return;
    }

    private boolean checkForFire() {
        for (Cell myNeigh : nonDiagNeighbours) {
            if (myNeigh.getCurrentType().equals("FIRE")) {
                return true;
            }
        }
        return false;
    }

    void setStatesInMap(Cell myCell) {
        return;
    }

    public void setColor(Cell myCell, CellGrid myGrid) {
        if (myCell.getCurrentType().equals("FIRE")) {
            myCell.setFill(Color.RED);
        } else if (myCell.getCurrentType().equals("TREE")) {
            myCell.setFill(Color.GREEN);
        } else if (myCell.getCurrentType().equals("EMPTY")) {
            myCell.setFill(Color.WHITE);
        }
    }

    @Override
    void setBGStatesInMap(BackgroundCell myBGCell) {
        return;
    }

    @Override
    void evaluateBackgroundCell(BackgroundCell myBackgroundCell) {
        return;
    }

}
