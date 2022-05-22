# BUCHL: Single-Page-Application and Multi-Page-Application
## What is BUCHL / this repository about?
This project was created as part of the bachelor thesis. 
There are two websites with identical content in the repository. 
One is an SPA with VueJS and the other is an MPA with Spring Boot and Thymeleaf. 
Accessibility tests for the websites were designed, which were carried out with visually impaired (blind) people. 
The aim was to investigate and document the differences in accessibility.

## Setup
To get the project up and running, follow these steps:
* Install MySQL (e.g. by installing xampp)
* Start the database and create a database scheme "buchl"
* Install nodeJS
* Install a Java IDE (such as IntelliJ or Eclipse)
* Start the Multi-Page-Application (under `server/src/main/java/at/technikumwien/buchl/MainApplication.java`)
* Change the url and port of `axios.defaults.baseURL` (under `spa/src/main.js`) to your MPA Server `localhost:8080`
* Start the Single-Page-Application by executing the command `npm run serve` in the spa directory

The MPA should use localhost:8080. 
The SPA should print the url under which the website can be accessed. 
The port of the SPA can be changed by altering the script `serve` in the `package.json` file.
