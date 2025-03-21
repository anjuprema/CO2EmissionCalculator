# CO₂ Emission Calculator

## Overview
The CO₂ Emission Calculator is a Java-based application that calculates the CO₂-equivalent emissions for traveling between two cities using a selected mode of transport. It leverages the OpenRouteService API to determine distances and estimates emissions based on transport type.

## Features
✔️ Fetches city coordinates using OpenRouteService.  
✔️ Calculates travel distances.  
✔️ Estimates CO₂ emissions based on transport choice.  

### Prerequisites
Ensure you have the following installed:  
Java 11 → All dependencies require Java 11. Verify installation using > `java -version`  
Maven 3 → Verify installation using > `mvn -v`  
OpenRouteService API Key → You can get one at [https://openrouteservice.org/sign-up/](https://openrouteservice.org/sign-up/)  

## Installation
# Setup
1. **Import the Project**  
	- Open your IDE (e.g., IntelliJ, Eclipse).  
	- Import the project as a Maven Project.  

2. **Set up your environment variable 'ORS_TOKEN' for OpenRouteService:**  
	# In Windows  
	&nbsp;&nbsp;&nbsp;&nbsp;- Set your api key as environment variable 			# To persist the value  
	&nbsp;&nbsp;&nbsp;&nbsp;- Use command >$env:ORS_TOKEN = "your-api-key-here"      				# For Command Prompt (temporary in session)  
	# In Linux/Mac  
	&nbsp;&nbsp;&nbsp;&nbsp;- Add ORS_TOKEN to .bashrc/.cshrc		 			# To persist the value  
	&nbsp;&nbsp;&nbsp;&nbsp;- export ORS_TOKEN=your-api-key-here   				# Execute in terminal  (temporary in session)

3. **Verify the project setup**  
	- Ensure Java version is 11. Use `java -version` command  
	- Ensure Maven3 is installed. Use `mvn -v` command  
	- Ensure ORS_TOKEN is set as environment variable. Use  
	&nbsp;&nbsp;&nbsp;&nbsp;> echo $env:ORS_TOKEN&nbsp;&nbsp;&nbsp;&nbsp;# Windows  
	&nbsp;&nbsp;&nbsp;&nbsp;> echo $ORS_TOKEN&nbsp;&nbsp;&nbsp;&nbsp;# Linux/Mac
  
4. **Build the project using Maven:**  
	- Right click the project and execute maven goal <i>"clean package"</i>&nbsp;&nbsp;&nbsp;&nbsp;# For IDE  
	- From the path where pom.xml execute <i>"mvn clean package"</i>&nbsp;&nbsp;&nbsp;&nbsp;# For Command Prompt  
  
5. **Expected build result**  
	- This will create 2 jar files at [PROJECT_DIRECTORY]/target/  
		(co2-calculator.jar & co2-calculator-jar-with-dependencies.jar)  
	
6. **Run the application:**  
	(Works on Windows PowerShell/Linux & Mac Bash)  
	> cd [PROJECT_DIRECTORY]/scripts  
	> ./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium    
&nbsp;&nbsp;&nbsp;&nbsp;'--start' & '--end' accepts city names. To know about possible transportation methods make use of './co2-calculator --help' 
7. **Expected Result**  
	> ./co2-calculator --start Hamburg --end Berlin --transportation-method diesel-car-medium  
&nbsp;&nbsp;&nbsp;&nbsp;=> Your trip caused 49.2 kg of CO2-equivalent  
    > ./co2-calculator --start "Los Angeles" --end "New York" --transportation-method=diesel-car-medium  
&nbsp;&nbsp;&nbsp;&nbsp;=> Your trip caused 770.4 kg of CO2-equivalent.  
	> ./co2-calculator --end "New York" --start "Los Angeles" --transportation-method=electric-car-large  
&nbsp;&nbsp;&nbsp;&nbsp;=> Your trip caused 328.9 kg of CO2-equivalent.  
	> ./co2-calculator --help  
&nbsp;&nbsp;&nbsp;&nbsp;=> Gives details on script usage

## Running Tests
To run the test suite:  
From the path of pom.xml file, execute the command > mvn test  
The project uses **JUnit 5 & Mockito** for testing.

## Project Structure
```
co2-calculator/
│── src/
│   ├── main/java/com/anju/co2calculator/  	# Main application code
│   │   ├── config/                      	# Configurations
│   │   ├── exception/                      	# Custom exceptions
│   │   ├── model/                      	# Model Classes
│   │   ├── service/                       	# Business logic
│   ├── test/java/com/anju/co2calculator/   	# Test cases
│── scripts/                                 
│   ├── co2-calculator                      	# Script to run application in Linux/Mac
│   ├── co2-calculator.bat                   	# Script to run application in Windows
│── pom.xml                                 	# Maven dependencies
│── README.md                               	# Documentation
```

## Technologies Used
- **Java 11**
- **JUnit 5 & Mockito**
- **OpenRouteService API**
- **Maven3**


## Troubleshooting
### `Error: Failed to get coordinates: HTTP error code 403` while run
- Ensure your **ORS_TOKEN** is set correctly.

### `MockitoException: Could not modify all classes` in tests
- Make sure Java version is 11

## Enhancements
- Support for Additional Transportation Methods
- Localization & Multi-language Support
- Support for multi city routes
  
  
  
  
  
  
  


