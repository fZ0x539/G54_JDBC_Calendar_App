# 📅 Java Calendar Application

## 🎯 Overview

The **Java Calendar Application** is a lightweight event scheduling tool built with **Java, JDBC, and Streams**. It
allows users to **create calendars, schedule events, and invite participants via email**.

## 📌 Understanding the Application

The Meeting Calendar App is designed to help users organize their schedules efficiently. Each user can create one or multiple calendars, such as **Work Calendar** or **Personal Calendar**, to keep track of different types of events. Users can then schedule events with specific details like title, description, and time. The system will store all this data in a database and allow retrieving, updating, and deleting events when needed.

This project focuses on building a **calendar application** that allows users to:  
✅ **Create and manage calendars** (Work, Personal, etc.)  
✅ **Schedule events with participants**  
✅ **Store and retrieve events from a database**

---
## 📌 Core Features:
- 👤 **Users (Persons)** – Each user has a calendar where they can schedule events.
- 📆 **Calendars** – Users can create multiple calendars for different purposes.
- 📅 **Events** – Each event has a title, description, and scheduled time.
- 📧 **Participants** – Users can invite participants to events via email.
- 🗄️ **Database** – Uses MySQL/PostgreSQL for storing all data.
- 📧 **Email Notifications Using Java Mail API** – Sends reminders for upcoming events (Optional).

## 📌 Application Components
### 1️⃣ **Person (User)**
- A `Person` has an **ID, name, email**, and a list of **calendars**.
- Users can **create, update, and delete** their calendars.
### 2️⃣ **Calendar**
- A `MyCalendar` belongs to a **single user**.
- Users can **add and remove events** from their calendars.
### 3️⃣ **Event**
- An `Event` belongs to a **calendar**.
- Each event has a **title, description, date/time, and participants**.
- Users can **invite participants** by email.

## ⚡ Database Design

### 📌 Tables: `person`, `my_calendar`, `event`, `event_participants`

```sql
DROP DATABASE calendar_db;
CREATE DATABASE calendar_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE calendar_db;

CREATE TABLE person
(
    id    INT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(100)        NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE my_calendar
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    person_id   INT          NOT NULL,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    FOREIGN KEY (person_id) REFERENCES person (id) ON DELETE CASCADE
);

CREATE TABLE event
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    calendar_id INT          NOT NULL,
    title       VARCHAR(100) NOT NULL,
    description TEXT,
    date_time   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (calendar_id) REFERENCES my_calendar (id) ON DELETE CASCADE
);

CREATE TABLE event_participants
(
    event_id INT          NOT NULL,
    email    VARCHAR(100) NOT NULL,
    PRIMARY KEY (event_id, email),
    FOREIGN KEY (event_id) REFERENCES event (id) ON DELETE CASCADE
);
```

## 🎯 **How It Works**

1. A user **creates an account** (Person).
2. The user **creates a calendar** for different events (e.g., Work, Personal).
3. Events are **scheduled inside a calendar** with a specific **date and time**.
4. Users **invite participants** by adding email addresses.
5. (Optional) Email notifications **remind participants** of upcoming events.

---

