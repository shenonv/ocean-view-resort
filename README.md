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
| 🔐 **Secure Login** | Session-based authentication with servlet filter protection |
| 🛏️ **Room Booking** | Book Standard, Deluxe, or Suite rooms with date validation |
| 💰 **Dynamic Billing** | Per-room-type pricing with automatic surcharges |
| 📧 **Email Confirmation** | Automated HTML email sent to guest on reservation creation |
| 📄 **Bill Printing** | Printable bill view for each confirmed reservation |
| 🔍 **View Reservations** | Look up any reservation by ID |
| 🚪 **Logout** | Safe session invalidation |

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
| **Server** | Apache Tomcat (any compatible version) |
| **Frontend** | HTML5 / CSS3 / JSP |

---

## 🎨 Design Patterns

This project implements **four classic GoF design patterns**. Each pattern solves a specific real-world problem in the codebase.

---

### 1. 🏭 Factory Method Pattern

> **Package:** `com.oceanview.factory`

**Problem:** Creating different types of room objects (`StandardRoom`, `DeluxeRoom`, `SuiteRoom`) without tightly coupling the service layer to concrete classes.

**Solution:** A `RoomFactory` interface defines a `createRoom()` method. Each concrete factory encapsulates the creation of a specific room type.

```
«interface»
RoomFactory
    └── createRoom() : Room
         ├── StandardRoomFactory  →  StandardRoom  ($100/night)
         ├── DeluxeRoomFactory    →  DeluxeRoom    ($200/night)
         └── SuiteRoomFactory     →  SuiteRoom     ($350/night)
```

**Files involved:**
- `RoomFactory.java` — Factory interface
- `StandardRoomFactory.java` — Creates standard rooms
- `DeluxeRoomFactory.java` — Creates deluxe rooms
- `SuiteRoomFactory.java` — Creates suite rooms

**How it's used in `ReservationService`:**
```java
RoomFactory roomFactory;
if (roomType.equalsIgnoreCase("Standard")) {
    roomFactory = new StandardRoomFactory();
} else if (roomType.equalsIgnoreCase("Deluxe")) {
    roomFactory = new DeluxeRoomFactory();
} else {
    roomFactory = new SuiteRoomFactory();
}
Room room = roomFactory.createRoom(); // ← Factory Method called
```

---

### 2. 💡 Strategy Pattern

> **Package:** `com.oceanview.strategy`

**Problem:** Each room type has a different billing calculation rule. Hardcoding all billing logic with `if/else` chains would be brittle and hard to extend.

**Solution:** A `BillingStrategy` interface is implemented by each room type's billing class. The correct strategy is selected at runtime via `BillingStrategyFactory`.

```
«interface»
BillingStrategy
    └── calculateBill(nights, pricePerNight) : double
         ├── StandardBillingStrategy  →  nights × price  (no surcharge)
         ├── DeluxeBillingStrategy    →  nights × price + 10% service charge
         └── SuiteBillingStrategy     →  nights × price + 15% service charge
```

**Files involved:**
- `BillingStrategy.java` — Strategy interface
- `StandardBillingStrategy.java` — Base price only
- `DeluxeBillingStrategy.java` — Base price + 10% surcharge
- `SuiteBillingStrategy.java` — Base price + 15% surcharge
- `BillingStrategyFactory.java` — Selects the right strategy by room type

**How it's used:**
```java
BillingStrategy strategy = BillingStrategyFactory.getStrategy(room.getType());
double totalBill = strategy.calculateBill((int) nights, room.getPricePerNight());
```

---

### 3. 👁️ Observer Pattern

> **Package:** `com.oceanview.observer`

**Problem:** After a reservation is saved, multiple side-effects need to happen (e.g., send an email, log the booking). These shouldn't be hardcoded into `ReservationService` — the service should not know about email logic.

**Solution:** `ReservationService` maintains a list of `ReservationObserver` instances. Any observer registered before `createReservation()` is called will be notified automatically after the reservation is saved.

```
ReservationService  (Subject)
    │  notifyObservers(reservation)
    ▼
«interface»
ReservationObserver
    └── onReservationCreated(Reservation)
         └── EmailNotificationService  →  sends HTML confirmation email
```

**Files involved:**
- `ReservationObserver.java` — Observer interface
- `EmailNotificationService.java` — Concrete observer; sends SMTP email
- `ReservationService.java` — Subject; holds observer list and fires events

