# Viva / Write-up Guide — Case Study 12

## Architecture
Three-tier: JSP/HTML (presentation) → Servlet (controller + business logic) → Oracle XE via JDBC (DbConfig). Session tracks `roll_no`, `name`, `role`.

## Flow to explain
1. **Login** — `LoginServlet` queries `applicants` by `roll_no OR email` + `password`; on success stores session attributes and redirects: `coordinator` → `/cs/coord`, else `/cs/home`. Invalid → error HTML on login page.
2. **Register** — `RegisterServlet` validates required fields, email format, 10-digit phone, password length, duplicate roll/email check, then INSERT with `role='student'`.
3. **Browse** — `InternshipServlet?action=view` runs a parameterized `LIKE` query across org/role/location; `open_flag` computed via `last_date >= SYSDATE` decides Apply vs Closed.
4. **Apply** — `doPost` verifies internship still open, checks duplicate via `SELECT COUNT`, then INSERT `status='Applied'` + `SYSDATE`. DB also has `UNIQUE(roll_no, internship_id)` as safety net.
5. **My Applications** — JOIN applications+internships filtered by session roll_no; shows status and coordinator remarks.
6. **Coordinator** — `coord.jsp` has publish form (`action=publish` inserts) and per-internship "View Applicants". `applicants.jsp` posts `action=update` setting status (Shortlisted/Rejected/Selected) + remarks — visible to applicant on My Applications.
7. **Access control** — every protected servlet does `getSession(false)` null check → `sendRedirect` to login; JSPs do the same inline. Logout invalidates session.

## Why this design
- One servlet per role-area with `action` param (same pattern as earlier CrudServlet lab) — fewer files, consistent structure.
- Parameterized statements everywhere — no SQL injection.
- Oracle `IDENTITY` columns + FK constraints + UNIQUE constraint enforce rules at DB level too.
- Messages: success/error via JSP attributes and redirect query params; Closed state shown per row.

## Likely viva questions
- Q: How is duplicate application prevented? A: servlet COUNT check + UNIQUE constraint.
- Q: How is last date enforced? A: `last_date >= SYSDATE` in view query (open_flag) and again in apply.
- Q: How is access control done? A: session check in every servlet/JSP; role check for coordinator pages.
- Q: Where are remarks stored? A: `applications.remarks`, updated by coordinator, read in myapps.jsp.
