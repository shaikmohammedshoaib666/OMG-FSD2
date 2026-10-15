# FSD2 Web Application - Student Feedback System

A full-stack web application built with Java Servlets, JSP, and Oracle Database for FSD2 course.

## 📋 Project Overview

This repository includes FSD2 course work:

### 1. Web Application (fsd2-web/)
- **Servlet-based web application** with JDBC connectivity
- **Student Feedback System** (Mini Project)
- Session management and role-based access control
- JSP pages with JavaBeans
- CRUD operations on Oracle database

### 2. JDBC Programs (fsd2-jdbc-maven/)
- Maven-based Java JDBC programs
- 16 programs for Oracle and MySQL
- CRUD operations, PreparedStatements, dynamic input
- See `fsd2-jdbc-maven/JDBC_README.md` for details

## 🚀 Mini Project: Student Feedback System

### Features
- **Student Login**: Students can login and submit feedback
- **Admin Login**: Admins can login and view all feedback
- **Role-Based Access**: Different dashboards for students and admins
- **Session Management**: Secure login/logout with session tracking
- **Database Storage**: Feedback stored in Oracle database

### User Credentials

| Role | Username | Password |
|------|----------|----------|
| Student | shoaib | system |
| Admin | admin | admin123 |

## 🛠️ Technologies Used

- **Java 21**
- **Java Servlets (javax.servlet)**
- **JSP (JavaServer Pages)**
- **Tomcat 9**
- **Oracle Database XE (FREEPDB1)**
- **Maven**
- **IntelliJ IDEA (Community Edition with SmartTomcat)**
- **HTML, CSS**

## 📁 Project Structure

```
OMG-FSD2/
├── fsd2-jdbc-maven/      # JDBC Programs (Maven)
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── Dbconn/ # All JDBC programs
│   ├── pom.xml           # Maven dependencies
│   └── JDBC_README.md    # JDBC documentation
├── src/                  # Web Application
│   ├── servlets/          # All servlet classes
│   │   ├── LoginServlet.java
│   │   ├── LogoutServlet.java
│   │   ├── DashboardServlet.java
│   │   ├── FeedbackServlet.java
│   │   ├── AdminFeedbackServlet.java
│   │   ├── CrudServlet.java
│   │   └── ...
│   └── beans/             # JavaBeans
│       ├── UserBean.java
│       ├── StudentBean.java
│       └── FeedbackBean.java
├── web/
│   ├── jsp/               # JSP pages
│   │   ├── feedback-form.jsp
│   │   ├── admin-dashboard.jsp
│   │   ├── calculator.jsp
│   │   └── ...
│   ├── WEB-INF/
│   │   └── web.xml        # Servlet mappings
│   ├── login.html
│   └── index.html
└── lib/
    └── ojdbc11.jar        # Oracle JDBC driver
```

## 🔧 Setup Instructions

### Prerequisites
1. **Java 21** installed
2. **IntelliJ IDEA** (Community Edition)
3. **Oracle Database XE** running on localhost:1521/FREEPDB1
4. **Tomcat 9** installed
5. **SmartTomcat plugin** installed in IntelliJ

### Database Setup

1. Start Oracle Database:
```bash
docker start oracle-xe
```

2. Create tables (run in SQLPlus or SQL Developer):
```sql
-- Users table for authentication
CREATE TABLE users (
    username VARCHAR2(50) PRIMARY KEY,
    password VARCHAR2(50) NOT NULL,
    role VARCHAR2(20) NOT NULL
);

-- Insert users
INSERT INTO users VALUES ('shoaib', 'system', 'student');
INSERT INTO users VALUES ('admin', 'admin123', 'admin');

-- Students table
CREATE TABLE students (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    branch VARCHAR2(30),
    year NUMBER
);

-- Feedback table
CREATE TABLE feedback (
    id NUMBER PRIMARY KEY,
    username VARCHAR2(50),
    subject VARCHAR2(100),
    message VARCHAR2(500),
    rating NUMBER,
    submitted_date DATE DEFAULT SYSDATE
);
```

### Running the Project

1. **Open in IntelliJ**:
   ```
   File → Open → /Users/sk.md.shoaib.raza/Projects/fsd2-web
   ```

2. **Configure Tomcat** (if not already done):
   - IntelliJ IDEA → Settings → Build, Execution, Deployment → Application Servers
   - Click + → Tomcat Server → Set path to Tomcat 9
   - OK

3. **Run the Application**:
   - Look at top-right dropdown in IntelliJ
   - Select "FSD2 Tomcat" (or your Tomcat configuration)
   - Click the green ▶ Run button
   - Wait for Tomcat to start
   - Browser will open automatically to: `http://localhost:8080/fsd2-web/`

### Database Table Setup

The web application requires two tables in Oracle. Run these Java programs to create them:

1. **Create students table**:
   - Open `fsd2-jdbc-maven/src/main/java/Dbconn/StudentCRUD.java`
   - Run it (creates students table with test data)

2. **Add year column** (if needed):
   - Open `fsd2-jdbc-maven/src/main/java/Dbconn/AddYearColumn.java`
   - Run it (adds missing year column to students table)

3. **Create feedback table** (for mini project):
   - Open `fsd2-jdbc-maven/src/main/java/Dbconn/CreateFeedbackTable.java`
   - Run it (creates feedback table)

## 🌐 Accessing the Application

### Main Page
```
http://localhost:8080/fsd2-web/
```

### Mini Project URLs

**Student Login:**
```
http://localhost:8080/fsd2-web/login.html
```
- Login with: `shoaib` / `system`
- Navigate to: Submit Feedback

**Admin Login:**
```
http://localhost:8080/fsd2-web/login.html
```
- Login with: `admin` / `admin123`
- Navigate to: View All Feedback (Admin)

### Other Lab URLs

| Feature | URL |
|---------|-----|
| CRUD Operations | `http://localhost:8080/fsd2-web/crud` |
| Display Records | `http://localhost:8080/fsd2-web/records` |
| Insert Student | `http://localhost:8080/fsd2-web/insert-student` |
| JSP Calculator | `http://localhost:8080/fsd2-web/jsp/calculator.jsp` |
| Dashboard | `http://localhost:8080/fsd2-web/dashboard` |

## 📝 Labs Completed

### Unit 1: Servlets + JDBC
1. JDBC CRUD Operations
2. Login Form (doPost)
3. Display DB Records
4. Insert Student (PreparedStatement)
5. Session Tracking

### Unit 2: JSP + MVC
1. JSP Calculator
2. JSP Display DB Records
3. JavaBeans with JSP
4. MVC Login (Servlet → Bean → JSP)
5. Student Registration System

### Mini Project: Student Feedback System
- Student authentication and feedback submission
- Admin authentication and feedback viewing
- Role-based access control
- Session management

## 👨‍💻 Author

**Shaik Mohammed Shoaib**
- FSD2 Course Project
- Full Stack Development

## 📄 License

This project is for educational purposes.
