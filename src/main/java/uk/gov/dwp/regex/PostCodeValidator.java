package uk.gov.dwp.regex;

import java.util.Locale;

/**
 * <p>PostCodeValidator class.</p>
 *
 * @version $Id: $Id
 */
public class PostCodeValidator {
  private static final String POSTCODE_REGEX =
      "(^[A-Z&&[^QVX]]([A-Z&&[^IJZ]][0-9]([0-9]?|[ABEHMNPRVWXY]?)|[0-9]"
          + "([0-9]?|[ABCDEFGHJKPSTUW]?))[0-9][A-Z&&[^CIKMOV]]{2}$)";
  private static final String BFPO_REGEX = "(^(BFPO)[ ]?[0-9]{1,4}$)";
  private static final String NI_AREA_CODE = "BT";
  private static final String BFPO = "BFPO";

  private String outwardCode;
  private String inwardCode;

  /**
   * <p>Constructor for PostCodeValidator.</p>
   */
  public PostCodeValidator() {
    // plain old class
  }

  /**
   * Constructor takes a sting input and validates it prior to setting the internal elements.
   *
   * @param inputPostCode as per the variable name!!!
   * @throws uk.gov.dwp.regex.InvalidPostcodeException if the inputPostCode is not in a valid format
   */
  public PostCodeValidator(String inputPostCode) throws InvalidPostcodeException {
    if (!setPostCode(inputPostCode)) {
      throw new InvalidPostcodeException(String.format("'%s' is invalid", inputPostCode));
    }
  }

  /**
   * Validates the input to ensure that it's not null, it's not empty and it is of the correct
   * format.
   *
   * @param inputPostCode as per the variable name!!!
   * @return format correct T/F
   */
  public static boolean validateInput(String inputPostCode) {
    return null != inputPostCode
        && !inputPostCode.isEmpty()
        && (reformatInput(inputPostCode).matches(POSTCODE_REGEX)
        || reformatInput(inputPostCode).matches(BFPO_REGEX));
  }

  /**
   * A private function to standardise the input before validation.
   *
   * <p>Called by: validateInput setPostCode
   *
   * @param inputPostCode as per the variable name!!!
   * @return String reformatted input (stripped spaces and changed to uppercase)
   */
  private static String reformatInput(String inputPostCode) {
    return inputPostCode.replaceAll("[ ]", "").toUpperCase(Locale.ROOT);
  }

  /**
   * Sets the postcode object given that the input fulfills criteria.
   *
   * @param inputPostCode as per the variable name!!!
   * @return boolean set correctly T/F
   */
  public boolean setPostCode(String inputPostCode) {
    boolean returnValue = validateInput(inputPostCode);
    if (returnValue) {
      String reformattedInput = reformatInput(inputPostCode);
      int length = reformattedInput.length();
      if (reformattedInput.matches(BFPO_REGEX)) {
        outwardCode = BFPO;
        inwardCode = reformattedInput.substring(BFPO.length());
      } else {
        inwardCode = reformattedInput.substring(length - 3);
        outwardCode = reformattedInput.substring(0, length - 3);
      }
    }
    return returnValue;
  }

  /**
   * Function to return the full postcode.
   *
   * @return String
   */
  public String returnFullPostcode() {
    return String.format("%s %s", outwardCode, inwardCode);
  }

  /**
   * Function to return the Outward code of the post code (the fist bit before the space).
   *
   * @return String
   */
  public String returnOutwardCode() {
    return outwardCode;
  }

  /**
   * Function to return the Inward code of the post code (the last bit after the space).
   *
   * @return String
   */
  public String returnInwardCode() {
    return inwardCode;
  }

  /**
   * Function to return the Area code of the post code (the fist letters of the Outward code).
   *
   * @return String
   */
  public String returnArea() {
    String returnString;
    if (isBFPO()) {
      returnString = null;

    } else if ('A' <= outwardCode.charAt(1) && outwardCode.charAt(1) <= 'Z') {
      returnString = outwardCode.substring(0, 2);

    } else {
      returnString = outwardCode.substring(0, 1);
    }

    return returnString;
  }

  /**
   * Function to return the District code of the post code (the fist numbers(and possibly a letter)
   * of the Outward code).
   *
   * @return String
   */
  public String returnDistrict() {
    String returnString;
    if (isBFPO()) {
      returnString = null;

    } else if ('A' <= outwardCode.charAt(1) && outwardCode.charAt(1) <= 'Z') {
      returnString = outwardCode.substring(2);

    } else {
      returnString = outwardCode.substring(1);
    }

    return returnString;
  }

  /**
   * Function to return the Sector code of the post code (the fist Number of the Inward code).
   *
   * @return String
   */
  public String returnSector() {
    return isBFPO() ? null : inwardCode.substring(0, 1);
  }

  /**
   * Function to return the Unit code of the post code (the last two letters of the Inward code).
   *
   * @return String
   */
  public String returnUnit() {
    return isBFPO() ? null : inwardCode.substring(1);
  }

  /**
   * Function to return if the postcode is from Northern Ireland or not.
   *
   * @return boolean Northern Irish postcode T/F
   */
  public boolean isNorthernIreland() {
    return !isBFPO() && returnArea().contains(NI_AREA_CODE);
  }

  /**
   * <p>isBFPO.</p>
   *
   * @return a boolean
   */
  public boolean isBFPO() {
    return returnOutwardCode().contains(BFPO);
  }
}
