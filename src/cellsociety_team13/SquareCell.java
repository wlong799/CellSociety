package cellsociety_team13;

import java.util.HashMap;

import com.sun.javafx.geom.Shape;

public class SquareCell extends Cell {


	public SquareCell(String cellType, double xPos, double yPos, double width, double height, int row, int col) {
		super(cellType, xPos, yPos, width, height, row, col);
		// TODO Auto-generated constructor stub
		setFill(DEFAULT_COLOR);

		myRow = row;
		myCol = col;
		currentType = cellType;
		nextType = cellType;
		currentState = new HashMap<>();
		nextState = new HashMap<>();
	}
/**
	@Override
	public Shape impl_configShape() {
		// TODO Auto-generated method stub
		return null;
	}
*/
}
