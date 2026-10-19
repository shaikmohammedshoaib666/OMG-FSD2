# Instructions for Your Friend to Run This Project

## What Your Friend Needs (Only 1 Thing)

**Docker Desktop** - Download from: https://www.docker.com/products/docker-desktop

That's it! No IntelliJ, no Tomcat, no Oracle installation needed.

---

## Step-by-Step Instructions

### Step 1: Clone the Repository
Open terminal (Command Prompt/PowerShell on Windows, Terminal on Mac/Linux) and run:

```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

### Step 2: Make Sure Docker is Running
- Open Docker Desktop application
- Wait for it to start (you'll see the Docker icon in the menu bar/taskbar)
- Make sure it says "Docker Desktop is running"

### Step 3: Run the Project
In the same terminal, run:

```bash
docker-compose -f docker-compose-simple.yml up
```

**What happens:**
- Docker downloads Oracle Database (first time: 5-10 minutes)
- Docker downloads Tomcat (first time: 2-3 minutes)
- Docker starts both containers
- Docker links them together
- Your web application starts

### Step 4: Wait for It to Start
Wait 1-2 minutes. You'll see messages like:
- "oracle-xe is up-to-date"
- "fsd2-webapp is up-to-date"
- "Listening on port 8080"

### Step 5: Open in Browser
Open your web browser and go to:

```
http://localhost:8080/
```

### Step 6: Login

**For Student Login:**
- Username: `shoaib`
- Password: `system`

**For Admin Login:**
- Username: `admin`
- Password: `admin123`

### Step 7: Explore
Once logged in, you can:
- View student records
- Add new students
- Perform CRUD operations
- Submit feedback (if student)
- View all feedback (if admin)

### Step 8: Stop When Done
Go back to terminal and press `Ctrl+C` to stop everything.

Or run:
```bash
docker-compose -f docker-compose-simple.yml down
```

---

## Database Information

**Database:** Oracle XE (runs in Docker container)
**Host:** oracle-db (Docker internal name)
**Port:** 1521
**Service:** FREEPDB1
**User:** system
**Password:** system

**Note:** You don't need to configure this - Docker handles it automatically!

---

## Troubleshooting

### "Docker is not running"
- Open Docker Desktop application
- Wait for it to start
- Try the command again

### "Port 8080 is already in use"
- Stop other applications using port 8080
- Or change the port in docker-compose-simple.yml

### "Cannot connect to database"
- Wait 30-60 seconds after starting
- Oracle needs time to initialize
- Try refreshing the browser

### First run is slow
- Docker is downloading images (Oracle + Tomcat)
- First run takes 5-10 minutes
- Subsequent runs are fast (images already downloaded)

---

## What Your Friend Will See

When they open `http://localhost:8080/`, they'll see:
- FSD2 Full Stack Development Labs
- Links to all labs and the mini project
- Working student feedback system
- All features fully functional

---

## Important Notes

- **No installation needed** - Docker handles everything
- **No configuration needed** - All settings are in docker-compose.yml
- **No IntelliJ needed** - Runs directly in browser
- **No Tomcat needed** - Tomcat runs in Docker container
- **No Oracle needed** - Oracle runs in Docker container

---

## After They're Done

To completely clean up:
```bash
docker-compose -f docker-compose-simple.yml down
docker volume rm fsd2-web_oracle-data
```

This removes all containers and data.

---

## Need Help?

If they have issues:
1. Make sure Docker Desktop is running
2. Make sure they cloned the correct folder
3. Check the DOCKER_SETUP.md file for more details
