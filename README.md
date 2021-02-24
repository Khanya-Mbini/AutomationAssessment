# Test Automation Assessment

## Getting Started

### What you need

1. Download and install apache Maven from
https://maven.apache.org/install.html

2. Download and install Java 15 from
https://www.oracle.com/in/java/technologies/javase-downloads.html

Configure environment variables to include java and maven

### Running these tests

1. Clone this repo

2. From the terminal run: `mvn clean verify`

3. At the end of the test run, a report will be produced and found under `target/site/serenity/index.html`

#### Using Chrome

This test suite uses the `chromedriver` by default, if changed to something else you can set the `webdriver.driver` switch in `serenity.properties` to `chrome`.

To use chrome without changing the properties run: `mvn clean verify -Dwebdriver.driver=chrome`

#### Using Firefox

Set the `webdriver.driver` switch in `serenity.properties` to `firefox`.

To use firefox without changing the properties run: `mvn clean verify -Dwebdriver.driver=firefox`

#Tools used

[Maven](https://maven.apache.org/)

[Serenity BDD](https://serenity-bdd.github.io/theserenitybook/latest/index.html)

[Selenium](https://www.selenium.dev/about/)

[Rest Assured](https://rest-assured.io/) 