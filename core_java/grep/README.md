# Introduction
<p align="justify"> The Java Grep is an application that performs a recursive search for a specified text pattern within
a directory and saves matched lines to a file. The implementation employed various technologies such
as Intellij IDEA, Core Java, Maven, Regex, Lambda & Stream APIs. Additionally, the project was 
containerized using docker for efficient deployment and management. </p>

# Quick Start

To use the app follow this format:
</br></br> **Usage: regex rootPath outFile**
- regex: A specific text string that describes the pattern to search for.
- rootPath: The path to the root directory to begin the search from.
- outFile: The name of the output file to which matched lines will be saved.

**To run the application, follow these steps:**
#### Approach 1: Downloading The App and run the JAR file
1. Install Java and Maven on your computer
2. Clone the GitHub repository containing the source code for the application
3. Navigate to the **grep** directory within the repository.
4. Build the application by running the following command: </br>
   ```mvn clean package```
   </br> This command will create a JAE file named "grep-1.0-SNAPSHOT.jar" in the "target" directory.
5. To search for a pattern, run the following command: </br>
   ``` java -jar target/grep-1.0-SNAPSHOT.jar <regex> <rootPath> <outFile>```
   </br> **_Replace regex, rootPath, and outFile with the appropriate values for your search._**
6. Verify the results by running: </br>
   ```cat out/outFile```
#### Approach 2: Using Docker Image
1. Run the grep app using the Docker image </br>
```
docker run --rm \ 
-v $PWD/data:/data -v $PWD/out:/out jrvs/grep \
<regex> <rootPath> <outFile> 
```
2. Verify the output: </br>
```
cat out/outFile
```
# Implementation
```
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```
# Performance issue
<p align="justify"> The current implementation of the grep app loads all data from the root directory recursively in
memory, which can cause the application to fail with an "OutOfMemoryError" if it runs out of heap 
space. To prevent this issue, we have two options: </p>

1. Increase the heap memory size allocated to the application to handle larger data sets.
2. The `readLines` method in this class use `BufferedReader` to read the file in smaller chunks and
   return a stream of lines, but it is still recommended to increase the memory to avoid running out of
   memory.
# Test
<p align="justify">The application has been thoroughly tested using a combination of manual test cases and automated testing with JUnit. Sample data was prepared to ensure the application is able to handle various inputs and edge cases. The results were then compared to expected outcomes to ensure accuracy. 
All methods were tested and passed successfully.</p>

# Deployment
The deployment involved the following steps:
1. Create a Dockerfile in the application directory with commands to build the image.
2. Package the application using Maven.
3. Build a new Docker image locally using the Docker build command and tag it with Docker Hub
   username and the application name: `docker build` and `-t` option.
4. Verify the image using `docker image ls` command.
5. Push the docker image to Docker Hub using `docker push`.

# Improvements
- Adding command-line options to enable case-insensitive search and regex search would provide users
  with more flexibility and functionality.
- Improve performance on large datasets by implementing the use of multiple threads.