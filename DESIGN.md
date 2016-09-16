CompSci 308: Cell Society
===================
Team 13: Will Long (wpl5), John Martin (jfm41), Lucia Martos Jimenez (lm260)

Specification
=============
### Introduction
![alt text][logo]

[logo]: (Logo.png "CellSociety Team 13" = 250x)

In this program, our team is attempting to create a JavaFX program that can simulate any general Cellular Automata simulation. The program reads in a set of game rules from an XML file, and draws the grid of cells to screen. When specified (i.e. through a Step or Run button), the simulation updates all cells simultaneously, and redraws them in their new state. The program can also accept new XML files as game input, and allows for users to adjust parameters on the go.

The goal of our design is to make the code as flexible as possible in terms of accepting new game rules. Adding a new game should be easy, only requiring addition of a new class describing the rules, and an XML file describing the initial setup. As we are developing as a team, we also want to modularize the code as much as possible, so that we can work on separate parts of the project without having to worry about how other parts are being implemented. Each class is responsible for a specific part of the program (e.g. XML file parsing, game rule evaluation, cell state, etc.), and only gives access to its variables as necessary (e.g. through get/set methods).

Classes:

* Main
	* Boilerplate
	* Launch
* CellSocietyManager
	* CellGrid
	* XML File
	* Step
	* Run
* XMLReader
	* Name (Class)
	* Parameters
	* Dimensions, Initial States
* CellGrid
	* Array of Cell Objects
	* Rule Object
	* Evaluate States
	* Update States
* Cell
	* CellType (int)
	* Color
	* Size
	* CurrentStates (int [ ])
	* NextStates (int [ ])
* Rule
	* (abstract)
	* Evaluate
	* Subclasses for Specific Algorithms
	* Parameters (Map: String name → Val)

### Overview
Overview:
![alt text][overview]

[overview]: overview.png "Overview"

### User Interface
All the user interaction will happen the the CellSocietyManager, there will be a screen at the top with the cell grid and in the bottom there will be several things the user can interact with:

* A string inputter where the user can write the name of the XML file that is going to be read.
* A button that says “XML file submit” and upon pressing this button the typed string will be taken in. If there is not a file with this name in the data folder there should be an error telling the user. In the case that the file does not contain a valid form of data we will also report an error on the screen and ask for another file name.
* The “next” and “run” buttons that determine the way of iterating through the algorithm (these will be methods within the cell grid class)
* There will be a set of scrollers that will determine the parameters of the algorithm. When a parameter is changed we will set the parameter in the “Rule” class (which contains the logic for a specific game). 

Appearance of the UI:
![alt text][userInt]

[userInt]: userInt.png "Intended User Interface"
### Design Details 
This section describes each component introduced in the Overview in detail (as well as any other sub-components that may be needed but are not significant to include in a high-level description of the program). It should describe how each component handles specific features given in the assignment specification, what resources it might use, how it collaborates with other components, and how each could be extended to include additional requirements (from the assignment specification or discussed by your team). Include the steps needed to complete the Use Cases below to help make your descriptions more concrete. Finally, justify the decision to create each component with respect to the design's key goals, principles, and abstractions. This section should go into as much detail as necessary to cover all your team wants to say.
### Design Considerations 
This section describes any issues which need to be addressed or resolved before attempting to devise a complete design solution. It should include any design decisions that the group discussed at length (include pros and cons from all sides of the discussion) as well as any assumptions or dependencies regarding the program that impact the overall design. This section should go into as much detail as necessary to cover all your team wants to say.

* Implementation of Different Algorithms:
	* The group discussed different potential implementations of the varying algorithms required in this project. After due consideration we elected to follow a pattern involving an abstract “Rule” superclass, with specific algorithms being stored as subclasses. With intelligent design and care this layout should enable us to implement new rules more quickly independent of the rest of the program (provided the required core elements of the program remain the same). The alternative that was discussed was using an abstract cell type with subclasses holding their own rules (for example, a fire cell would have different update rules to a tree cell), however, we chose to avoid this method to enable simpler updating of cell [display] parameters within the cell grid.
	* Whilst this decision does enable greater flexibility in implementing many different algorithms quickly and efficient, the downside is that the algorithms must have the same core basis/interaction (for the scope of this project this is suitable).
* Defining Cell Types:
	* Cell types will define what the parameters a cell has.
	* Discussed different options to define cell type (mostly centered on integers vs. strings). The challenge in both cases is the potential to input an invalid string or integer. To negate this (somewhat), we decided it best to utilise a hashmap stored within the rule which then interacts with the cell parameters in each cell.
	* Size considerations: we debated putting the hashmaps within each cell, but for larger simulations it was decided this could cause a memory bloat as hashmaps take up more space than arrays, and we would need to create two per cell (for both current and next states).

### Team Responsibilities
* Will - Primary responsibility is XML file parsing. This includes creating the XML files in an appropriate format, and a class that can read these files and determine the proper variables from them (i.e. game type/name, initial parameters/cell states).

* Lucia - Primary responsibility is creation of abstract Rule class, as well as implementing the various subclasses for each different game type.

* John - Primary responsibility is creation of CellGrid and Cell classes. CellGrid is responsible for interacting with the Rule class, and using it to update all Cells contained within the grid.



Use Cases
=========
