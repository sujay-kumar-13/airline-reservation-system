# Airline Reservation Management System

## Overview
The **Airline Reservation Management System** is a web-based application designed to manage flight bookings, cancellations, passenger details, and schedules efficiently. The system provides an intuitive interface for users to book flights and an administrative panel for airlines to manage reservations, flight details, and customer data.

## Features
- **User Authentication:** Secure login and registration for customers and administrators.
- **Flight Booking:** Users can search, book, and manage their flight tickets.
- **Flight Management:** Admins can add, update, and delete flights.
- **Payment Integration:** Secure online payment processing.
- **Booking Cancellation:** Users can cancel reservations and receive applicable refunds.
- **Passenger Information Management:** Stores user and flight details in MongoDB.
- **Responsive UI:** Designed with HTML and CSS for an engaging user experience.

## Technologies Used
### Backend
- **Java** (Core business logic)
- **Spring Boot** (REST API development)
- **Spring Security** (Authentication and Authorization)
- **MongoDB** (NoSQL Database for storing flight and user details)

### Frontend
- **HTML, CSS** (User Interface Design)
- **JavaScript** (Client-side interactivity)

## Installation & Setup
### Prerequisites
Ensure you have the following installed:
- **Java JDK 11+**
- **Maven**
- **MongoDB** (Running locally or cloud instance)
- **Spring Boot**

### Backend Setup
1. Clone the repository:
   ```sh
   git clone https://github.com/sujay-kumar-13/airline-reservation-system.git
   cd airline-reservation
   ```
2. Configure MongoDB in `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/airline_db
   ```
3. Build and run the application:
   ```sh
   mvn spring-boot:run
   ```

### Frontend Setup
1. Navigate to the frontend directory:
   ```sh
   cd frontend
   ```
2. Open `index.html` in a browser or start a local server.

## API Endpoints
### User Authentication
- **POST** `/api/auth/register` – Register a new user.
- **POST** `/api/auth/login` – Authenticate and get JWT token.

### Flight Management
- **GET** `/api/flights` – Get all available flights.
- **POST** `/api/flights` – Add a new flight (Admin only).
- **PUT** `/api/flights/{id}` – Update flight details (Admin only).
- **DELETE** `/api/flights/{id}` – Remove a flight (Admin only).

### Booking
- **POST** `/api/bookings` – Book a flight.
- **GET** `/api/bookings/user/{userId}` – Get user's bookings.
- **DELETE** `/api/bookings/{bookingId}` – Cancel a booking.

## Usage
- Open the frontend in a browser.
- Register/login to access the booking system.
- Search for available flights and book a ticket.
- Admin users can manage flights and reservations.

## Future Enhancements
- Implement real-time seat availability.
- Add email notifications for bookings.
- Enable multi-city flight bookings.

## License
This project is licensed under the MIT License.

## Author
Developed by [Sujay Kumar](https://github.com/sujay-kumar-13).
