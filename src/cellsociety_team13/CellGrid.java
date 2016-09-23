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

public class CellGrid extends Group {
	private double drawWidth, drawHeight;
	private double drawCellWidth, drawCellHeight;

	private int gridWidth, gridHeight;
	private Cell[][] cells;
	private Rule rule;
	private boolean isRunning;
	
	public CellGrid(double xPos, double yPos, double drawWidth, double drawHeight,
					int gridWidth, int gridHeight, String[][] initialCellTypes, Rule rule) {
		setLayoutX(xPos);
		setLayoutY(yPos);
		this.drawWidth = drawWidth;
		this.drawHeight = drawHeight;
		drawCellWidth = drawWidth / gridWidth;
		drawCellHeight = drawHeight / gridHeight;

		this.gridWidth = gridWidth;
		this.gridHeight = gridHeight;
		cells = new Cell[gridHeight][gridWidth];
		for (int r = 0; r < gridHeight; r++) {
			for (int c = 0; c < gridWidth; c++) {
				addCell(initialCellTypes[r][c], r, c);
			}
		}

		this.rule = rule;
	}

	private void addCell(String cellType, int row, int col) {
		double xPos = col * drawCellWidth;
		double yPos = row * drawCellHeight;
		Cell cell = new Cell(cellType, xPos, yPos, drawCellWidth, drawCellHeight);
		getChildren().add(cell);
		cells[row][col] = cell;
	}
	
	public List<Cell> getCellsByType(String cellType){
		List<Cell> myCells = new ArrayList<>();
			for(int r = 0; r < gridHeight; r ++){
				for(int c = 0; c < gridWidth; c ++){
					if (cells[r][c].getCurrentType().equals(cellType)) {
						myCells.add(cells[r][c]);
					}
				}
			}
		return myCells;
	}
	
	public Cell getCell(int row, int col){
		return cells[r][c];
	}
	
	public void step(){
		rule.evaluateGrid(this);
	}
	
	public void run() throws InterruptedException{
		this.isRunning = true;
		while (isRunning){
			this.step();
			Thread.sleep(5000);
		}
	}
}
