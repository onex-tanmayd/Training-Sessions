# FileIO

A Java project for reading user data from a CSV file, inserting it into a PostgreSQL database, and exporting data from the database back to a CSV file.

## Features

- Read users from a CSV file and insert them into a PostgreSQL database.
- Export users from the database to a CSV file.

## Technologies Used

- Java 21
- Maven
- PostgreSQL
- JDBC

## Prerequisites

- Java 21+
- Maven
- PostgreSQL (running and accessible)

## Setup

1. Clone the repository:
   ```
   git clone git@github.com:onex-tanmayd/Training-Sessions.git
   ```
2. Navigate to the project directory:
   ```
   cd Training-Sessions/Day18/FileIO
   ```
3. Update the database credentials in `FileReaderUtil.java` if needed.
4. Ensure the `users` table exists in your PostgreSQL database:
   ```sql
   CREATE TABLE users (
     id INT PRIMARY KEY,
     name VARCHAR(100),
     email VARCHAR(100)
   );
   ```
5. Build the project:
   ```
   mvn clean install
   ```

## Usage

1. Place your input CSV file (e.g., `users.csv`) in the `src/main/resources` directory.
2. Run the application:
   ```
   mvn exec:java -Dexec.mainClass="com.onextel.Main"
   ```
3. The program will:
   - Read users from `users.csv` and insert them into the database.
   - Export users from the database to `users_export.csv`.

## File Structure

- `src/main/java/com/onextel/Main.java` - Entry point of the application.
- `src/main/java/file/FileReaderUtil.java` - File and database utility methods.
- `src/main/java/dto/User.java` - User data record.
- `src/main/resources/users.csv` - Sample input CSV.
- `src/main/resources/users_export.csv` - Output CSV after export.

## License

This project is for training and educational purposes.
