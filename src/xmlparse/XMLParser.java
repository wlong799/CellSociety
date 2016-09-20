package xmlparse;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;

/**
 * Parses XML files containing initial Cell Society game information. Stores the
 * information from the file, to be easily accessible by other classes that need
 * it.
 *
 * @author Will Long
 */
public class XMLParser {

    public static void main(String argv[]) {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            GameInfoHandler handler = new GameInfoHandler();

            saxParser.parse(new File("data/text.xml"), handler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
