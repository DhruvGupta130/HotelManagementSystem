# 🏨 Hotel Management Project

## 🌟 Overview

This **Hotel Management Project** provides a comprehensive solution for managing hotel services and room bookings. The frontend is built with **React.js**, while the backend is powered by **Spring Boot** and **MySQL**. The application supports both **admin and user functionalities**, ensuring a seamless experience.

🔗 **Live Website**: https://guptahotel.netlify.app/

## ⚙️ Features

### 🛠️ Admin Features

- **Dashboard**: Manage rooms, bookings, and users in an interactive admin panel.
- **Room Management**: Add, update, and delete hotel room details.
- **Booking Management**: View, approve, and cancel bookings as needed.
- **Photo Management**: Upload and store hotel room images securely.
- **User Management**: View user details and manage accounts efficiently.

### 🧑‍🤝‍🧑 User Features

- **Online Room Booking**: Easily book available rooms based on preferences.
- **Booking Confirmation**: Receive a confirmation email with a unique booking ID.
- **Profile Management**: Users can update their details and manage bookings.
- **Password Reset**: Secure email OTP verification for forgotten passwords.
- **Account Deletion**: Users can delete their accounts if they no longer require the service.

## 📦 Installation Guide

### 🔧 Prerequisites

Ensure you have the following installed:

- **Node.js** (Frontend)
- **npm** (Package Manager)
- **Java JDK 17+** (Spring Boot Backend)
- **Spring Boot**
- **MySQL** (Database)
- **Maven** (Build Tool)

### 🛠️ Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone [repository link]
   cd [project-directory]
   ```

2. **Database Setup (MySQL)**
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

3. **Frontend Setup**
   ```bash
   cd frontend
   npm install
   npm start
   ```

4. **Backend Setup**
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```

## 🚀 Usage

- **Admin Panel**: Manage rooms, users, and bookings.
- **User Dashboard**: Explore rooms, book stays, and manage reservations.

## 🔍 Technologies Used

### 🌐 Frontend
- **React.js** – Interactive UI components
- **Axios** – Api calling

### 🖥️ Backend
- **Spring Boot** – REST API Development
- **Spring Security** – Authentication & Authorization
- **MySQL** – Database Management

### 🛠️ Additional Services
- **Local Storage Server** – Image storage and management
- **Email Service** – SMTP-based email notifications

## 🤝 Contributing

We welcome contributions! Follow these steps:

1. **Fork the repository**.
2. **Create a feature branch** (`git checkout -b feature-name`).
3. **Make changes and commit** (`git commit -m "Added feature"`).
4. **Push to your branch** (`git push origin feature-name`).
5. **Submit a pull request**.

## 🌱 Future Enhancements

- **AI-Based Booking Suggestions**: Recommend rooms based on user preferences.
- **Chat Support**: Implement live chat for customer service.
- **Payment Gateway Integration**: Enable online payment options.

## 🙏 Acknowledgment

This project was inspired by **Phegon Dev** tutorials and extended with additional functionalities and optimizations.

## 📄 License

This project is licensed under the **MIT License**.

---

🚀 **Thank you for checking out the project! Feel free to contribute and enhance the system.** 🏨✨
