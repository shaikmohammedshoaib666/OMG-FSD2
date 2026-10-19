# Docker Setup - Easiest Way to Share Project

With Docker, anyone can run your project with ONE command!

## What Your Friend Needs

ONLY Docker Desktop installed (https://www.docker.com/products/docker-desktop)

That's it! No IntelliJ, no Tomcat, no Oracle installation needed.

## How to Run

### Step 1: Clone the Repository
```bash
git clone https://github.com/shaikmohammedshoaib666/OMG-FSD2.git
cd OMG-FSD2/fsd2-web
```

### Step 2: Run with Docker Compose
```bash
docker-compose -f docker-compose-simple.yml up
```

That's it! Docker will:
- ✅ Download Oracle Database
- ✅ Download Tomcat
- ✅ Start both containers
- ✅ Link them together
- ✅ Run your web application

### Step 3: Access the Application
Open browser: `http://localhost:8080/`

Login credentials:
- Student: `shoaib` / `system`
- Admin: `admin` / `admin123`

### Step 4: Stop When Done
```bash
docker-compose -f docker-compose-simple.yml down
```

## What Happens Behind the Scenes

Docker creates 2 containers:
1. **oracle-db**: Oracle XE database (port 1521)
2. **webapp**: Tomcat + your code (port 8080)

They talk to each other automatically via Docker network.

## Notes

- First run takes 5-10 minutes (downloads images)
- Subsequent runs are fast (images already downloaded)
- All data is stored in Docker volumes
- No need to install anything else!

## Troubleshooting

### Docker not running
- Open Docker Desktop app
- Wait for it to start

### Port already in use
- Stop other Tomcat/Oracle instances
- Or change ports in docker-compose-simple.yml

### Database connection error
- Wait 30-60 seconds after starting
- Oracle needs time to initialize
