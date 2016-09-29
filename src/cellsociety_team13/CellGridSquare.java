package cellsociety_team13;

import java.util.List;

import rule.Rule;

public class CellGridSquare extends CellGrid {

	
	public CellGridSquare(double xPos, double yPos, double drawWidth, double drawHeight, int gridWidth, int gridHeight,
			List<String> initialCellTypes, Rule rule, List<GameParameter> initialParameters) {
		super(xPos, yPos, drawWidth, drawHeight, gridWidth, gridHeight, initialCellTypes, rule, initialParameters);
		// TODO Auto-generated constructor stub
	}

	public void addItemsToGrid(int gridWidth, int gridHeight, List<String> initialCellTypes) {
		for (int row = 0; row < gridHeight; row++){
 			for (int col = 0; col < gridWidth; col++){
 				int arrayPos = row*gridWidth + col;
				double cellXPos = row * drawCellWidth;
				double cellYPos = col * drawCellHeight;
 				Cell cell = new Cell(initialCellTypes.get(arrayPos), cellXPos, cellYPos,
								     drawCellWidth, drawCellHeight, row, col);
 				cells.add(cell);
 				getChildren().add(cell);
 			}
 		}
	}
	
	

}
