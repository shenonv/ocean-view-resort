<div align="center">

# 🌊 OceanView Resort
### Hotel Reservation Management System

[![Java](https://img.shields.io/badge/Java-11-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Maven-3.x-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-Servlet%204.0-FF6C37?style=for-the-badge)](https://jakarta.ee/)
[![License](https://img.shields.io/badge/License-MIT-green?style=for-the-badge)](LICENSE)

*A full-featured hotel reservation web application built with Java Servlets, demonstrating real-world application of classic Gang of Four (GoF) Design Patterns.*

</div>

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Design Patterns](#-design-patterns)
- [Project Structure](#-project-structure)
- [Architecture](#-architecture)
- [Setup & Installation](#-setup--installation)
- [Email Configuration](#-email-configuration)
- [Pages & Navigation](#-pages--navigation)

---

## 🏨 Overview

**OceanView Resort** is a hotel reservation management system that allows staff to manage guest bookings end-to-end — from login and room selection to billing, email confirmation, and viewing existing reservations. The project is built with a clean layered architecture using **Java Servlets (MVC)** and a **MySQL** database backend.

The primary educational goal of this project is to demonstrate **four Gang of Four (GoF) design patterns** applied to a real-world domain.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 **RBAC Security** | Role-based access control (ADMIN/STAFF) with servlet filter protection |
| 🛏️ **Room Booking** | Book Standard, Deluxe, or Suite rooms with date validation |
| 💰 **Dynamic Billing** | Per-room-type pricing with automatic strategy-based surcharges |
| 📧 **Email Confirmation** | Automated HTML email sent via Observer pattern (Jakarta Mail) |
| 📄 **Bill Printing** | Printable Invoice generation for confirmed reservations |
| 🔍 **View Reservations** | Global lookup of booking details by Reservation ID |
| 🧪 **JUnit Testing** | Automated test suite for billing strategies and reservation logic |

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| **Language** | Java 11 |
| **Web Framework** | Jakarta EE — Servlets & JSP (Servlet API 4.0) |
| **Build Tool** | Apache Maven 3.x |
| **Database** | MySQL 8.0 |
| **DB Driver** | MySQL Connector/J 8.0.33 |
| **Email** | Jakarta Mail (com.sun.mail 1.6.7) |
| **Testing** | JUnit 3.8.1 |
| **Server** | Apache Tomcat (any compatible version) |

---

## 🎨 Design Patterns

This project implements **four classic GoF design patterns** to ensure a high-quality, decoupled, and maintainable codebase.

### 1. 🏭 Factory Method Pattern
> **Package:** `com.oceanview.factory`  
> **Purpose:** Decouples the creation of room objects (Standard, Deluxe, Suite) from the business logic.

### 2. 💡 Strategy Pattern
> **Package:** `com.oceanview.strategy`  
> **Purpose:** Allows interchangeable billing rules for different room types (Surcharges: Deluxe 10%, Suite 15%).

### 3. 👁️ Observer Pattern
> **Package:** `com.oceanview.observer`  
> **Purpose:** Decouples post-reservation side-effects (like sending emails) from the core reservation service.

### 4. 🔒 Filter Pattern (Chain of Responsibility)
> **Package:** `com.oceanview.filter`  
> **Purpose:** Centralised security layer ensuring only authenticated users can access protected pages.

---

## 🏗️ Version Control & Git Workflow

This project utilizes Git for version control, strictly adhering to a **feature-branch workflow** to maintain code stability and ease of integration. The repository currently consists of **14 distinct branches** and **34 commits** mapping out the full development lifecycle.

### Branching Strategy

| Branch | Purpose |
|---|---|
| `master` | Stable, production-ready release branch (`v1`, `v2`, etc.). |
| `development` | Main integration branch for combining and testing features. |
| `JUnit-testing` | Implementation of automated test suites (TDD). |
| `feature/*` | Isolated development for specific modules (Login, Billing, etc). |
| `bugfix/*` | Dedicated branches for critical logic and security fixes. |

### Version History

The application iterations are tracked via major version releases on the `master` branch:

- **v1 (Version 1.0):** Initial release. Includes core functionality: RBAC Security, standard Room Booking, and foundational database schema.
- **v2 (Version 2.0):** Enhanced release. Introduces GoF design patterns like Dynamic Billing strategies, Email Confirmations (Observer pattern), Bill Printing, and a comprehensive JUnit Test Suite.

---

## ⚙️ Setup & Installation

### 1. Clone the Repository
```bash
git clone https://github.com/shenonv/ocean-view-resort
cd ocean-view-resort
```

### 2. Set Up the Database (MySQL)
Create the `ocean_view_db` and the tables below. Note the inclusion of the `role` column for RBAC.

```sql
CREATE DATABASE ocean_view_db;
USE ocean_view_db;

CREATE TABLE users (
    user_id  INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role     VARCHAR(20) NOT NULL DEFAULT 'STAFF'
);

CREATE TABLE reservations (
    id              INT AUTO_INCREMENT PRIMARY KEY,
    guest_name      VARCHAR(100) NOT NULL,
    email           VARCHAR(150),
    address         VARCHAR(255),
    contact_number  VARCHAR(20),
    room_type       VARCHAR(50) NOT NULL,
    check_in_date   DATE NOT NULL,
    check_out_date  DATE NOT NULL
);

-- Seed initial Admin account
INSERT INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
```

### 3. Build & Run
1. Update `DBConnection.java` with your local MySQL credentials.
2. Run `mvn clean package` to generate the WAR file.
3. Deploy to your local **Tomcat** server.
4. Access via: `http://localhost:8080/OceanViewResort/login.jsp`

---

## 📧 Email Configuration
Email credentials are kept in a **gitignored** `email.properties` file for security. Use `email.properties.example` as a template. You must use a **Gmail App Password** if using Gmail SMTP.

---

<div align="center">
Made with ☕ and Java · OceanView Resort © 2026 · shenonv
</div>
