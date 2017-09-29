package gov.dwp.utilities.formatvalidation;

public class InvalidPostcodeException extends Exception {
    public InvalidPostcodeException(String input) {
        super(input);
    }
}
