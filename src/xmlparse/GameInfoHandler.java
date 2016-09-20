package xmlparse;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * Extends the DefaultHandler class, to properly interpret XML files formatted
 * to store Cell Society game information.
 *
 * @author Will Long
 */
public class GameInfoHandler extends DefaultHandler {
    private static final int REMOVE = -1, ADD = 1;

    private LinkedList<String> currentSection;

    private String title, ruleName, author;

    Map<String, Integer> parameterMap;
    Map<String, Integer> cellTypeMap;

    private boolean bfname, blname, bnname, bsalary;

    GameInfoHandler() {
        currentSection = new LinkedList<>();

        title = "";
        ruleName = "";
        author = "";

        parameterMap = new HashMap<>();
        cellTypeMap = new HashMap<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        updateCurrentSection(qName, ADD);
    }

    @Override
    public void endElement(String uri, String localName,
                           String qName) throws SAXException {
        updateCurrentSection(qName, REMOVE);
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (currentSection.size() == 0) {
            return;
        }
        String information = new String(ch, start, length);
        if (currentSection.getFirst().equals("MAIN")) {
            getMainInfo(information);
        } else if (currentSection.getFirst().equals("PARAMETER")) {
            getParameterInfo(information);
        } else if (currentSection.getFirst().equals("CELLTYPE")) {
            getCellTypeInfo(information);
        } else if (currentSection.getFirst().equals("GRID")) {
            getGridInfo(information);
        }
    }

    private void updateCurrentSection(String sectionName, int operation) {
        if (sectionName.equalsIgnoreCase("GAME")) {
            return;
        } else if (operation == ADD) {
            currentSection.addLast(sectionName.toUpperCase());
        } else if (operation == REMOVE) {
            currentSection.removeLast();
        }
    }

    private void getMainInfo(String information) {
        System.out.println("MAIN : " + information);
    }

    private void getParameterInfo(String information) {
        System.out.println("PARAMETER : " + information);
    }

    private void getCellTypeInfo(String information) {
        System.out.println("CELL TYPE : " + information);
    }

    private void getGridInfo(String information) {
        System.out.println("GRID : " + information);
    }


}
