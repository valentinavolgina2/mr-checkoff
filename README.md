# Mr-Checkoff

**Mr-Checkoff** is a web-based To-Do List application that allows users to create, track, update, and complete their tasks efficiently. The app supports both guest and registered users, providing different levels of functionality and data persistence.

## Features

### General Features:
- Create and manage to-do lists with tasks.
- Tasks can be marked as completed or incomplete.

### Unregistered Users:
- Can create a single to-do list with up to 20 incomplete tasks.
- Lists are temporary and disappear after 30 minutes of inactivity.

### Registered Users:
- Can create multiple to-do lists with unlimited tasks.
- Lists are stored permanently and only accessible to the user.
- Secure login system with username and valid email required for registration.

### Architecture:
- **Model:** POJOs with DAO (Data Access Object) interacting with a MySQL database via JDBC.
- **View:** Static HTML files generated using Freemarker templates.
- **Controller:** Java Servlet running on Apache Tomcat, handling URL parsing and permission enforcement.
- **Data Storage Options:** Memory-based or MySQL database (switchable in `ToDoListServlet.java`).

## Installation

### Prerequisites
- Java 8 or later
- Apache Tomcat 9 or later
- MySQL (for persistent storage option)
- Maven for dependency management

### Setup Instructions
1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-username/mr-checkoff.git
   cd mr-checkoff
   ```
2. **Configure the database (if using MySQL):**
   - Create a new MySQL database and update connection settings in `ToDoListServlet.java`.
3. **Build the project using Maven:**
   ```sh
   mvn clean package
   ```
4. **Deploy to Tomcat:**
   - Copy the generated WAR file from `target/mr-checkoff.war` to the `webapps` directory of your Tomcat server.
5. **Start Tomcat and access the app:**
   ```sh
   http://localhost:8080/mr-checkoff/
   ```

## Dependencies
The project dependencies are managed via Maven and listed in `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>javax</groupId>
        <artifactId>javaee-web-api</artifactId>
        <version>7.0</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.13</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.freemarker</groupId>
        <artifactId>freemarker</artifactId>
        <version>2.3.28</version>
    </dependency>
    <dependency>
        <groupId>org.mariadb.jdbc</groupId>
        <artifactId>mariadb-java-client</artifactId>
        <version>2.6.0</version>
    </dependency>
</dependencies>
```

