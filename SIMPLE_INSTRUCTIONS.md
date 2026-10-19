# SIMPLE Instructions for Your Friend

## What Your Friend Probably Has
- IntelliJ IDEA
- Tomcat (any version: 9, 10, or 11)
- Oracle Database installed locally
- SQL knowledge

That's it! No Docker needed.

---

## Step 1: Clone the Repository
```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

---

## Step 2: Open in IntelliJ
- Open IntelliJ IDEA
- File → Open → Select `OMG-FSD2/fsd2-web` folder
- Click "Trust Project"

---

## Step 3: Configure Tomcat in IntelliJ
1. IntelliJ IDEA → Settings → Build, Execution, Deployment → Application Servers
2. Click + → Tomcat Server
3. Set Tomcat Home to your Tomcat folder (wherever Tomcat is installed)
4. Click OK

**Note:** Tomcat 9, 10, or 11 - all work! No need for specific version.

---

## Step 4: Create Database Tables in Oracle

Since your friend has SQL knowledge, they can create tables directly in Oracle:

**Using SQLPlus or SQL Developer:**

```sql
-- Create users table
CREATE TABLE users (
    username VARCHAR2(50) PRIMARY KEY,
    password VARCHAR2(50) NOT NULL,
    role VARCHAR2(20) NOT NULL
);

-- Insert users
INSERT INTO users VALUES ('shoaib', 'system', 'student');
INSERT INTO users VALUES ('admin', 'admin123', 'admin');

-- Create students table
CREATE TABLE students (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50) NOT NULL,
    branch VARCHAR2(30),
    year NUMBER
);

-- Insert test data
INSERT INTO students VALUES (1, 'Shoaib', 'CSE', 3);
INSERT INTO students VALUES (2, 'Aisha', 'IT', 2);

-- Create feedback table
CREATE TABLE feedback (
    id NUMBER PRIMARY KEY,
    student_name VARCHAR2(100) NOT NULL,
    roll_no VARCHAR2(20) NOT NULL,
    course VARCHAR2(50),
    feedback_text VARCHAR2(500),
    submitted_date DATE DEFAULT SYSDATE
);
```

**Or use the Java programs in IntelliJ:**
- Open `fsd2-jdbc-maven/src/main/java/Dbconn/StudentCRUD.java`
- Right-click → Run
- Open `AddYearColumn.java` → Run
- Open `CreateFeedbackTable.java` → Run

---

## Step 5: Update Database Connection (if needed)

If your friend's Oracle is different, update these lines in servlets:

**File:** `src/servlets/DbConfig.java`

```java
public static String getDbHost() {
    return "localhost";  // or your Oracle server IP
}

public static String getDbPort() {
    return "1521";  // or your Oracle port
}

public static String getDbService() {
    return "FREEPDB1";  // or your Oracle service name
}

public static String getDbUser() {
    return "system";  // or your Oracle username
}

public static String getDbPassword() {
    return "system";  // or your Oracle password
}
```

---

## Step 6: Run the Application

1. In IntelliJ, look at top-right dropdown
2. Select your Tomcat configuration
3. Click the green ▶ Run button
4. Browser opens to: `http://localhost:8080/fsd2-web/`

---

## Step 7: Login

**Student Login:**
- Username: `shoaib`
- Password: `system`

**Admin Login:**
- Username: `admin`
- Password: `admin123`

---

## That's It!

**No Docker, no complex setup.** Just:
- Clone
- Open in IntelliJ
- Configure Tomcat
- Create tables in Oracle (they know SQL)
- Run

---

## Troubleshooting

**Tomcat version not supported?**
- Tomcat 9, 10, 11 all work with servlets
- No specific version needed

**Oracle connection error?**
- Update DbConfig.java with your Oracle credentials
- Make sure Oracle is running
- Check host/port/service name

**Port 8080 already in use?**
- Change Tomcat port in IntelliJ settings
- Or stop other applications using port 8080

---

## What Your Friend Needs to Know

This guide assumes your friend:
- Knows how to use IntelliJ
- Has Tomcat installed (any version)
- Has Oracle installed locally
- Knows SQL to create tables

If they don't have Oracle, they can use Docker just for Oracle:
```bash
docker run -d --name oracle-xe -p 1521:1521 -e ORACLE_PASSWORD=system gvenzl/oracle-xe:21-slim-faststart
```

But otherwise, no Docker needed!
