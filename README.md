# PostCodeValidator

This class performs validation of a postcode format. It uses regex to do this. There is no validation that the postcode itself is valid. There is no data lookup functionality.

The class includes the BFPO format and has a query for a NI postcode.

The class has a static method to validate user input, or it can be used dynamically to query the postcode itself.

For example: 
`PostCodeValidator::validateInput("AA9B 9DD");`
will return true as AA9B9DD is in a valid format.

`PostCodeValidator postcodeValidator = new PostCodeValidator("AA9B 9DD");`
Both validates the input and configures the variable. If the input is not in valid postcode format, an InvalidPostcodeException will be thrown.

#### Project inclusion

properties entry in pom

    <properties>
        <dwp.formatvalidation.postcode>x.x</dwp.formatvalidation.postcode>
    </properties>
    
internal Artifactory repository reference is required (plugin reference required for OWASP checks)

    <repositories>
        <repository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </repository>
    </repositories>
    <pluginRepositories>
        <pluginRepository>
            <id>dwp internal</id>
            <url>###REPOSITORY_URL###</url>
        </pluginRepository>
    </pluginRepositories>

dependency reference

    <dependency>
        <groupId>gov.dwp.software-engineering.formatvalidation</groupId>
        <artifactId>postcode</artifactId>
        <version>${dwp.formatvalidation.postcode}</version>
    </dependency>
    
#### Example of use

    import gov.dwp.utilities.formatvalidation.postcode;

_declaration_

`PostCodeValidator postcodeValidator = new PostCodeValidator("AA9B 9DD");`

_Use_

`postcodeValidator.returnArea()`

or as above:

`PostCodeValidator::validateInput("AA9B 9DD");`
