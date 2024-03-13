package uk.gov.dwp.regex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class PostCodeValidatorTest {

  private final PostCodeValidator instance = new PostCodeValidator();

  @Test
  public void validateInputReturnsFalseWhenGivenStringInIncorrectAlphaNumericFormat() {
    assertThat(PostCodeValidator.validateInput("12345678"))
        .isFalse();
  }

  @Test
  public void validateInputReturnsFalseWhenGivenNull() {
    assertThat(PostCodeValidator.validateInput(null))
        .isFalse();
  }

  @Test
  public void validateInputReturnsFalseWhenGivenEmptyString() {
    assertThat(PostCodeValidator.validateInput(""))
        .isFalse();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenAA9A9AA() {
    assertThat(PostCodeValidator.validateInput("AA9B9DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenAA9A9AAWithSpace() {
    assertThat(PostCodeValidator.validateInput("AA9B 9DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenA9A9AA() {
    assertThat(PostCodeValidator.validateInput("A9B9DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenA99AA() {
    assertThat(PostCodeValidator.validateInput("A99DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenA999AA() {
    assertThat(PostCodeValidator.validateInput("A999DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenAA99AA() {
    assertThat(PostCodeValidator.validateInput("AA99DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsTrueWhenGivenAA999AA() {
    assertThat(PostCodeValidator.validateInput("AA999DD"))
        .isTrue();
  }

  @Test
  public void validateInputReturnsFalseWhenGivenAA99A9AA() {
    assertThat(PostCodeValidator.validateInput("AA99B9DD"))
        .isFalse();
  }

  @Test
  public void setPostCodeSetsInwardTo9AAWhenGivenAA999AA() {
    instance.setPostCode("AA999DD");
    assertThat(instance.returnInwardCode())
        .isEqualTo("9DD");
  }

  @Test
  public void setPostCodeSetsOutwardToAA99WhenGivenAA999AA() throws InvalidPostcodeException {
    PostCodeValidator localPostCode = new PostCodeValidator("AA999DD");
    assertThat(localPostCode.returnOutwardCode())
        .isEqualTo("AA99");
  }

  @Test
  public void constructPostCodeThrowsErrorWhenGiven12345678() throws InvalidPostcodeException {
    assertThatThrownBy(() -> new PostCodeValidator("12345678"))
        .isInstanceOf(InvalidPostcodeException.class)
        .hasMessageContaining("'12345678' is invalid");
  }

  @Test
  public void setPostCodeSetsInwardTo9AAWhenGivenAA999AAWithSpace() {
    instance.setPostCode("AA99 9DD");
    assertThat(instance.returnInwardCode())
        .isEqualTo("9DD");
  }

  @Test
  public void setPostCodeSetsOutwardToAA99WhenGivenAA999AAWithSpace() {
    instance.setPostCode("AA99 9DD");
    assertThat(instance.returnOutwardCode())
        .isEqualTo("AA99");
  }

  @Test
  public void throwsAFormatError() {
    assertThatThrownBy(() -> new PostCodeValidator("bad-bad-postcode"))
        .isInstanceOf(InvalidPostcodeException.class)
        .hasMessageContaining("'bad-bad-postcode' is invalid");
  }

  @Test
  public void setPostCodeSetsOutwardToAA9AWhenGivenAA9A9AAWithSpace() {
    instance.setPostCode("AA9B 9DD");
    assertThat(instance.returnOutwardCode())
        .isEqualTo("AA9B");
  }

  @Test
  public void returnFullPostCodeReturnsAA9A9AAWhenConstructedWithAA9A9AA() {
    instance.setPostCode("AA9B9DD");
    assertThat(instance.returnFullPostcode())
        .isEqualTo("AA9B 9DD");
  }

  @Test
  public void returnAreaReturnsAAWhenGivenAA9A9AAWithSpace() {
    instance.setPostCode("AA9B 9DD");
    assertThat(instance.returnArea())
        .isEqualTo("AA");
  }

  @Test
  public void returnAreaReturnsAWhenGivenA9A9AAWithSpace() {
    instance.setPostCode("A9B 9DD");
    assertThat(instance.returnArea())
        .isEqualTo("A");
  }

  @Test
  public void returnDistrictReturns9AWhenGivenAA9A9AAWithSpace() {
    instance.setPostCode("AA9B 9DD");
    assertThat(instance.returnDistrict())
        .isEqualTo("9B");
  }

  @Test
  public void returnDistrictReturns9WhenGivenAA99AAWithSpace() {
    instance.setPostCode("A9 9DD");
    assertThat(instance.returnDistrict())
        .isEqualTo("9");
  }

  @Test
  public void returnSectorReturns0WhenGivenAA9A0AAWithSpace() {
    instance.setPostCode("AA9B 0DD");
    assertThat(instance.returnSector())
        .isEqualTo("0");
  }

  @Test
  public void returnUnitReturnsAAWhenGivenAA9A9AAWithSpace() {
    instance.setPostCode("AA9B 9DD");
    assertThat(instance.returnUnit())
        .isEqualTo("DD");
  }

  @Test
  public void returnTrueIfAreaSetToBT() {
    instance.setPostCode("BT9B 9DD");
    assertThat(instance.isNorthernIreland())
        .isTrue();
  }

  @Test
  public void returnFalseIfAreaNotBT() {
    instance.setPostCode("AA9B 9DD");
    assertThat(instance.isNorthernIreland())
        .isFalse();
  }

  @Test
  public void givenQVXInTheFirstPositionReturnsFalse() {
    assertThat(instance.setPostCode("QA9B 9DD"))
        .isFalse();
  }

  @Test
  public void givenIJZInTheSecondPositionReturnsFalse() {
    assertThat(instance.setPostCode("AI9B 9DD"))
        .isFalse();
  }

  @Test
  public void givenILMNOQRVXYZInTheThirdPositionReturnsFalse() {
    assertThat(instance.setPostCode("A9L 9DD"))
        .isFalse();
  }

  @Test
  public void givenCDFGIJKLOQSTUZInTheFourthPositionReturnsFalse() {
    assertThat(instance.setPostCode("AI9C 9DD"))
        .isFalse();
  }

  @Test
  public void givenCIKMOVInTheLastPositionReturnsFalse() {
    assertThat(instance.setPostCode("AI9C 9DK"))
        .isFalse();
  }

  @Test
  public void givenCIKMOVInThePenultimatePositionReturnsFalse() {
    assertThat(instance.setPostCode("AI9C 9KD"))
        .isFalse();
  }

  @Test
  public void validateShortBFPOIsValid() {
    assertThat(PostCodeValidator.validateInput("BFPO 1"))
        .isTrue();
  }

  @Test
  public void validateLongestBFPOIsValid() {
    assertThat(PostCodeValidator.validateInput("BFPO 1234"))
        .isTrue();
  }

  @Test
  public void validateBFPOIsInvalidWithNoSpace() {
    assertThat(PostCodeValidator.validateInput("BFPO123"))
        .isTrue();
  }

  @Test
  public void validateTooLongBFPOIsInvalid() {
    assertThat(PostCodeValidator.validateInput("BFPO 12345"))
        .isFalse();
  }

  @Test
  public void validateTooShortBFPOIsInvalid() {
    assertThat(PostCodeValidator.validateInput("BFPO "))
        .isFalse();
  }

  @Test
  public void validate_isBFPO_Returns_True_Given_A_Valid_BFPOCode()
      throws InvalidPostcodeException {
    instance.setPostCode("BFPO 123");
    assertThat(instance.isBFPO())
        .isTrue();
  }

  @Test
  public void validate_ReturnOutwardCode_Returns_BFPO_Given_A_Valid_BFPOCode() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnOutwardCode())
        .isEqualTo("BFPO");
  }

  @Test
  public void validate_ReturnInwardCode_Returns_123_Given_BFPO_123() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnInwardCode())
        .isEqualTo("123");
  }

  @Test
  public void validate_returnArea_Returns_Null_Given__A_Valid_BFPOCode() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnArea())
        .isNull();
  }

  @Test
  public void validate_returnDistrict_Returns_Null_Given__A_Valid_BFPOCode() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnDistrict())
        .isNull();
  }

  @Test
  public void validate_returnSector_Returns_Null_Given__A_Valid_BFPOCode() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnSector())
        .isNull();
  }

  @Test
  public void validate_returnUnit_Returns_Null_Given__A_Valid_BFPOCode() {
    instance.setPostCode("BFPO 123");
    assertThat(instance.returnUnit())
        .isNull();
  }

  @Test
  public void validate_returnFullPostcode_Returns_BFPO_123_Given__BFPO123() {
    instance.setPostCode("BFPO123");
    assertThat(instance.returnFullPostcode())
        .isEqualTo("BFPO 123");
  }

  @Test
  public void validate_BFPOPostcode_Is_Not_NI_Postcode() throws InvalidPostcodeException {
    instance.setPostCode("BFPO123");
    assertThat(instance.isNorthernIreland())
        .withFailMessage("BFPO should not be seen as NI postcode as we don't know!")
        .isFalse();
  }
}