package xmlparser;

/**
 * Exception to be thrown in case of errors in XML parsing, due to incorrect formatting.
 */
public class XMLGameInfoException extends Exception {
    public XMLGameInfoException(String message) {
        super(message);
    }
}
