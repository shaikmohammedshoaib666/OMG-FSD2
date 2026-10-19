# Instructions for Your Friend to Run This Project

## What Your Friend Needs

**Option 1: Reliable (Recommended)**
- IntelliJ IDEA Community Edition (free)
- Java 21
- Docker Desktop (for Oracle database only)
- Tomcat 9

**Option 2: Docker-Only (Advanced)**
- Docker Desktop only
- ⚠️ Note: Oracle image is very large (several GB), first download can take 10-20 minutes or timeout

---

## Option 1: Reliable Method (IntelliJ + Docker for Oracle)

### Step 1: Clone the Repository
```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

### Step 2: Start Oracle Database (Docker)
```bash
docker start oracle-xe
```
If Oracle container doesn't exist:
```bash
docker run -d --name oracle-xe -p 1521:1521 -e ORACLE_PASSWORD=system gvenzl/oracle-xe:21-slim-faststart
```

### Step 3: Create Database Tables
Open IntelliJ IDEA:
- File → Open → OMG-FSD2/fsd2-web
- Open `fsd2-jdbc-maven/src/main/java/Dbconn/StudentCRUD.java`
- Right-click → Run
- Open `AddYearColumn.java` → Run
- Open `CreateFeedbackTable.java` → Run

### Step 4: Configure Tomcat in IntelliJ
- IntelliJ IDEA → Settings → Build, Execution, Deployment → Application Servers
- Click + → Tomcat Server
- Set Tomcat Home to Tomcat 9 folder
- Download Tomcat 9: https://tomcat.apache.org/download-90.cgi

### Step 5: Run the Application
- In IntelliJ, select "FSD2 Tomcat" from dropdown
- Click green ▶ Run button
- Browser opens to: `http://localhost:8080/fsd2-web/`

Note: All URLs include `/fsd2-web/` context path

### Step 6: Login
- Student: `shoaib` / `system`
- Admin: `admin` / `admin123`

---

## Option 2: Docker-Only Method (Advanced)

⚠️ **Warning**: Oracle image is several GB and can timeout on slow connections

### Step 1: Clone the Repository
```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

### Step 2: Run Docker Compose
```bash
docker-compose -f docker-compose-simple.yml up
```

This will:
- Download Oracle XE (several GB, can take 10-20 minutes)
- Download Tomcat
- Start both containers
- Run the application

### Step 3: Open Browser
```
http://localhost:8081/
```

Note: Port is 8081 (not 8080) to avoid conflicts

### Step 4: Login
- Student: `shoaib` / `system`
- Admin: `admin` / `admin123`

### Step 5: Stop
```bash
docker-compose -f docker-compose-simple.yml down
```

---

## Database Information

**Database:** Oracle XE
**User:** system
**Password:** system
**Port:** 1521
**Service:** FREEPDB1

---

## Why Option 1 is Recommended

Option 1 (IntelliJ + Docker for Oracle only) is more reliable because:
- ✅ Faster (Oracle already downloaded locally)
- ✅ No timeout issues
- ✅ Easier to debug if something goes wrong
- ✅ Can see logs in IntelliJ
- ✅ Standard development approach

Option 2 (Docker-only) has issues:
- ❌ Oracle image is very large (several GB)
- ❌ First download can timeout on slow internet
- ❌ Harder to debug issues
- ❌ Port conflicts possible

---

## Troubleshooting

### Option 1 Issues

**Tomcat doesn't start**
- Check Tomcat path in IntelliJ settings
- Make sure Docker is running: `docker ps`

**Database connection error**
- Start Oracle: `docker start oracle-xe`
- Wait 30 seconds for Oracle to initialize
- Run table creation programs first

### Option 2 Issues

**Download timeout**
- Use Option 1 instead (more reliable)
- Oracle image is too large for slow connections

**Port already in use**
- Change ports in docker-compose-simple.yml
- Stop other applications using those ports

---

## What Your Friend Will See

Working application with:
- Student login and feedback submission
- Admin login and feedback viewing
- CRUD operations on students
- All features fully functional

---

## Recommendation

**Use Option 1** (IntelliJ + Docker for Oracle). It's:
- More reliable
- Faster
- Easier to debug
- Standard development approach