**How it's wired in `ReservationServlet`:**
```java
ReservationService service = new ReservationService();
service.addObserver(new EmailNotificationService()); // ← register observer
service.createReservation(reservation);              // ← notifyObservers() fires inside
```

---

### 4. 🔒 Servlet Filter Pattern *(Structural — Security)*

> **Package:** `com.oceanview.filter`

**Problem:** Every protected page (dashboard, add reservation, view reservation, etc.) must verify the user is logged in. Duplicating this check in every servlet is error-prone.

**Solution:** `AuthenticationFilter` intercepts every request. If the session has no `user` attribute, the request is redirected to the login page before any servlet is reached.

```
HTTP Request
    │
    ▼
AuthenticationFilter  ──── no session? ──→  redirect to login.jsp
    │
   yes (logged in)
    │
    ▼
Servlet / JSP  (normal flow continues)
```

**Files involved:**
- `AuthenticationFilter.java` — Intercepts all requests, checks session

---

## 📁 Project Structure

```
OceanViewResort/
│
├── src/main/
│   ├── java/com/oceanview/
│   │   ├── controller/                  ← Servlets (MVC Controllers)
│   │   │   ├── LoginServlet.java
│   │   │   ├── LogoutServlet.java
│   │   │   ├── ReservationServlet.java  ← Main booking controller
│   │   │   ├── ViewReservationServlet.java
│   │   │   └── PrintBillServlet.java
│   │   │
│   │   ├── dao/                         ← Data Access Layer
│   │   │   ├── ReservationDAO.java
│   │   │   └── UserDAO.java
│   │   │
│   │   ├── factory/                     ← Factory Method Pattern
│   │   │   ├── RoomFactory.java
│   │   │   ├── StandardRoomFactory.java
│   │   │   ├── DeluxeRoomFactory.java
│   │   │   ├── SuiteRoomFactory.java
│   │   │   └── BillingStrategyFactory.java
│   │   │
│   │   ├── strategy/                    ← Strategy Pattern
│   │   │   ├── BillingStrategy.java
│   │   │   ├── StandardBillingStrategy.java
│   │   │   ├── DeluxeBillingStrategy.java
│   │   │   └── SuiteBillingStrategy.java
│   │   │
│   │   ├── observer/                    ← Observer Pattern
│   │   │   ├── ReservationObserver.java
│   │   │   └── EmailNotificationService.java
│   │   │
│   │   ├── filter/                      ← Security Filter
│   │   │   └── AuthenticationFilter.java
│   │   │
│   │   ├── model/                       ← Domain Models
│   │   │   ├── Reservation.java
│   │   │   ├── ReservationResult.java
│   │   │   ├── Room.java
│   │   │   ├── StandardRoom.java
│   │   │   ├── DeluxeRoom.java
│   │   │   ├── SuiteRoom.java
│   │   │   └── User.java
│   │   │
│   │   ├── service/                     ← Business Logic
│   │   │   └── ReservationService.java
│   │   │
│   │   └── util/                        ← Utilities (DB Connection, etc.)
│   │
│   ├── resources/
│   │   ├── email.properties             ← 🔒 Gitignored — your real credentials
│   │   └── email.properties.example     ← ✅ Committed — safe template
│   │
│   └── webapp/                          ← JSP Views
│       ├── login.jsp
│       ├── dashboard.jsp
│       ├── addReservation.jsp
│       ├── reservationSuccess.jsp
│       ├── viewReservation.jsp
│       ├── printBill.jsp
│       ├── help.jsp
│       └── WEB-INF/
│           └── web.xml
│
├── pom.xml                              ← Maven build config
├── .gitignore
└── README.md
```

---

## 🏗️ Architecture

The application follows a classic **MVC (Model-View-Controller)** layered architecture:

