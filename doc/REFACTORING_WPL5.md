Refactoring
===========
**Will Long (wpl5)**

For my refactoring, I edited the XML parsing class. The class had begun to grow
fairly large, with a lot of if-statements to handle the various sections of the
file. Functions were dealing with a diverse number of situations, so 
I decided to make a general XML parsing
class, and let each individual class implement its own way of parsing the file.
This allowed for the parsing of each section to be updated individually and it improved code
flexibility.

**Commits**
* [Added initial Parser classes](http://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team13/commit/35fc8eb2aed0666a3813467db2affdae0fd57c46)
* [Completed XML parsing changes](http://git.cs.duke.edu/CompSci308_2016Fall/cellsociety_team13/commit/076e44422312d6043f3decd78159724544e15ebc)