# Watchlist Application

This is my first Spring Boot project, created as part of the **"Create Web Applications Efficiently With the Spring Boot MVC Framework"** course on OpenClassrooms by Vahid Yaghini.

## Project Overview

The Watchlist Application is designed to manage a list of movies or shows that you want to watch. It leverages various features and best practices of Spring Boot and Spring MVC to provide a robust and maintainable application.

## Course Outline

### Part #1 - Set up a deployable Spring Boot project

- **Learnings:**
  - Benefits of Spring Boot over Spring
  - Creating and running a Spring Boot web project

### Part #2 - Create a working web application with Spring MVC

- **Learnings:**
  - Implementing Spring MVC architecture with Thymeleaf templates
  - Handling form submissions
  - Creating and validating form fields, including cross-field and cross-record validations
  - Configuring Spring MVC

### Part #3 - Refactor an application to ensure loosely coupled and testable backend services

- **Learnings:**
  - Organizing code in a three-tier architecture
  - Using dependency injection for unit testable services
  - Configuring Spring beans via XML and Java
  - Conditional bean configurations for multiple versions

### Part #4 - Manage a production environment with Spring Boot

- **Learnings:**
  - Setting up logging for application issue tracking
  - Getting started with Spring Boot Actuator
  - Enabling and creating custom Actuator endpoints

## How to Run

1. Clone the repository:

   ```bash
   git clone <repository-url>
   cd Watchlist
   ```

2. Set up the OMDB API key:

   ```bash
   export OMDB_API_KEY=your_api_key
   ```

3. Build and run the application:
   ```bash
   ./mvnw clean package
   ./mvnw spring-boot:run
   ```

## Project Features

- **Watchlist Management:** Add, update, and view watchlist items.
- **Form Validation:** Ensure valid input for watchlist items.
- **External API Integration:** Fetch movie ratings from the OMDB API.
- **Three-Tier Architecture:** Well-organized codebase for maintainability and testability.
- **Actuator Endpoints:** Monitor and manage the application in a production environment.

## Contributing

Contributions are welcome! Please create an issue or submit a pull request for any enhancements or bug fixes.
