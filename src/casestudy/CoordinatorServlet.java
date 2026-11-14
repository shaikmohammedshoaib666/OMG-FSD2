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

public class CoordinatorServlet extends HttpServlet {
    private static final String URL = DbConfig.getUrl();
    private static final String DBUSER = DbConfig.getDbUser();
    private static final String DBPASS = DbConfig.getDbPassword();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();
        if (session == null || session.getAttribute("roll_no") == null
                || !"coordinator".equals(session.getAttribute("role"))) {
            resp.sendRedirect(ctx + "/case-study/login.html");
            return;
        }
        String action = req.getParameter("action");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS)) {
                if ("applicants".equals(action)) {
                    int iid = Integer.parseInt(req.getParameter("iid"));
                    loadApplicants(con, iid, req);
                    req.getRequestDispatcher("/case-study/applicants.jsp").forward(req, resp);
                    return;
                }
                loadInternships(con, req);
            }
        } catch (Exception e) {
            req.setAttribute("err", "Database error: " + e.getMessage());
        }
        req.getRequestDispatcher("/case-study/coord.jsp").forward(req, resp);
    }

    private void loadInternships(Connection con, HttpServletRequest req) throws Exception {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT id, org, role, TO_CHAR(last_date,'YYYY-MM-DD') AS last_date "
                        + "FROM internships ORDER BY last_date DESC");
             ResultSet rs = ps.executeQuery()) {
            java.util.List<String[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new String[]{String.valueOf(rs.getInt("id")), rs.getString("org"),
                        rs.getString("role"), rs.getString("last_date")});
            }
            req.setAttribute("internships", rows);
        }
    }

    private void loadApplicants(Connection con, int iid, HttpServletRequest req) throws Exception {
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT a.id AS app_id, a.roll_no, ap.sname, ap.branch, a.status, a.remarks, "
                        + "TO_CHAR(a.applied_date,'YYYY-MM-DD') AS applied_date "
                        + "FROM applications a JOIN applicants ap ON a.roll_no = ap.roll_no "
                        + "WHERE a.internship_id=? ORDER BY a.applied_date DESC");
             ) {
            ps.setInt(1, iid);
            try (ResultSet rs = ps.executeQuery()) {
                java.util.List<String[]> rows = new java.util.ArrayList<>();
                while (rs.next()) {
                    rows.add(new String[]{
                            String.valueOf(rs.getInt("app_id")), rs.getString("roll_no"),
                            rs.getString("sname"), rs.getString("branch"),
                            rs.getString("status") == null ? "" : rs.getString("status"),
                            rs.getString("remarks") == null ? "" : rs.getString("remarks"),
                            rs.getString("applied_date")});
                }
                req.setAttribute("applicants", rows);
            }
        }
        req.setAttribute("iid", String.valueOf(iid));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();
        if (session == null || session.getAttribute("roll_no") == null
                || !"coordinator".equals(session.getAttribute("role"))) {
            resp.sendRedirect(ctx + "/case-study/login.html");
            return;
        }
        String action = req.getParameter("action");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS)) {
                if ("publish".equals(action)) {
                    publish(con, req);
                } else if ("update".equals(action)) {
                    updateStatus(con, req);
                } else if ("delete".equals(action)) {
                    deleteApplication(con, req);
                }
            }
        } catch (Exception e) {
            req.setAttribute("err", "Error: " + e.getMessage());
        }
        resp.sendRedirect(ctx + "/cs/coord?msg=" + java.net.URLEncoder.encode(
                "success".equals(req.getParameter("done")) ? "Done" : "Check console", "UTF-8"));
    }

    private void publish(Connection con, HttpServletRequest req) throws Exception {
        String org = req.getParameter("org").trim();
        String role = req.getParameter("role").trim();
        String elig = req.getParameter("eligibility").trim();
        String loc = req.getParameter("location").trim();
        String stip = req.getParameter("stipend").trim();
        String lastDate = req.getParameter("last_date");
        if (org.isEmpty() || role.isEmpty() || lastDate == null || lastDate.isEmpty()) return;
        try (PreparedStatement ps = con.prepareStatement(
                "INSERT INTO internships (org, role, eligibility, location, stipend, last_date) "
                        + "VALUES (?,?,?,?,?,TO_DATE(?,'YYYY-MM-DD'))")) {
            ps.setString(1, org);
            ps.setString(2, role);
            ps.setString(3, elig);
            ps.setString(4, loc);
            ps.setString(5, stip);
            ps.setString(6, lastDate);
            ps.executeUpdate();
        }
    }

    private void updateStatus(Connection con, HttpServletRequest req) throws Exception {
        int appId = Integer.parseInt(req.getParameter("app_id"));
        String status = req.getParameter("status");
        String remarks = req.getParameter("remarks");
        if (remarks == null) remarks = "";
        if (!"Shortlisted".equals(status) && !"Rejected".equals(status) && !"Selected".equals(status))
            return;
        try (PreparedStatement ps = con.prepareStatement(
                "UPDATE applications SET status=?, remarks=? WHERE id=?")) {
            ps.setString(1, status);
            ps.setString(2, remarks);
            ps.setInt(3, appId);
            ps.executeUpdate();
        }
    }

    private void deleteApplication(Connection con, HttpServletRequest req) throws Exception {
        int appId = Integer.parseInt(req.getParameter("app_id"));
        try (PreparedStatement ps = con.prepareStatement(
                "DELETE FROM applications WHERE id=?")) {
            ps.setInt(1, appId);
            ps.executeUpdate();
        }
    }
}
