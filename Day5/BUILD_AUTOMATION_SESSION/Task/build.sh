#!/bin/bash

echo "Cleaning project..."
mvn clean

echo "Installing dependencies and compiling..."
mvn install

echo "Running tests..."
mvn test
