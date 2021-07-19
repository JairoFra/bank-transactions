# Bank Transactions Code Challenge

## System requirements
- Java 16
- Maven 3.5+


## Run the application using embedded Tomcat
The application can be run in an embedded Tomcat servlet container. To do so generate a new JAR file with the command
```sh
mvn clean install
```
A new JAR file will be generated in _/target_.

Then run the application with the command
```sh
java -jar bank-transactions-0.0.1-SNAPSHOT.jar
```

## Deployment
The application can be packaged in both WAR and JAR files using Maven commands and then being deployed in a servlet container or a Docker container.

**Generate WAR**
```sh
mvn clean package -Pwar
```
**Generate JAR**
```sh
mvn clean package -Pjar
```


## Initial data
The application uses an in-memory H2 database and it has been populated with initial data to facilitate the testing of the application. 
The data can be inspected in the [BootstrapData class](./src/main/java/com/challenge/banktransactions/bootstrap/BootstrapData.java).

## H2 UI console
The H2 database user interface console can be accessed in **[BASE_URL]/h2-console/**
> http://localhost:8080/h2-console/

To access it, enter the following data in the login window: 
| Field| Value |
| ------ | ------ |
| Saved Settings| Generic H2 (embedded) |
| Setting Name| Generic H2 (embedded)  |
| Driver Class | org.h2.Driver |
| JDBC URL | Value generated in every deployment, that can be found in the console logs, like _H2 console available at '/h2-console'. Database available at XXXXXXXXX_ |
| User Name| sa |
| Password |  |




## API documentation
Can be found in **[BASE_URL]/swagger-ui/**

> http://localhost:8080/swagger-ui/

## Testing
#### Run unit tests
```sh
mvn test
```

#### Run acceptance tests
```sh
mvn test -Dtest=AcceptanceIT
```
A new report file will be generated in _/target/reports/acceptance-tests.html_.

#### Test with Postman
A Postman collection file with sample requests can be found [here](./docs/BankTransactions.postman_collection.json).
