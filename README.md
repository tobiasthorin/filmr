# Filmr: Cinema Management System

##Introduction
Filmr is the result of a five week long 

##Installation
This project is based on [Spring Boot](http://projects.spring.io/spring-boot/), and uses [Maven](https://maven.apache.org) for dependencies and deployment. It also uses a [MySQL](http://www.mysql.com) database for persistance.

* Download the repository and open the folder in your favourite IDE (we use [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](http://www.eclipse.org)).
    
* In the resource folder (src/main/resources) create the file application.properties and define the following properties for your own MySQL database:
    * `spring.datasource.url=`MY_DATABASE_URL
    * `spring.datasource.username=`MY_DATABASE_USERNAME
    * `spring.datasource.password=`MY_DATABASE_PASSWORD
* Run Application.java, located in src/main/java/filmr.

* Access the website at [http://localhost:8080/filmr](http://localhost:8080/filmr).

## Functionality (as of v0.3)
### The admin interface
##### The main page
##### The cinema page
##### The theater page
### The customer interface
##### The booking pages


## Version History
### 0.1 Demo
A simple demo version intended to display the most basic functionality of a finished system.
### 0.2 Admin
A more fleshed out version of the admin tools. Among other things, functionalty for adding and removing cinemas, theaters and showings were added in this version.
### 0.3 Project end
This release marks the end of the limited project, during which this product was created. This version adds capability for a "customer" to book tickets and experience all the changes and configurations made in the admin interface. Also a major facelift and improvement to the user experience.

*This is the current state of the software, but with the right incentive we might develop it further. :)*

## Project Contributors
- [Marco Fält](https://gitlab.com/u/marco.falt)
- [Adrian Kinberger](https://gitlab.com/u/luffarvante)
- [Tobias Ljungström](https://gitlab.com/u/vintr)
- [Emil Rånge](https://gitlab.com/u/rangeemil)
- [Erik Wiberg](https://gitlab.com/u/erik-wiberg-87)

