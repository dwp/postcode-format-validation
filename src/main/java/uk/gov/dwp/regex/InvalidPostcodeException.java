package uk.gov.dwp.regex;

/**
 * <p>InvalidPostcodeException class.</p>
 *
 * @version $Id: $Id
 */
public class InvalidPostcodeException extends Exception {
  /**
   * <p>Constructor for InvalidPostcodeException.</p>
   *
   * @param input a {@link java.lang.String} object
   */
  public InvalidPostcodeException(String input) {
    super(input);
  }
}
