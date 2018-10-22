# Sales message processing
This is a Java console application that receives and processes sales notification messages from console. 

## Requirements
Java 1.8 needs to be installed. Add JAVA_HOME environment variable or set java executable on the PATH environment variable.

## Build
Use Gradle build tool to make the artifact. The generated artifact includes all the required dependencies.
```
gradlew clean build
```
Run only tests
```
gradlew clean test
```

## Command line interface usage
Console application start: ```java -jar <artifact-path>```
```
java -jar build/libs/sales-messages-all-1.0.jar
```

Demo
```
java -jar build/libs/sales-messages-all-1.0.jar < resources/demo-input.txt
```