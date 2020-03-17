# Serverside test #

Serverside test is a simple application which scrapes a portion of the website with provided address. 
There are two implementations of providing website address. 

Application is licensed by [MIT](https://opensource.org/licenses/mit-license.php)

## Tech/frameworks used ##

<img src="https://whirly.pl/wp-content/uploads/2017/05/spring.png" width="200"><img src="http://yaqzi.pl/wp-content/uploads/2016/12/apache_maven.png" width="200"><img src="https://upload.wikimedia.org/wikipedia/commons/2/2c/Mockito_Logo.png" width="200"><img src="https://shiftkeylabs.ca/wp-content/uploads/2017/02/JUnit_logo.png" width="200"><img src="https://jules-grospeiller.fr/media/logo_competences/lang/json.png" width="200">

## Instalation ##

* JDK 11
* Apache Maven 3.x

## Build and Run ##
```
mvn clean verify
java -jar target/serverside-test-0.0.1-SNAPSHOT.jar
```

## Dependencies ##
All dependencies are included in `pom.xml` file.

## API ##

Application is available on localhost:8080. Use `http://localhost:8080/?address=`
and paste an address of the webside, which data should be read. Alternatively use `http://localhost:8080/console` to provide url, which you want to check.

Application works correctly only with address given in task requirements, other web addresses generate errors that are (should be) properly handled.

## TO DO ##

To do this task quite completely, tests for the ApplicationService class should be added.
