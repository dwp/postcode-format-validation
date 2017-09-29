import gov.dwp.utilities.formatvalidation.InvalidPostcodeException;
import gov.dwp.utilities.formatvalidation.PostCodeValidator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PostCodeValidatorTest extends PostCodeValidator {

    @Test
    public void validateInputReturnsFalseWhenGivenStringInIncorrectAlphaNumericFormat() {
        assertFalse(validateInput("12345678"));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenNull() {
        assertFalse(validateInput(null));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenEmptyString() {
        assertFalse(validateInput(""));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA9A9AA() {
        assertTrue(validateInput("AA9B9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA9A9AAWithSpace() {
        assertTrue(validateInput("AA9B 9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA9A9AA() {
        assertTrue(validateInput("A9B9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA99AA() {
        assertTrue(validateInput("A99DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA999AA() {
        assertTrue(validateInput("A999DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA99AA() {
        assertTrue(validateInput("AA99DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA999AA() {
        assertTrue(validateInput("AA999DD"));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenAA99A9AA() {
        assertFalse(validateInput("AA99B9DD"));
    }

    @Test
    public void setPostCodeSetsInwardTo9AAWhenGivenAA999AA() {
        setPostCode("AA999DD");
        assertEquals("9DD", returnInwardCode());
    }

    @Test
    public void setPostCodeSetsOutwardToAA99WhenGivenAA999AA() throws InvalidPostcodeException {
        PostCodeValidator localPostCode = new PostCodeValidator("AA999DD");
        assertEquals("AA99", localPostCode.returnOutwardCode());
    }

    @Test(expected = InvalidPostcodeException.class)
    public void constructPostCodeThrowsErrorWhenGiven12345678() throws InvalidPostcodeException {
        new PostCodeValidator("12345678");
    }

    @Test
    public void setPostCodeSetsInwardTo9AAWhenGivenAA999AAWithSpace() {
        setPostCode("AA99 9DD");
        assertEquals("9DD", returnInwardCode());
    }

    @Test
    public void setPostCodeSetsOutwardToAA99WhenGivenAA999AAWithSpace() {
        setPostCode("AA99 9DD");
        assertEquals("AA99", returnOutwardCode());
    }

    @Test
    public void setPostCodeSetsOutwardToAA9AWhenGivenAA9A9AAWithSpace() {
        setPostCode("AA9B 9DD");
        assertEquals("AA9B", returnOutwardCode());
    }

    @Test
    public void returnFullPostCodeReturnsAA9A9AAWhenConstructedWithAA9A9AA() {
        setPostCode("AA9B9DD");
        assertEquals("AA9B 9DD", returnFullPostcode());
    }

    @Test
    public void returnAreaReturnsAAWhenGivenAA9A9AAWithSpace() {
        setPostCode("AA9B 9DD");
        assertEquals("AA", returnArea());
    }

    @Test
    public void returnAreaReturnsAWhenGivenA9A9AAWithSpace() {
        setPostCode("A9B 9DD");
        assertEquals("A", returnArea());
    }

    @Test
    public void returnDistrictReturns9AWhenGivenAA9A9AAWithSpace() {
        setPostCode("AA9B 9DD");
        assertEquals("9B", returnDistrict());
    }

    @Test
    public void returnDistrictReturns9WhenGivenAA99AAWithSpace() {
        setPostCode("A9 9DD");
        assertEquals("9", returnDistrict());
    }

    @Test
    public void returnSectorReturns0WhenGivenAA9A0AAWithSpace() {
        setPostCode("AA9B 0DD");
        assertEquals("0", returnSector());
    }

    @Test
    public void returnUnitReturnsAAWhenGivenAA9A9AAWithSpace() {
        setPostCode("AA9B 9DD");
        assertEquals("DD", returnUnit());
    }

    @Test
    public void returnTrueIfAreaSetToBT() {
        setPostCode("BT9B 9DD");
        assertTrue(isNorthernIreland());
    }

    @Test
    public void returnFalseIfAreaNotBT() {
        setPostCode("AA9B 9DD");
        assertFalse(isNorthernIreland());
    }

    @Test
    public void givenQVXInTheFirstPositionReturnsFalse() {
        assertFalse(setPostCode("QA9B 9DD"));
    }

    @Test
    public void givenIJZInTheSecondPositionReturnsFalse() {
        assertFalse(setPostCode("AI9B 9DD"));
    }

    @Test
    public void givenILMNOQRVXYZInTheThirdPositionReturnsFalse() {
        assertFalse(setPostCode("A9L 9DD"));
    }

    @Test
    public void givenCDFGIJKLOQSTUZInTheFourthPositionReturnsFalse() {
        assertFalse(setPostCode("AI9C 9DD"));
    }

    @Test
    public void givenCIKMOVInTheLastPositionReturnsFalse() {
        assertFalse(setPostCode("AI9C 9DK"));
    }

    @Test
    public void givenCIKMOVInThePenultimatePositionReturnsFalse() {
        assertFalse(setPostCode("AI9C 9KD"));
    }

    /*********************************************************************************\
     BFPO SECTION OF TESTS
     \*********************************************************************************/
    @Test
    public void validateShortBFPOIsValid() {
        assertTrue(PostCodeValidator.validateInput("BFPO 1"));
    }

    @Test
    public void validateLongestBFPOIsValid() {
        assertTrue(PostCodeValidator.validateInput("BFPO 1234"));
    }

    @Test
    public void validateBFPOIsInvalidWithNoSpace() {
        assertTrue(PostCodeValidator.validateInput("BFPO123"));
    }

    @Test
    public void validateTooLongBFPOIsInvalid() {
        assertFalse(PostCodeValidator.validateInput("BFPO 12345"));
    }

    @Test
    public void validateTooShortBFPOIsInvalid() {
        assertFalse(PostCodeValidator.validateInput("BFPO "));
    }

    @Test
    public void validate_isBFPO_Returns_True_Given_A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertTrue(isBFPO());
    }

    @Test
    public void validate_ReturnOutwardCode_Returns_BFPO_Given_A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertEquals("BFPO", returnOutwardCode());
    }

    @Test
    public void validate_ReturnInwardCode_Returns_123_Given_BFPO_123() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertEquals("123", returnInwardCode());
    }

    @Test
    public void validate_returnArea_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertNull(returnArea());
    }

    @Test
    public void validate_returnDistrict_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertNull(returnDistrict());
    }

    @Test
    public void validate_returnSector_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertNull(returnSector());
    }

    @Test
    public void validate_returnUnit_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        setPostCode("BFPO 123");
        assertNull(returnUnit());
    }

    @Test
    public void validate_returnFullPostcode_Returns_BFPO_123_Given__BFPO123() throws InvalidPostcodeException {
        setPostCode("BFPO123");
        assertEquals("BFPO 123", returnFullPostcode());
    }

    @Test
    public void validate_BFPOPostcode_Is_Not_NI_Postcode() throws InvalidPostcodeException {
        setPostCode("BFPO123");
        assertFalse("BFPO should not be seen as NI postcode as we don't know!", isNorthernIreland());
    }
}