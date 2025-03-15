# 🏨 Hotel Management Project

## 🌟 Overview

This **Hotel Management Project** provides a comprehensive solution for managing hotel services and room bookings. The frontend is built with **React.js**, while the backend is powered by **Spring Boot** and **MySQL**. The application is designed for both admin and user functionalities, ensuring a smooth and efficient experience.

🔗 **Live Website**: https://guptahotel.netlify.app/

## ⚙️ Features

### 🛠️ Admin Features

- **Room Management**: Admins can add, delete, and update room services.
- **Photo Management**: Upload and store hotel room photos on the local server for seamless access without external dependencies.

### 🧑‍🤝‍🧑 User Features

- **Online Booking**: Users can book rooms online and manage their bookings easily.
- **Booking Confirmation**: Users receive a confirmation with all booking details, including a unique booking confirmation code for tracking.
- **Profile Management**: Users can update their personal information and details in their profiles.
- **Account Deletion**: Users have the option to delete their accounts if they choose not to use the services anymore.
- **Password Reset**: A "Forgot Password" feature allows users to reset their passwords through email OTP verification.

## 📦 Installation

### 🔧 Prerequisites

Ensure you have the following installed:
- Node.js & npm
- Java (JDK 17+ for Spring Boot)
- Spring Boot
- MySQL Database
- Maven

### 🛠️ Setup Instructions

1. **Clone the Repository**:
   ```bash
   git clone [repository link]
   cd [project-directory]
   ```

2. **Database Setup (MySQL)**:
   - Install MySQL and create a database:
     ```sql
     CREATE DATABASE hotel_management;
     ```
   - Update **backend/src/main/resources/application.properties** with:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/hotel_management
     spring.datasource.username=root
     spring.datasource.password=yourpassword
     spring.jpa.hibernate.ddl-auto=update
     ```

3. **Frontend Setup**:
   ```bash
   cd frontend
   npm install
   npm start
   ```

4. **Backend Setup**:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

## 🚀 Usage

- **Admin Dashboard**: Access the admin panel to manage room services and bookings.
- **User Dashboard**: Explore hotel services, book rooms, and manage bookings with ease.

## 🤝 Contributing

We welcome contributions! Please follow these steps:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-name`).
3. Make your changes and commit (`git commit -m "Added feature"`).
4. Push to the branch (`git push origin feature-name`).
5. Submit a pull request.

## 📄 License
This project is licensed under the [MIT License](LICENSE.txt).

## 🌱 Future Enhancements

- **Advanced Booking Features**: Support for group bookings and seasonal offers.
- **Improved UI**: Enhanced user experience with a modern design.
- **Analytics Dashboard**: Admins can track booking trends and user engagement.

🙏 **Thank You for Checking Out This Project!** 🚀  
Feel free to contribute and improve the system! 🏨✨
