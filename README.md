# mr-checkoff
A web-based To-Do List application

# Model-View-Controller (MVC) Pattern

Model: POJOs with DAOs talking to MySQL database via JDBC
View: Static HTML files generated from Freemarker templates
Controller: Java Servlet running on Apache Tomcat. Parses URLs and enforces functional permissions.

# Data Access Object (DAO) Pattern

Hides database implementation from Controller
