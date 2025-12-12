# Client-Server Architecture

A Java-based client-server application with MySQL database integration and user management functionality.

## Features

- **Multi-threaded Server**: Handles multiple client connections simultaneously using threaded architecture
- **Socket Communication**: TCP/IP socket-based communication between clients and server
- **Database Integration**: MySQL database for persistent user data storage
- **User Management**: Registration, login, authentication, account locking, and password recovery
- **GUI Interface**: Graphical user interfaces for both client and server applications
- **Email Support**: Email notification system (placeholder for future implementation)

## Technologies

- Java
- MySQL (JDBC)
- Socket Programming
- Swing (GUI)

## Project Structure

- `src/client/` - Client-side application and communication logic
- `src/server/` - Server-side application and connection handling
- `src/MySQL/` - Database connection and data access layer
- `src/email/` - Email functionality
- `userManager.java` - User management operations
- GUI components for client and server interfaces

## Setup

1. Configure MySQL database connection in `DataBaseConnection.java`
2. Create the required database schema and user table
3. Run the server application first
4. Launch client application(s) to connect

## Configuration

- Default Port: `8000`
- Default Host: `127.0.0.1` (localhost)
