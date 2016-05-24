# Filmr: Cinema Management System

##Introduction
Filmr is the result of a six week long course at Teknikhögskolan, Gothenburg. The product consists of two main parts: A backend, written in Java, serving JSON-formatted data and acting as an API for a frontend. Our provided frontend, in turn, is meant to serve as an example of how one can use the API to manage a cinema. The frontend is written mainly in JavaScript, using Angular 1.x.

##Installation
This project is based on [Spring Boot](http://projects.spring.io/spring-boot/), and uses [Maven](https://maven.apache.org) for dependencies and deployment. It also uses a [MySQL](http://www.mysql.com) database for persistance.

* Download the repository and open the folder in your favourite IDE (we use [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](http://www.eclipse.org)).
    
* In the resource folder (src/main/resources) create the file application.properties and define the following properties for your own MySQL database:
    * `spring.datasource.url=`MY_DATABASE_URL
    * `spring.datasource.username=`MY_DATABASE_USERNAME
    * `spring.datasource.password=`MY_DATABASE_PASSWORD
* Run Application.java, located in src/main/java/filmr.

* Access the website at [http://localhost:8080/filmr](http://localhost:8080/filmr).

*This is not the most elegant solution, we hope to be able to bring you a proper installer in the future.*


##Deployment (.war)
If you have a server with tomcat (tomcat manager) and know how to deploy a .war file, you can follow these steps: (NOTE: our project uses Java 8 features so server will have to have a compatible Java version)

* Clone the repository, eg. 
	`clone https://gitlab.com/vintr/filmr.git`
* Change directory to project root 
	`cd filmr`
* Change to branch easier_deployment
	`git checkout easier_deployment`
* Make sure branch easier_deployment is up to date `git merge origin/dev` 
* In the resource folder (src/main/resources) create the file application.properties and define the following properties for your own MySQL database:
    * `spring.datasource.url=`MY_DATABASE_URL
    * `spring.datasource.username=`MY_DATABASE_USERNAME
    * `spring.datasource.password=`MY_DATABASE_PASSWORD
* Build the .war file 
	`mvn clean package -DskipTests`
* Locate the .war file in folder /target and rename it to filmr (needs to correspond to the value of 'server-context' in src/main/resources/application.yml)
* Deploy the .war file to Tomcat server or similar, eg. through Tomcat Manager App.


## Functionality (as of version 0.3)
### The admin interface
This is the part of the system intended for use by a cinema administrator - such as the owner, or the scheduler.
##### The main page
This is the starting point for the management of cinemas and their attributes. If the user of the system owns several cinemas that are all part of the same chain, they will be displayed here, along with the capability to create new cinemas.
##### The cinema page
This is a detailed view of a single cinema, and it consists of two main parts: the repertoire and the theaters.

The repertoire allows a cinema to be associated with any number of movies. These movies are fetched from an external source or central movie library. One associated, a movie can be used to schedule showings in theaters owned by the same cinema. The theaters are representations of the physical auditoriums where the movies will be shown. New theaters can be created from this page, and an initial value must be supplied for the number of rows and the number of seats per row that the theater will contain. These numbers can be changed later.
##### The theater page
This page displays information about a specific theater. Controls allow for the theaters name to be changed, as well as the number of rows and seats.

A visual representation of the theaters layout allows the user of the system to customize the number and size of rows. Clicking each individual seat also cycles the seats' status between "enabled" (seat can be used as normal), "disabled" (seat is visible but not bookable), and "not a seat" (seat is disabled and will not show up at all for bookings). The last state can be used to shape the theater - to give rows different width or to create lanes and spacing.
### The customer interface
This part of the system is meant to represent how a customer of the cinema can use the system to book tickets. There is currently no functionality for paying immediately on the site.
##### The booking pages
The booking pages consists of three views: The selection of a showing, the selection of seats, and the confirmation.

Selecting a showing is as easy as clicking its button. Finding the right showing however is done by a couple of filters. A user can filter the results both by movie and by date.

Once a showing has been selected it is time to select the seats the user wishes to book. Clicking any number of seats will select them, and the user can then enter a reference number (suggested as their phone number, for convenience) and submit their booking request.

If the request is successful the user will be taken to a confirmation page which displays the details of the booking.

## Version History
### 0.1 Demo
A simple demo version intended to display the most basic functionality of a finished system.
### 0.2 Admin
A more fleshed out version of the admin tools. Among other things, functionalty for adding and removing cinemas, theaters and showings were added in this version.
### 0.3 Project end
This release marks the end of the project, during which this product was created. This version adds capability for a "customer" to book tickets and experience all the changes and configurations made in the admin interface. Also a major facelift and improvement to the user experience.

*This is the current state of the software, but with the right incentive we might develop it further. :)*

## Project Contributors
- [Marco Fält](https://gitlab.com/u/marco.falt)
- [Adrian Kinberger](https://gitlab.com/u/luffarvante)
- [Tobias Ljungström](https://gitlab.com/u/vintr)
- [Emil Rånge](https://gitlab.com/u/rangeemil)
- [Erik Wiberg](https://gitlab.com/u/erik-wiberg-87)

## License
Filmr is licensed under a [Creative Commons Attribution-NonCommercial 4.0 International License](http://](http://creativecommons.org/licenses/by-nc/4.0/).


