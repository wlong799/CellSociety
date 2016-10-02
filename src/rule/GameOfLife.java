package rule;

import cellsociety_team13.AppResources;
import cellsociety_team13.BackgroundCell;
import cellsociety_team13.Cell;
import cellsociety_team13.CellGrid;
import javafx.scene.paint.Color;

/**
 * GameOfLife is a model in which if any live cell with fewer than two live neigbours dies,
 * any live cell with two or three live neighbours lives on the next generation
 * Any live cell with more than three live neighbours dies
 * any dead cell with exactly three live neighbours becomes a live cell
 *
 * @author Lucia Martos
 */

public class GameOfLife extends Rule {
    private static final String DEAD = AppResources.GOL_DEAD_TYPE.getResource();
    private static final String LIVE = AppResources.GOL_LIVE_TYPE.getResource();

    void evaluateCell(Cell myCell, CellGrid myGrid) {
        myNeighbours = myGrid.getNeighbours(myCell);
        nonDiagNeighbours = myGrid.getNonDiagNeighbours(myCell);
        if (myCell.getCurrentType().equals(LIVE)) {
            handleLiveCell(myCell);
        } else {
            handleDeadCell(myCell);
        }
    }

    private void handleDeadCell(Cell myCell) {
        if (countNeigboursOfType(LIVE) == 3) {
            myCell.setNextType(LIVE);
        } else {
            myCell.setNextType(DEAD);
        }
    }

    private void handleLiveCell(Cell myCell) {
        if (countNeigboursOfType(LIVE) < 2 || countNeigboursOfType(LIVE) > 3) {
            myCell.setNextType(DEAD);
        } else {
            myCell.setNextType(LIVE);
        }
    }

    private int countNeigboursOfType(String type) {
        int count = 0;
        for (Cell neighbour : myNeighbours) {
            if (neighbour.getCurrentType().equals(type)) {
                count++;
            }
        }
        return count;
    }

    public void setColor(Cell myCell, CellGrid myGrid) {
        if (myCell.getCurrentType().equals(DEAD)) {
            myCell.setFill(Color.BLACK);
        } else if (myCell.getCurrentType().equals(LIVE)) {
            myCell.setFill(Color.WHITE);
        }
    }

    void setStatesInMap(Cell myCell) {
        return;
    }

    @Override
    void setBGStatesInMap(BackgroundCell myBGCell) {
        return;
    }
}
