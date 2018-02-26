package uk.gov.dwp.regex;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PostCodeValidatorTest {
    private PostCodeValidator instance = new PostCodeValidator();

    @Test
    public void validateInputReturnsFalseWhenGivenStringInIncorrectAlphaNumericFormat() {
        assertFalse(PostCodeValidator.validateInput("12345678"));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenNull() {
        assertFalse(PostCodeValidator.validateInput(null));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenEmptyString() {
        assertFalse(PostCodeValidator.validateInput(""));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA9A9AA() {
        assertTrue(PostCodeValidator.validateInput("AA9B9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA9A9AAWithSpace() {
        assertTrue(PostCodeValidator.validateInput("AA9B 9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA9A9AA() {
        assertTrue(PostCodeValidator.validateInput("A9B9DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA99AA() {
        assertTrue(PostCodeValidator.validateInput("A99DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenA999AA() {
        assertTrue(PostCodeValidator.validateInput("A999DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA99AA() {
        assertTrue(PostCodeValidator.validateInput("AA99DD"));
    }

    @Test
    public void validateInputReturnsTrueWhenGivenAA999AA() {
        assertTrue(PostCodeValidator.validateInput("AA999DD"));
    }

    @Test
    public void validateInputReturnsFalseWhenGivenAA99A9AA() {
        assertFalse(PostCodeValidator.validateInput("AA99B9DD"));
    }

    @Test
    public void setPostCodeSetsInwardTo9AAWhenGivenAA999AA() {
        instance.setPostCode("AA999DD");
        assertEquals("9DD", instance.returnInwardCode());
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
        instance.setPostCode("AA99 9DD");
        assertEquals("9DD", instance.returnInwardCode());
    }

    @Test
    public void setPostCodeSetsOutwardToAA99WhenGivenAA999AAWithSpace() {
        instance.setPostCode("AA99 9DD");
        assertEquals("AA99", instance.returnOutwardCode());
    }

    @Test
    public void throwsAFormatError() {
        try {
            PostCodeValidator localInstance = new PostCodeValidator("bad-bad-postcode");
            fail("should always throw an error");

        } catch (InvalidPostcodeException e) {
            assertThat("catch error correctly", e.getMessage(), containsString("bad-bad-postcode"));
        }
    }

    @Test
    public void setPostCodeSetsOutwardToAA9AWhenGivenAA9A9AAWithSpace() {
        instance.setPostCode("AA9B 9DD");
        assertEquals("AA9B", instance.returnOutwardCode());
    }

    @Test
    public void returnFullPostCodeReturnsAA9A9AAWhenConstructedWithAA9A9AA() {
        instance.setPostCode("AA9B9DD");
        assertEquals("AA9B 9DD", instance.returnFullPostcode());
    }

    @Test
    public void returnAreaReturnsAAWhenGivenAA9A9AAWithSpace() {
        instance.setPostCode("AA9B 9DD");
        assertEquals("AA", instance.returnArea());
    }

    @Test
    public void returnAreaReturnsAWhenGivenA9A9AAWithSpace() {
        instance.setPostCode("A9B 9DD");
        assertEquals("A", instance.returnArea());
    }

    @Test
    public void returnDistrictReturns9AWhenGivenAA9A9AAWithSpace() {
        instance.setPostCode("AA9B 9DD");
        assertEquals("9B", instance.returnDistrict());
    }

    @Test
    public void returnDistrictReturns9WhenGivenAA99AAWithSpace() {
        instance.setPostCode("A9 9DD");
        assertEquals("9", instance.returnDistrict());
    }

    @Test
    public void returnSectorReturns0WhenGivenAA9A0AAWithSpace() {
        instance.setPostCode("AA9B 0DD");
        assertEquals("0", instance.returnSector());
    }

    @Test
    public void returnUnitReturnsAAWhenGivenAA9A9AAWithSpace() {
        instance.setPostCode("AA9B 9DD");
        assertEquals("DD", instance.returnUnit());
    }

    @Test
    public void returnTrueIfAreaSetToBT() {
        instance.setPostCode("BT9B 9DD");
        assertTrue(instance.isNorthernIreland());
    }

    @Test
    public void returnFalseIfAreaNotBT() {
        instance.setPostCode("AA9B 9DD");
        assertFalse(instance.isNorthernIreland());
    }

    @Test
    public void givenQVXInTheFirstPositionReturnsFalse() {
        assertFalse(instance.setPostCode("QA9B 9DD"));
    }

    @Test
    public void givenIJZInTheSecondPositionReturnsFalse() {
        assertFalse(instance.setPostCode("AI9B 9DD"));
    }

    @Test
    public void givenILMNOQRVXYZInTheThirdPositionReturnsFalse() {
        assertFalse(instance.setPostCode("A9L 9DD"));
    }

    @Test
    public void givenCDFGIJKLOQSTUZInTheFourthPositionReturnsFalse() {
        assertFalse(instance.setPostCode("AI9C 9DD"));
    }

    @Test
    public void givenCIKMOVInTheLastPositionReturnsFalse() {
        assertFalse(instance.setPostCode("AI9C 9DK"));
    }

    @Test
    public void givenCIKMOVInThePenultimatePositionReturnsFalse() {
        assertFalse(instance.setPostCode("AI9C 9KD"));
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
        instance.setPostCode("BFPO 123");
        assertTrue(instance.isBFPO());
    }

    @Test
    public void validate_ReturnOutwardCode_Returns_BFPO_Given_A_Valid_BFPOCode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertEquals("BFPO", instance.returnOutwardCode());
    }

    @Test
    public void validate_ReturnInwardCode_Returns_123_Given_BFPO_123() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertEquals("123", instance.returnInwardCode());
    }

    @Test
    public void validate_returnArea_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertNull(instance.returnArea());
    }

    @Test
    public void validate_returnDistrict_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertNull(instance.returnDistrict());
    }

    @Test
    public void validate_returnSector_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertNull(instance.returnSector());
    }

    @Test
    public void validate_returnUnit_Returns_Null_Given__A_Valid_BFPOCode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO 123");
        assertNull(instance.returnUnit());
    }

    @Test
    public void validate_returnFullPostcode_Returns_BFPO_123_Given__BFPO123() throws InvalidPostcodeException {
        instance.setPostCode("BFPO123");
        assertEquals("BFPO 123", instance.returnFullPostcode());
    }

    @Test
    public void validate_BFPOPostcode_Is_Not_NI_Postcode() throws InvalidPostcodeException {
        instance.setPostCode("BFPO123");
        assertFalse("BFPO should not be seen as NI postcode as we don't know!", instance.isNorthernIreland());
    }
}