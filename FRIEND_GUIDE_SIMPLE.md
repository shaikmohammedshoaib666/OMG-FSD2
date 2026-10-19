# Guide for Your Friend - Simple Version

## What to Focus On (ONLY This)

When you clone the repo, **IGNORE** these folders:
- ❌ `fsd2-jdbc-maven/` - You don't need this
- ❌ `Dockerfile` - You don't need this
- ❌ `docker-compose.yml` - You don't need this
- ❌ `docker-compose-simple.yml` - You don't need this

**ONLY work with:**
- ✅ `fsd2-web/` folder - This is the web application
- ✅ `src/servlets/DbConfig.java` - This is where you change passwords
- ✅ `web/` folder - This has the HTML/JSP pages

---

## Step 1: Clone the Repository

```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

**Note:** Only work in the `fsd2-web/` folder. Ignore everything else.

---

## Step 2: Open in IntelliJ

1. Open IntelliJ IDEA
2. File → Open → Select `OMG-FSD2/fsd2-web` folder
3. Click "Trust Project"

---

## Step 3: Install Docker

1. Download Docker Desktop: https://www.docker.com/products/docker-desktop
2. Install it
3. Open Docker Desktop
4. Wait for it to start

---

## Step 4: Run Oracle Database in Docker

Open terminal and run:

```bash
docker run -d --name oracle-xe -p 1521:1521 -e ORACLE_PASSWORD=system gvenzl/oracle-xe:21-slim-faststart
```

**What this does:**
- Downloads Oracle Database (first time: ~2GB, takes a few minutes)
- Runs Oracle in a container
- Maps port 1521 to your computer
- Password is set to `system`

**Wait 30-60 seconds** for Oracle to fully start.

**Verify it's running:**
```bash
docker ps
```
You should see `oracle-xe` in the list.

---

## Step 5: Change Database Password (IMPORTANT)

Open this file in IntelliJ:
```
fsd2-web/src/servlets/DbConfig.java
```

Look for these lines and change them:

```java
public static String getDbUser() {
    return "system";  // ← Change to YOUR Oracle username
}

public static String getDbPassword() {
    return "system";  // ← Change to YOUR Oracle password
}
```

**Example:**
- If your Oracle username is `scott` and password is `tiger`:
```java
public static String getDbUser() {
    return "scott";
}

public static String getDbPassword() {
    return "tiger";
}
```

**Save the file.**

---

## Step 6: Create Tables in Oracle

Since you know SQL, run these commands in your Oracle tool (SQLPlus, SQL Developer, etc.):

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

**That's it.** The tables are now created.

---

## Step 7: Configure Tomcat in IntelliJ

1. In IntelliJ: `IntelliJ IDEA → Settings → Build, Execution, Deployment → Application Servers`
2. Click `+` → `Tomcat Server`
3. Set "Tomcat Home" to your Tomcat folder (Tomcat 9, 10, or 11 - all work)
4. Click OK

**Note:** If you don't have Tomcat, download it: https://tomcat.apache.org/download-90.cgi

---

## Step 8: Run the Application

1. Look at the top-right corner of IntelliJ
2. You'll see a dropdown menu near the green ▶ button
3. Select your Tomcat configuration from the dropdown
4. Click the **green ▶ Run button**
5. Wait for Tomcat to start
6. Browser will automatically open to: `http://localhost:8080/fsd2-web/`

---

## Step 9: Login

**Student Login:**
- Username: `shoaib`
- Password: `system`

**Admin Login:**
- Username: `admin`
- Password: `admin123`

---

## Important Notes

### Database
- **This code ONLY works with Oracle Database**
- It will NOT work with SQL Server, MySQL, or PostgreSQL
- If you don't have Oracle, use Docker (Step 4) to run Oracle

### Tomcat Version
- Tomcat 9, 10, or 11 - all work
- No specific version needed

### Files to Edit
- **ONLY edit:** `src/servlets/DbConfig.java` (for password changes)
- **DON'T edit:** Anything else unless you know what you're doing

### Files to Ignore
- `fsd2-jdbc-maven/` - Ignore this
- `Dockerfile` - Ignore this
- `docker-compose.yml` - Ignore this
- `docker-compose-simple.yml` - Ignore this

---

## What If Docker Oracle Doesn't Work?

If Docker is too slow or times out, use your local Oracle installation:

1. Start your local Oracle
2. Skip Step 4 (Docker)
3. Update DbConfig.java with your local Oracle credentials
4. Create tables in your local Oracle (Step 6)
5. Continue with Step 7

---

## Troubleshooting

**"Cannot connect to database"**
- Make sure Oracle is running (check with `docker ps`)
- Wait 30-60 seconds after starting Oracle
- Check if you updated DbConfig.java with correct password

**"Port 1521 already in use"**
- Stop other Oracle instances: `docker stop oracle-xe`
- Or change port in Docker command

**"Tomcat doesn't start"**
- Check Tomcat path in IntelliJ settings
- Make sure you have Tomcat installed

**"404 error"**
- Make sure URL includes `/fsd2-web/`
- Example: `http://localhost:8080/fsd2-web/` (not `http://localhost:8080/`)

---

## Summary

**What you need:**
- IntelliJ IDEA
- Tomcat (any version)
- Oracle Database (or Docker for Oracle)
- Java 21

**What to do:**
1. Clone repo
2. Open in IntelliJ
3. Run Oracle in Docker
4. Change password in DbConfig.java
5. Create tables in Oracle
6. Configure Tomcat in IntelliJ
7. Run in IntelliJ

**That's it!** The application will work.
