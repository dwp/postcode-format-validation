package uk.gov.dwp.regex;

public class InvalidPostcodeException extends Exception {
    public InvalidPostcodeException(String input) {
        super(input);
    }
}
