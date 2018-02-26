# PostCodeValidator

This class performs validation of a postcode format. It uses regex to do this. There is no validation that the postcode itself is valid. There is no data lookup functionality.

The class includes the BFPO format and has a query for a NI postcode.

The class has a static method to validate user input, or it can be used dynamically to query the postcode itself.

For example: 
`PostCodeValidator.validateInput("AA9B 9DD");`
will return true as AA9B9DD is in a valid format.

`PostCodeValidator postcodeValidator = new PostCodeValidator("AA9B 9DD");`
Both validates the input and configures the variable. If the input is not in valid postcode format, an InvalidPostcodeException will be thrown.

#### Project inclusion

properties entry in pom

    <properties>
        <dwp.formatvalidation.postcode>x.x</dwp.formatvalidation.postcode>
    </properties>

dependency reference

    <dependency>
        <groupId>uk.gov.dwp.regex</groupId>
        <artifactId>postcode-validation</artifactId>
        <version>${dwp.formatvalidation.postcode}</version>
    </dependency>
    
#### Example of use

    import uk.gov.dwp.regex.PostCodeValidator;

_declaration_

`PostCodeValidator postcodeValidator = new PostCodeValidator("AA9B 9DD");`

_Use example_

`postcodeValidator.returnArea()`

or statically as:

`PostCodeValidator.validateInput("AA9B 9DD");`
