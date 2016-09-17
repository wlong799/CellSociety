CompSci 308: Cell Society
===================
**Team 13**  
Will Long (wpl5)  
John Martin (jfm41)  
Lucia Martos Jimenez (lm260)

Specification
=============
### Introduction

<img src="Logo.png" alt="Cell Society Team 13 Logo" width = 150>

In this program, our team is attempting to create a JavaFX program that can
run any general cellular automata simulation. The program reads in a set of
game rules from an XML file, and draws the grid of cells to screen. When 
specified by the user (i.e. through a step or run button), the simulation 
updates all cells simultaneously, and redraws them in their new state. 
The program can also accept new XML files as game input, and allows for users to
adjust parameters on the go.

The goal of our design is to make the code as flexible as possible, especially
in terms of accepting new game rules. Since we do not know the possible games
we may be asked to simulate, it is necessary to write code carefully to be
easily extensible to a wide variety of cases. Building on this point, it should
be easy to add a new game, requiring addition of only a single new class and the
relevant XML file.

As this is a team development project, it is also necessary to code in a way 
that is clean and consistent. We want to modularize the code as much as possible
so that we can each work on separate parts of the project without having to 
worry about how other parts are being implemented. Each class is responsible for
a specific part of the program (e.g. XML file parsing, game rule evaluation, 
cell state, etc.), and only gives access to its variables as necessary (e.g. 
through get/set methods). The functions that each class will provide as 
publically accessible should be determined before coding, so each team member
knows how the class they are building should interact with the others.

### Overview
![alt text][overview]
[overview]: overview.png "Overview"

### User Interface
All user interaction will be handled by CellSocietyManager, which is also 
responsible for handling interactions between the different classes in the 
simulation. There will be a screen at the top containing the grid of cells, and
there will be a bottom panel with several things the user can interact with:

* A box to input a String, for the user to write the name of the XML game file.
* A button that says “XML File Submit.” Upon pressing this button, the typed 
String will be taken in. If there is not a file with this name in the data
folder, an error should be reported to the user. An error will also be reported
in case of invalid file formatting.
* There will be two buttons (Next and Run), which control iteration through the
simulation. Each time the user presses Next, the simulation will move one period
forwards. When the user presses Run, the simulation will continue to iterate
until the user presses the button again.
* There will be a slider for each adjustable parameter within the simulation,
which will allow for the user to edit the parameters passed to the simulation
algorithm. Any time a parameter is changed, its updated value will be sent to
the Rule class, which contains logic for a specific game.

![alt text][userInt]
[userInt]: userInt.png "Intended User Interface"

### Design Details 
Main: This class will simply launches the game, this is a generic class that can be used for any interface.  

CellSocietyManager: When starting up for the first time, there will be a generic screen with the cell grid and the XML file inputter and then when the XML file is read and the simulation is specified a particular method will be called to draw out the required environment (with the specific parameters etc). A different method will need to be created for each new implementation of the game. CellSocietyManager interacts with the XML reader class when the “XML File submit” button is pressed. CellSocietyManager will make an instance of the cellGrid and this will update and change the states of the cells in the grid. 

CellGrid: The cell grid class is simply an array of Cells (obj) which extends Group. CellGrid interacts with the Rule class by making an instance of it. The cell grid will be in charge of calling the for the Rule class to calculate the next state of the cells and then change the state of the cells.

Rule: This is an abstract class which has different methods with the logic of the particular simulation, in this class we can change the parameters and evaluate the state of the cell. Methods included:
evaluate which takes in cell and the cell grid
setParameter method which takes in the name of the parameter and the value of it, this will be stored in a HashMap with the rest of the parameters
As rule is an abstract class we can then create different classes for the different simulations, this will give a lot of flexibility when making an instance of Rule in the CellGrid. We are also going to make a HashMap that maps each of possible states of the cell to the index number it will be represented by.  

Cell: The cell class extends Rectangle which contains the current state of the cell, the next state of the cell, the cell type and any other type of useful information.

XMLGameReader: Accepts a String filename for its constructor, and parses the XML file at that location. Determines all necessary variables that the CellGrid class will need to create the game, set the rules, and initialize the starting cells and parameters.

The way that the game is designed should make it relatively easy to add new simulations of the game as well as new parameters. 


### Design Considerations
**Implementation of Different Algorithms**  
The group discussed a number of potential implementations for the various game
algorithms required by this project. After due consideration we elected to 
use an abstract Rule superclass, with each specific game algorithm created as a
subclass that overrides its abstract method for evaluating Cells. This layout 
should enable us to quickly implement new rules, independent of the rest of the
program (provided the required core elements of the program remain the same).

The alternative that was discussed was using an abstract Cell type with
subclasses holding their own rules (e.g FireCell would have different update
rules than a TreeCell). However, we chose to avoid this method, deciding that
it would be more complex to implement than the Rule class.

This decision enables greater flexibility in implementing many different 
algorithms. One downside is that the algorithms will need to have the same core
basis/interaction, but for the scope of this project this is suitable.

**Defining Cell Types**  
The type of a Cell will define the meaning of values stored in its state. It is 
up to the Rule class to properly understand a Cell's state variables. We 
discussed different options to define Cell types, mostly centered on using 
integers vs. Strings. The challenge in both cases is the potential to input an 
invalid String or integer. To negate this, we decided it best to utilize HashMap
stored within the Rule which can be used to understand the Cell states (i.e. it
maps the name of the state to the appropriate location of the state in the
Cell).

We debated putting the HashMaps within each Cell, but for larger simulations it
was decided this could cause a memory bloat as HashMaps take up more space than
arrays, and we would need to create two per cell (for both current and next
states).

### Team Responsibilities
**Will**  
Primary responsibility is XML file parsing. This includes creating the XML files
in an appropriate format, and a class that can read these files and determine
the proper variables from them (i.e. game type/name, initial parameters/cell 
states).

**Lucia**  
Primary responsibility is creation of abstract Rule class, as well as 
implementing the various subclasses for each different game type.

**John**   
Primary responsibility is creation of CellGrid and Cell classes. CellGrid is 
responsible for interacting with the Rule class, and using it to update all 
Cells contained within the grid.


Use Cases
=========
1. Apply rules to all cells within CellGrid (both edge and middle)
// Assume game is already loaded
// User triggers update by pressing either the next button or run button
void update() {
	for (Cell cell : cellGrid) {
		rule.evaluate(cell, cellGrid);
}
for (Cell cell : cellGrid) {
	cell.updateState();
}
}

2. Move to next generation
cellGrid.update();

3. Set simulation parameter
// User has adjusted value of parameter with String name to int value through UI
cellGrid.getRule().setParameter(name, value);

// and the relevant method in Rule...
setParameter(String name, int value) {
	parameterMap.put(name, value);
}

4. Switch to new game from file input
// user inputs filename and hits submit
// method in CellGrid
void newGame(String filename) {
	// creates new reader which parses file to determine values
	XMLGameReader gameReader = new XMLGameReader(filename);
	setSize(gameReader.getGameWidth(), gameReader.getGameHeight());
	setRule(gameReader.getRuleName());
	Map<String, Integer> params = gameReader.getInitialParameters();
	for (String name : params.keySet()) {
		getRule().setParameter(name, params.get(name));
	}
	initializeCells(gameReader.getInitialCellStates());
}