```
┌─────────────────────────────────────────────────────────┐
│                        Browser                          │
│                  (HTML Form / JSP View)                 │
└─────────────────────────┬───────────────────────────────┘
                          │  HTTP Request
                          ▼
┌─────────────────────────────────────────────────────────┐
│              AuthenticationFilter (Security)            │
└─────────────────────────┬───────────────────────────────┘
                          │  (if authenticated)
                          ▼
┌─────────────────────────────────────────────────────────┐
│               Controller Layer (Servlets)               │
│  LoginServlet │ ReservationServlet │ ViewReservation... │
└─────────────────────────┬───────────────────────────────┘
                          │  calls
                          ▼
┌─────────────────────────────────────────────────────────┐
│               Service Layer (Business Logic)            │
│                   ReservationService                    │
│          Factory Method + Strategy + Observer           │
└──────────────────┬──────────────────┬───────────────────┘
                   │                  │
          calls    ▼        notifies  ▼
┌──────────────────────┐  ┌───────────────────────────────┐
│    DAO Layer         │  │  Observer (EmailNotification) │
│  ReservationDAO      │  │  Sends HTML email via SMTP    │
│  UserDAO             │  └───────────────────────────────┘
└──────────┬───────────┘
           │  SQL queries
           ▼
┌─────────────────────┐
│     MySQL Database  │
└─────────────────────┘
```

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 11 or higher
- Apache Maven 3.x
- MySQL 8.0
- Apache Tomcat 9.x (or any Servlet 4.0 compatible server)
- IntelliJ IDEA (recommended)

### 1. Clone the Repository

```bash
git clone https://github.com/shenonv/ocean-view-resort
cd ocean-view-resort
```

### 2. Set Up the Database

Create a MySQL database and the required tables:

```sql
CREATE DATABASE oceanview_resort;
USE oceanview_resort;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    guest_name VARCHAR(100) NOT NULL,
    email VARCHAR(150),
    address VARCHAR(255),
    contact_number VARCHAR(20),
    room_type VARCHAR(50) NOT NULL,
    check_in_date DATE NOT NULL,
    check_out_date DATE NOT NULL
);

-- Insert a default admin user
INSERT INTO users (username, password) VALUES ('admin', 'admin123');
```

### 3. Configure Database Connection

Update the database URL, username, and password in your utility/connection class (typically in `src/main/java/com/oceanview/util/`).

### 4. Configure Email (Optional)

```bash
# Copy the example file
cp src/main/resources/email.properties.example src/main/resources/email.properties

# Edit with your credentials
```

See [Email Configuration](#-email-configuration) below.

### 5. Build & Deploy

```bash
# Build the WAR file
mvn clean package

# Deploy target/OceanViewResort.war to your Tomcat webapps/ folder
# Then start Tomcat and visit:
# http://localhost:8080/OceanViewResort/login.jsp
```

---

## 📧 Email Configuration

Email credentials are stored in a **gitignored properties file** to keep secrets out of version control.

### Setup

1. Copy the example file:
   ```
   src/main/resources/email.properties.example  →  email.properties
   ```

2. Fill in `email.properties`:
   ```properties
   email.sender=your-email@gmail.com
   email.password=abcd efgh ijkl mnop   # 16-char Gmail App Password
   email.smtp.host=smtp.gmail.com
   email.smtp.port=587
   ```

3. **How to get a Gmail App Password:**
   - Go to your Google Account → **Security**
   - Enable **2-Step Verification**
   - Search **"App Passwords"** → Create one for Mail
   - Paste the 16-character code as `email.password`

> ⚠️ `email.properties` is listed in `.gitignore` and will **never be committed** to version control.

---

## 🖥️ Pages & Navigation

| URL | Page | Description |
|---|---|---|
| `/login.jsp` | Login | Staff login with username & password |
| `/dashboard.jsp` | Dashboard | Home hub after login |
| `/addReservation.jsp` | Add Reservation | Form to book a new room |
| `/addReservation` (POST) | — | Servlet processes booking + sends email |
| `/reservationSuccess.jsp` | Confirmation | Shows reservation ID and total bill |
| `/viewReservation.jsp` | View Reservation | Look up a reservation by ID |
| `/printBill.jsp` | Print Bill | Printable bill for a reservation |
| `/help.jsp` | Help | Help & usage guide |
| `/logout` | Logout | Invalidates session, redirects to login |

---

## 💡 Design Pattern Summary Table

| Pattern | Category | Problem Solved | Classes |
|---|---|---|---|
| **Factory Method** | Creational | Decouple room object creation from service logic | `RoomFactory`, `*RoomFactory`, `*Room` |
| **Strategy** | Behavioural | Pluggable billing rules per room type | `BillingStrategy`, `*BillingStrategy` |
| **Observer** | Behavioural | Decouple post-reservation side-effects (email) from core booking logic | `ReservationObserver`, `EmailNotificationService` |
| **Filter (Chain of Responsibility)** | Structural | Centralised authentication guard for all protected routes | `AuthenticationFilter` |

---

<div align="center">

Made with ☕ and Java · OceanView Resort © 2026 · shenonv

</div>
