package cellsociety_team13;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashMap;
import java.util.Map;

/**
 * Cell class extends Rectangle to be drawn to screen. Contains information on
 * its type and state, in a general way that can easily be extended to many
 * different situations. The Rule class for a given name is responsible for
 * properly interpreting and setting the Cell's type and state.
 */
public class CellTriangle extends Polygon {
	protected static final Color DEFAULT_COLOR = Color.GRAY;

	protected String currentType, nextType;
	protected Map<String, Integer> currentState, nextState;
	protected int myRow, myCol;

	public CellTriangle(String cellType, double xPos, double yPos, double width, double height, int row, int col) {
		super();
		getPoints().addAll(xPos, yPos, xPos + width, yPos, xPos + width, yPos +width);
		setFill(DEFAULT_COLOR);

		myRow = row;
		myCol = col;
		currentType = cellType;
		nextType = cellType;
		currentState = new HashMap<>();
		nextState = new HashMap<>();
	}

	public int getMyCol() {
		return myCol;
	}

	public int getMyRow() {
		return myRow;
	}

	public void initalizeState(Map<String, Integer> initialStates) {
		for (String stateName : initialStates.keySet()) {
			int stateVal = nextState.get(stateName);
			currentState.put(stateName, stateVal);
			nextState.put(stateName, stateVal);
		}
	}

	public String getCurrentType() {
		return currentType;
	}

	public int getCurrentState(String stateName) {
		return currentState.get(stateName);
	}

	public int getNextState(String stateName) {
		return nextState.get(stateName);
	}
	
	public boolean containsNextState(String stateName) {
		return nextState.containsKey(stateName);
	}

	public void setCurrentState(String stateName, int value) {
		currentState.put(stateName, value);
	}

	public String getNextType() {
		return nextType;
	}
	
	public void stepToNextStateAndType() {
		currentType = nextType;
		nextType = null;
		for(String stateName :nextState.keySet()){
			int stateVal = nextState.get(stateName);
			currentState.put(stateName, stateVal);
		}
	}

	public void setNextType(String cellType) {
		nextType = cellType;
	}

	public void setNextState(String stateName, int stateVal) {
		nextState.put(stateName, stateVal);
	}

	public void removeNextState(String stateName) {
		if (nextState.containsKey(stateName)) {
			nextState.remove(stateName);
		}
	}

	public void removeAllNextStates() {
		nextState = new HashMap<>();
	}
}
