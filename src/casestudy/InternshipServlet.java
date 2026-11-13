package casestudy;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servlets.DbConfig;

public class InternshipServlet extends HttpServlet {
    private static final String URL = DbConfig.getUrl();
    private static final String DBUSER = DbConfig.getDbUser();
    private static final String DBPASS = DbConfig.getDbPassword();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();
        if (session == null || session.getAttribute("roll_no") == null
                || "coordinator".equals(session.getAttribute("role"))) {
            resp.sendRedirect(ctx + "/case-study/login.html");
            return;
        }
        String action = req.getParameter("action");
        if (action == null) action = "view";

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS)) {
                if ("myapps".equals(action)) {
                    loadMyApps(con, session, req);
                } else {
                    loadInternships(con, req);
                }
            }
        } catch (Exception e) {
            req.setAttribute("err", "Database error: " + e.getMessage());
        }
        req.getRequestDispatcher("myapps".equals(action) ? "/case-study/myapps.jsp" : "/case-study/internships.jsp")
                .forward(req, resp);
    }

    private void loadInternships(Connection con, HttpServletRequest req) throws Exception {
        String q = req.getParameter("q");
        q = (q == null) ? "" : q.trim();
        req.setAttribute("q", q);
        String sql = "SELECT id, org, role, eligibility, location, stipend, "
                + "TO_CHAR(last_date,'YYYY-MM-DD') AS last_date, "
                + "CASE WHEN last_date >= SYSDATE THEN 1 ELSE 0 END AS open_flag "
                + "FROM internships WHERE UPPER(org) LIKE ? OR UPPER(role) LIKE ? OR UPPER(location) LIKE ? "
                + "ORDER BY last_date";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + q.toUpperCase() + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                java.util.List<String[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new String[]{
                            String.valueOf(rs.getInt("id")), rs.getString("org"), rs.getString("role"),
                            rs.getString("eligibility"), rs.getString("location"), rs.getString("stipend"),
                            rs.getString("last_date"), String.valueOf(rs.getInt("open_flag"))});
                }
                req.setAttribute("internships", rows);
            }
        }
    }

    private void loadMyApps(Connection con, HttpSession session, HttpServletRequest req) throws Exception {
        String roll = (String) session.getAttribute("roll_no");
        String sql = "SELECT a.id, i.org, i.role, TO_CHAR(a.applied_date,'YYYY-MM-DD') AS applied_date, "
                + "a.status, a.remarks FROM applications a JOIN internships i ON a.internship_id = i.id "
                + "WHERE a.roll_no = ? ORDER BY a.applied_date DESC";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, roll);
            try (ResultSet rs = ps.executeQuery()) {
                java.util.List<String[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new String[]{
                            String.valueOf(rs.getInt("id")), rs.getString("org"), rs.getString("role"),
                            rs.getString("applied_date"), rs.getString("status"), rs.getString("remarks")});
                }
                req.setAttribute("myapps", rows);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();
        if (session == null || session.getAttribute("roll_no") == null
                || "coordinator".equals(session.getAttribute("role"))) {
            resp.sendRedirect(ctx + "/case-study/login.html");
            return;
        }
        String roll = (String) session.getAttribute("roll_no");
        int internshipId;
        try {
            internshipId = Integer.parseInt(req.getParameter("internship_id"));
        } catch (Exception e) {
            resp.sendRedirect(ctx + "/cs/internships?action=view&msg="
                    + java.net.URLEncoder.encode("Invalid internship.", "UTF-8"));
            return;
        }

        String result;
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS)) {
                // Check last date
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM internships WHERE id=? AND last_date >= SYSDATE")) {
                    ps.setInt(1, internshipId);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) == 0) {
                            result = "Application closed - last date has passed.";
                            resp.sendRedirect(ctx + "/cs/internships?action=view&msg="
                                    + java.net.URLEncoder.encode(result, "UTF-8"));
                            return;
                        }
                    }
                }
                // Check duplicate
                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT COUNT(*) FROM applications WHERE roll_no=? AND internship_id=?")) {
                    ps.setString(1, roll);
                    ps.setInt(2, internshipId);
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) > 0) {
                            result = "You have already applied for this internship.";
                            resp.sendRedirect(ctx + "/cs/internships?action=view&msg="
                                    + java.net.URLEncoder.encode(result, "UTF-8"));
                            return;
                        }
                    }
                }
                // Insert application
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO applications (roll_no, internship_id, applied_date, status, remarks) "
                                + "VALUES (?, ?, SYSDATE, 'Applied', NULL)")) {
                    ps.setString(1, roll);
                    ps.setInt(2, internshipId);
                    ps.executeUpdate();
                }
                result = "Application submitted successfully!";
            }
        } catch (Exception e) {
            result = "Error: " + e.getMessage();
        }
        resp.sendRedirect(ctx + "/cs/internships?action=view&msg="
                + java.net.URLEncoder.encode(result, "UTF-8"));
    }
}
