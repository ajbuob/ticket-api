# ticket-api  


Maven/Spring Boot MVC REST service provider war which supports ticket sales and query operations backed by H2 in-memory database. 
The default "dev" profile retrieves data from an in-memory H2 database, but this can be generalized to any other persistence store(database,web service, etc)
by providing another implementation of the InventoryRepository and CustomerRepository interfaces.

This project can be run locally using the Maven command 'mvn spring-boot:run' or the standard 'mvn package' command can be
used to produce a WAR file that can be deployed in any compatible Java servlet container.

This project also contains a file at the root (TICKET_API.postman_collection.json) of the exported Postman project which can be used to client API testing (Spring default dev profile will test against in-memory H2 database)

## RUNNING LOCALLY NOTES: 
H2 database console can be accessed at http://localhost:8181/console

The value of csvFilePath in application-dev.yml needs to be changed so the path to the maven project exists on the developers machine.
