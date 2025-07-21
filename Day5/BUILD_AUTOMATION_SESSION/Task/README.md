# Java Calculator Project

## Overview

This project is a simple Java application built with Maven. It includes a basic calculator with an addition function and a greeting method.

## Build and Test

To build and test the project, use the provided `build.sh` script:

```sh
./build.sh
```

## build.sh content

To build and test the project, use the provided `build.sh` script:

```sh
#!/bin/bash

echo "Cleaning project..."
mvn clean

echo "Installing dependencies and compiling..."
mvn install

echo "Running tests..."
mvn test
```
## Terminal output

```sh

:~/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task$ /bin/bash /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/build.sh

Cleaning project...
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.onextel:Task >--------------------------
[INFO] Building Task 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ Task ---
[INFO] Deleting /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  0.216 s
[INFO] Finished at: 2025-07-21T10:31:53+05:30
[INFO] ------------------------------------------------------------------------
Installing dependencies and compiling...
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.onextel:Task >--------------------------
[INFO] Building Task 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Task ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ Task ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/classes
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ Task ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ Task ---
[INFO] Changes detected - recompiling the module!
[INFO] Compiling 1 source file to /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/test-classes
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ Task ---
[INFO] Surefire report directory: /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.onextel.MainTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.041 sec

Results :

Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

[INFO] 
[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ Task ---
[INFO] Building jar: /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/Task-1.0-SNAPSHOT.jar
[INFO] 
[INFO] --- maven-install-plugin:2.4:install (default-install) @ Task ---
[INFO] Installing /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/Task-1.0-SNAPSHOT.jar to /home/tanmay.dalavi@onextel.com/.m2/repository/com/onextel/Task/1.0-SNAPSHOT/Task-1.0-SNAPSHOT.jar
[INFO] Installing /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/pom.xml to /home/tanmay.dalavi@onextel.com/.m2/repository/com/onextel/Task/1.0-SNAPSHOT/Task-1.0-SNAPSHOT.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.817 s
[INFO] Finished at: 2025-07-21T10:31:56+05:30
[INFO] ------------------------------------------------------------------------
Running tests...
[INFO] Scanning for projects...
[INFO] 
[INFO] --------------------------< com.onextel:Task >--------------------------
[INFO] Building Task 1.0-SNAPSHOT
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ Task ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] Copying 0 resource
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ Task ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-resources-plugin:2.6:testResources (default-testResources) @ Task ---
[INFO] Using 'UTF-8' encoding to copy filtered resources.
[INFO] skip non existing resourceDirectory /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/src/test/resources
[INFO] 
[INFO] --- maven-compiler-plugin:3.1:testCompile (default-testCompile) @ Task ---
[INFO] Nothing to compile - all classes are up to date
[INFO] 
[INFO] --- maven-surefire-plugin:2.12.4:test (default-test) @ Task ---
[INFO] Surefire report directory: /home/tanmay.dalavi@onextel.com/Desktop/Training-Sessions/Day5/BUILD_AUTOMATION_SESSION/Task/target/surefire-reports

-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.onextel.MainTest
Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.052 sec

Results :

Tests run: 2, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.041 s
[INFO] Finished at: 2025-07-21T10:31:58+05:30
[INFO] ------------------------------------------------------------------------
```
---