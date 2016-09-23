package cellsociety_team13;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Cell class extends Rectangle to be drawn to screen. Contains information on
 * its type and state, in a general way that can easily be extended to many
 * different situations. The Rule class for a given name is responsible for
 * properly interpreting and setting the Cell's type and state.
 */
public class Cell extends Rectangle {
	private static final Color DEFAULT_COLOR = Color.GRAY;

	private String currentType, nextType;
	private Map<String, Integer> currentState, nextState;

	public Cell(String cellType, double xPos, double yPos, double width, double height) {
		super(xPos, yPos, width, height);
		setFill(DEFAULT_COLOR);

		currentType = cellType;
		nextType = cellType;
		currentState = new HashMap<>();
		nextState = new HashMap<>();
	}

	public void initalizeState(Map<String, Integer> initialStates) {
		for (String stateName : initialStates.keySet()) {
			int stateVal = nextState.get(stateName);
			currentState.put(stateName, stateVal);
			nextState.put(stateName, stateVal);
		}
	}

	public void stepToNextState() {
		currentType = nextType;
		currentState = new HashMap<>();
		for (String stateName : nextState.keySet()) {
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
