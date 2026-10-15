# Setup Guide for FSD2 Web Project

Follow these steps to run the project on your computer.

## Prerequisites

1. **Java 21** installed (download from oracle.com)
2. **IntelliJ IDEA Community Edition** (download from jetbrains.com)
3. **Docker Desktop** (download from docker.com)
4. **Tomcat 9** (download from https://tomcat.apache.org/download-90.cgi)

## Step 1: Clone the Repository

```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

## Step 2: Start Oracle Database

1. Open Docker Desktop
2. Run this command in terminal:
```bash
docker pull gvenzl/oracle-xe:21-slim-faststart
docker run -d --name oracle-xe -p 1521:1521 -e ORACLE_PASSWORD=system gvenzl/oracle-xe:21-slim-faststart
```

3. Wait 30-60 seconds for Oracle to start
4. Verify it's running: `docker ps`

## Step 3: Open in IntelliJ

1. Open IntelliJ IDEA
2. `File → Open → OMG-FSD2/fsd2-web`
3. Click "Trust Project"

## Step 4: Configure Tomcat

1. In IntelliJ: `IntelliJ IDEA → Settings → Build, Execution, Deployment → Application Servers`
2. Click `+` → `Tomcat Server`
3. Set Tomcat Home to your Tomcat 9 folder
4. Click OK

## Step 5: Create Database Tables

1. In IntelliJ, open: `fsd2-jdbc-maven/src/main/java/Dbconn/StudentCRUD.java`
2. Right-click → Run
3. Wait for "Table STUDENTS created" message
4. Open `AddYearColumn.java` → Run
5. Open `CreateFeedbackTable.java` → Run

## Step 6: Run the Application

1. Look at top-right dropdown in IntelliJ
2. Select "FSD2 Tomcat" (or your Tomcat configuration)
3. Click the green ▶ Run button
4. Wait for Tomcat to start
5. Browser opens to: `http://localhost:8080/fsd2-web/`

## Access the Application

- **Main Page**: `http://localhost:8080/fsd2-web/`
- **Student Login**: `http://localhost:8080/fsd2-web/login.html`
  - Username: `shoaib`
  - Password: `system`
- **Admin Login**: `http://localhost:8080/fsd2-web/login.html`
  - Username: `admin`
  - Password: `admin123`

## Troubleshooting

### Tomcat doesn't start
- Make sure Docker is running
- Make sure Oracle container is running: `docker ps`
- Check Tomcat path in IntelliJ settings

### Database connection error
- Start Oracle: `docker start oracle-xe`
- Wait 30 seconds for Oracle to fully start
- Try again

### 404 errors
- Make sure URL includes `/fsd2-web/`
- Example: `http://localhost:8080/fsd2-web/` (not `http://localhost:8080/`)

## Need Help?

- Check the main README.md for more details
- Contact: [Your Name]
