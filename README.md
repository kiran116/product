# Product API with loading and filtering data features 



## How to Run 

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean install```
* Once successfully built, you can run the service by using the command:
```
          mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout or productfilter.log file to make sure no exceptions are thrown

Once the application runs you should see something like this

```
  INFO 19387 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090 (http)
  INFO 19387 --- [           main] com.telenor.products.Application         : Started Application in 14.77 seconds (JVM running for 20.862)
```

```
*please note that for the various use cases created a upload file api
instead of hardcoding db on start up. so must use the upload rest api on start of the application
to load the data

        load data by calling POST the http://localhost:8090/upload   
        use request param name as file to upload the data.csv
        
        check swagger documentation for the details on REST
        localhost:8090/swagger-ui.html
        
        once the data load is success filter data using
        
        http://localhost:8090/product?type=phone&property:color=guld
    
     

```
## How to Build Image for Docker

Execute the command to create a image for testing this app.

```
Command:

        docker build -t product-filter:1.0 . 
```

See last step on Dockerfile:

```

        CMD ["java", "-jar", "-Dspring.profiles.active=test", "/opt/spring-boot-rest/target/telenor-product.jar"]

```
This image will run the projet with test profile. 

## How to run this image with Docker

Execute the command to create an image for testing this app.

```
Command:

        docker run -d -p 8090:8090 -p 8091:8091 product-filter:1.0
        
initialize:

        load data by calling POST the http://localhost:8090/upload   
        use request param name as file to upload the data.csv
        
        check swagger documentation for the details on REST
        localhost:8090/swagger-ui.html
             
```


## About the Service and Assumptions of data and format

The service is just a simple product  REST service. It uses an in-memory database (H2) to store the data.

Here is what this little application demonstrates: 

* Packaging as a single war with embedded container (tomcat 8): No need to install a container separately on the host just run using the ``java -jar`` command
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger2 using annotations


### To view Swagger 2 API docs

Run the server and browse to localhost:8090/swagger-ui.html


### To view your H2 in-memory datbase

The 'test' profile runs on H2 in-memory database. To view and query the database you can browse to http://localhost:8090/h2-console. Default username is 'sa' with a blank password. Make sure you disable this in your production profiles. For more, see https://goo.gl/U8m62X
