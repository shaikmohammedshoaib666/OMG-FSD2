# Case Study 12 — Internship Application Tracker

## What it is
Servlet + JSP web app on Tomcat 9 + Oracle XE. Students register/login, browse internships, apply; coordinator publishes internships and updates applicant status with remarks.

## Run it
1. `docker start oracle-xe` (Oracle XE on FREEPDB1)
2. Run `case-study-12/SCHEMA.sql` once in Oracle (creates applicants, internships, applications + demo data)
3. IntelliJ → open `fsd2-web` → SmartTomcat ▶
4. Open `http://localhost:8080/fsd2-web/case-study/login.html`

## Demo logins
- Student: `STU001` / `stu123` (or register a new one)
- Coordinator: `COORD01` / `coord123`

## URLs
| Page | URL |
|---|---|
| Login | `/fsd2-web/case-study/login.html` |
| Register | `/fsd2-web/case-study/register.html` |
| Student home | `/fsd2-web/cs/home` |
| Internships/search | `/fsd2-web/cs/internships?action=view&q=TCS` |
| My applications | `/fsd2-web/cs/internships?action=myapps` |
| Coordinator | `/fsd2-web/cs/coord` |
| Logout | `/fsd2-web/cs/logout` |

## Files
- `src/casestudy/` — 6 servlets (Login, Register, Home, Internship, Coordinator, Logout)
- `web/case-study/` — login.html, register.html, home.jsp, internships.jsp, myapps.jsp, coord.jsp, applicants.jsp
- `case-study-12/SCHEMA.sql`, this README, `VIVA_GUIDE.md`
- DB config reused from `servlets/DbConfig.java`; mappings added in `web/WEB-INF/web.xml` (`/cs/*`)

## PS → code map
- Login (roll/email+password, session, redirect by role) → `LoginServlet`
- Registration + validation + insert → `RegisterServlet`
- View/search internships → `InternshipServlet?action=view` (LIKE on org/role/location)
- Apply (last-date check, duplicate check, status='Applied') → `InternshipServlet#doPost`
- My applications + remarks → `InternshipServlet?action=myapps` → `myapps.jsp`
- Coordinator publish → `CoordinatorServlet#doPost?action=publish`
- View applicants / update status+remarks → `CoordinatorServlet?action=applicants` / `doPost?action=update`
- Access control → `getSession(false)` null-check → redirect login in every protected servlet/JSP
- Logout → `LogoutServlet` (`session.invalidate()`)
