# FindIt - Campus Lost & Found System


## Overview

FindIt is a campus-wide lost and found management system designed specifically for CST (College of Science and Technology). Every semester, students lose phones, ID cards, laptops, water bottles, and more - with no official system to report or recover them. FindIt solves this by providing a secure, organized platform where items are only returned to rightful owners after proper verification.

## Problem Statement

Right now, lost items are reported through WhatsApp groups, word of mouth, or simply never recovered. Anyone can claim to own something without proof. FindIt eliminates this by requiring claimants to answer specific verification questions before admin approval.

## Features

- Report Found Items - Post with photo, description, location, and date
- Search Lost Items - Filter by category, location, or date
- Submit Claims - Answer verification questions to prove ownership
- Admin Verification - Claims reviewed before handover
- Real-time Tracking - Monitor lost/found item status
- Email Notifications - Receive updates on claims and matches

## Technology Stack

Backend: Spring Boot (Java)
Frontend: React
Database: PostgreSQL
Authentication: JWT
File Storage: Cloudinary
Notifications: Email (JavaMail)

## Project Structure

FindIt/
├── backend/
│   ├── src/main/java/com/findit/
│   │   ├── config/           # Security & JWT config
│   │   ├── controller/       # REST API endpoints
│   │   ├── model/            # JPA entities
│   │   ├── repository/       # Database repositories
│   │   ├── service/          # Business logic
│   │   ├── dto/              # Data transfer objects
│   │   └── FindItApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
│
├── frontend/
│   ├── src/
│   │   ├── components/       # React components
│   │   ├── pages/            # Page views
│   │   ├── services/         # API services
│   │   └── App.jsx
│   └── package.json
│
├── database/
│   ├── schema.sql
│   └── seed.sql
│
└── docs/
    ├── API.md
    ├── SETUP.md
    └── USER_GUIDE.md

## Database Schema

-- Users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'STUDENT',
    student_id VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Items table
CREATE TABLE items (
    id SERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    location VARCHAR(200),
    date_found TIMESTAMP,
    photo_url VARCHAR(500),
    status VARCHAR(20) DEFAULT 'PENDING',
    posted_by INTEGER REFERENCES users(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Claims table
CREATE TABLE claims (
    id SERIAL PRIMARY KEY,
    item_id INTEGER REFERENCES items(id),
    claimant_id INTEGER REFERENCES users(id),
    answers TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    reviewed_by INTEGER REFERENCES users(id),
    reviewed_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

## API Endpoints

POST   /api/auth/register     - User registration
POST   /api/auth/login        - User login
GET    /api/items             - Get all items
POST   /api/items             - Report found item
GET    /api/items/{id}        - Get item details
POST   /api/claims            - Submit claim
PUT    /api/claims/{id}/approve - Approve claim (admin)
PUT    /api/claims/{id}/reject  - Reject claim (admin)

## Team Members

Provis82 (https://github.com/Provis82) - Lead Backend Developer
- Spring Boot application setup and configuration
- REST API development
- Database design and integration
- JWT authentication and security
- Email notification service
- Deployment and DevOps

madboy-creator (https://github.com/madboy-creator/) - Lead Frontend Developer
- React application architecture
- UI/UX design implementation
- Component development
- State management
- API integration
- Responsive design

## Getting Started

Prerequisites:
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Maven

Quick Setup:

git clone https://github.com/Provis82/FindIt.git
cd FindIt

# Backend
cd backend
./mvnw spring-boot:run

# Frontend (new terminal)
cd frontend
npm install
npm start

## Workflow

User Finds Item -> Report Found Item -> Item Posted -> Owner Submits Claim -> Admin Verifies Claim -> Handover

User Loses Item -> Report Lost Item -> Search Items -> Found Item Found -> Admin Verifies Claim -> Handover

## Future Enhancements

- Mobile app (iOS/Android)
- AI-powered item matching
- QR code stickers for valuables
- Integration with campus security
- Analytics dashboard

## License

Academic project for CST

Built with Spring Boot and React for the CST community