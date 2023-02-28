# DRMSimulator
An free software to simulate the methane dry reforming

## Description
The DRM Simulator is under development as a product of a PhD project. The main objective of the software is to provide the user with an intuitive tool that contains a scientific basis to carry out the dry methane reforming simulation.

Several equations are part of the processing part, and the user only needs to enter the conditions of the experiment he wants to simulate.

## User Instructions - Executable File

To use DRMSimulator you must have the Java Runtime Environment (JRE - v.8) installed on your computer.

On Windows:
Download the target file "DRMSimulator-v1.jar" available at:
https://github.com/evandronk/DRMSimulator/blob/main/target/DRMSimulator-v1.jar

On iOS:
Not avaliable yet.

## User Instructions - How to use

- First enter the values of the inlet volumetric flow rates for each gas (methane and carbon dioxide).

- Also provide the reactor pressure here.

![tab1T](https://user-images.githubusercontent.com/47874917/221714033-96ff68ea-bd63-47e2-b8ed-06ad447c224b.png)

- At second screen, provide the values referring to the reactor/catalytic bed, such as:
- lenght, diameter, porosity and catalyst density

<img width="438" alt="Captura de Tela 2023-02-27 às 20 46 17" src="https://user-images.githubusercontent.com/47874917/221715398-7b6a4966-7716-41da-9ad6-7801263612b8.png">

- On the third screen, provide the kinetic data of the process to be simulated.
- Here you must select one of mechanisms (Power Law / Modelo de Potências, Eley-Rideal or Langmuir-Hinshelwood).
- Check or uncheck the reversible option and choose the desired kinetic model.
- Provite the parameters needed

<img width="898" alt="Captura de Tela 2023-02-27 às 20 46 47" src="https://user-images.githubusercontent.com/47874917/221715412-cce7cb93-4841-4548-b38f-e2d69e951cbc.png">

- In the fourth screen, provide the data referring to the heat exchange of the process.
- The global heat transfer coefficient (Utc) must be provided in this software version (v1.0).

<img width="440" alt="Captura de Tela 2023-02-27 às 20 47 12" src="https://user-images.githubusercontent.com/47874917/221715431-9e527cb7-8503-4caf-84e8-4d3ef5c86116.png">

- Finally, on the last screen, indicate the final time value at which you want to simulate.
- Provide the minimum and maximum time step values. Also provide the number of axial partitions.
- If these simulation parameters are unfamiliar start with the image below values and and adjust these values to higher or lower.

<img width="555" alt="Captura de Tela 2023-02-27 às 20 47 54" src="https://user-images.githubusercontent.com/47874917/221715437-f59a7b7f-26da-415e-93f4-59675b545c31.png">

- We will make available two references that address the mathematical methods used to solve partial differential equations:
https://www.sciencedirect.com/science/article/pii/S1110016815002197
https://www.sciencedirect.com/science/article/pii/B9780122083501500121


## Known Issues
- Some texts need to be properly translated .
- The "Parameter Estimation Module" is functional, but errors may occour.



## Maven Dependencies
Here we would like to mention the dependencies used in the development of DRMSimulator:

    <dependency>
			<groupId>com.googlecode.efficient-java-matrix-library</groupId>
			<artifactId>ejml</artifactId>
			<version>0.25</version>
		</dependency>
    
		<dependency>
			<groupId>de.jensd</groupId>
			<artifactId>fontawesomefx</artifactId>
			<version>8.9</version>
		</dependency>
    
		<dependency>
			<groupId>org.jfree</groupId>
			<artifactId>org.jfree.fxgraphics2d</artifactId>
			<version>2.1.3</version>
		</dependency>

		<dependency>
			<groupId>org.scilab.forge</groupId>
			<artifactId>jlatexmath</artifactId>
			<version>1.0.7</version>
		</dependency>

		<dependency>
			<groupId>net.objecthunter</groupId>
			<artifactId>exp4j</artifactId>
			<version>0.4.8</version>
		</dependency>

		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi</artifactId>
			<version>3.7</version>
		</dependency>

		<dependency>
			<groupId>org.apache.poi</groupId>
			<artifactId>poi-ooxml</artifactId>
			<version>3.7</version>
		</dependency>

		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-controls</artifactId>
			<version>19</version>
		</dependency>

		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-fxml</artifactId>
			<version>19</version>
		</dependency>


		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx-graphics</artifactId>
			<version>19</version>
		</dependency>

		<dependency>
			<groupId>org.openjfx</groupId>
			<artifactId>javafx</artifactId>
			<version>19</version>
			<type>pom</type>
		</dependency>

