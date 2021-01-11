# Mr-checkoff
A web-based To-Do List application that allows anyone to enter, track, update, and complete any collection of tasks that they need to get done.

A to-do list consists of a description and tasks. A task is simply a string of text and a checkbox that represent its two states: completed and incompleted.
Unregistered users can create one list with up to 20 incomplete items. An unregistered user's list disappears when they leave the website for more than 30 minutes. 

Registered users can have multiple lists with no limit on the number of incomplete items. To-do lists of registered users are stored permanently in their accounts.
Registered users must log in to see their lists. No one else can see a registered user's lists.
In order to register, a user must provide a username and a valid email address.

## Model-View-Controller (MVC) Pattern

- Model: POJOs with DAOs talking to MySQL database via JDBC
- View: Static HTML files generated from Freemarker templates
- Controller: Java Servlet running on Apache Tomcat. Parses URLs and enforces functional permissions.

## Data Access Object (DAO) Pattern

Hides database implementation from Controller
