# 🏨 Hotel Management Project

## 🌟 Overview

This **Hotel Management Project** provides a comprehensive solution for managing hotel services and room bookings. The frontend is built with **React.js**, while the backend is powered by **Spring Boot**. The application is designed for both admin and user functionalities, ensuring a smooth and efficient experience.

Live Website: https://guptahotel.netlify.app/

## ⚙️ Features

### 🛠️ Admin Features

- **Room Management**: Admins can add, delete, and update room services.
- **Photo Management**: Upload and store hotel room photos on the local server for seamless access without external dependencies.

### 🧑‍🤝‍🧑 User Features

- **Online Booking**: Users can book rooms online and manage their bookings easily.
- **Booking Confirmation**: Upon booking, users receive a confirmation with all booking details, including a unique booking confirmation code for tracking.
- **Profile Management**: Users can update their personal information and details in their profiles.
- **Account Deletion**: Users have the option to delete their accounts if they choose not to use the services anymore.
- **Password Reset**: A "Forgot Password" feature allows users to reset their passwords through email OTP verification.

## 📦 Installation

### 🔧 Prerequisites

Ensure you have the following installed:
- Node.js
- npm
- Java (for Spring Boot)
- Spring Boot

### 🛠️ Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone [repository link]
   cd [project-directory]
   ```

2. **Frontend Setup**:
   ```bash
   cd frontend
   npm install
   npm start
   ```

3. **Backend Setup**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

### Files to configure
1. Backened/Spring Boot Application/src/main/resources/application.properties -> Configure database and SMTP server

## 🚀 Usage

- **Admin Dashboard**: Access the admin panel to manage room services and bookings.
- **User Dashboard**: Explore hotel services, book rooms, and manage bookings with ease.

## 🤝 Contributing

We welcome contributions! Please read the [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to get involved.
1. Fork the repository.
2. Make your changes.
3. Submit a pull request.

## 🙏 Acknowledgments

Special thanks to everyone who contributed to this project! Your support is greatly appreciated! 

## 🌱 Future Enhancements

- Integrate advanced booking features like group bookings and seasonal offers.
- Improve user interface with enhanced design elements for better user experience.
- Implement analytics for admin to track booking trends and user engagement.

Thank you for checking out the **Hotel Management Project**! We hope you enjoy the experience and find it useful for your hotel management needs. 🏨✨
