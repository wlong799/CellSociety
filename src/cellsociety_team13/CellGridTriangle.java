package cellsociety_team13;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import rule.Rule;

/**
 * CellGrid is responsible for holding the current Cells within the Cell Society
 * game. It stores the position of the cells and uses the specified Rule class
 * to update the Cells when specified.
 */

public class CellGridTriangle extends CellGrid {

	public CellGridTriangle(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight, List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters) {
		super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters);

	}

	public void addItemsToGrid(int gridWidth, int gridHeight, List<String> initialCellTypes) {
	for (int row = 0; row < gridHeight; row++){
			for (int col = 0; col < gridWidth; col++){
				int arrayPos = row*gridWidth + col;
			double cellXPos = row * drawCellWidth;
			double cellYPos = col * drawCellHeight;
			double[] xpoints = {cellXPos, cellXPos + drawCellWidth};
			double[] ypoints = {cellYPos, cellYPos + drawCellHeight};
				CellTriangle cell = new CellTriangle(initialCellTypes.get(arrayPos), xpoints,ypoints, row, col);
				//cells.add(cell);

				getChildren().add(cell);
			}
		}
}

	@Override
	public List<Cell> getNonDiagNeighbours(Cell myCell) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cell> getNeighbours(Cell myCell) {
		// TODO Auto-generated method stub
		return null;
	}
}

