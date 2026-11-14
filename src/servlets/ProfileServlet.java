package servlets;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class ProfileServlet extends HttpServlet {
    private static final String URL = DbConfig.getUrl();
    private static final String USER = DbConfig.getDbUser();
    private static final String PASS = DbConfig.getDbPassword();

    // GET: read logged-in user from session, SELECT current details, forward to JSP
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        String username = (String) session.getAttribute("username");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT address, bio FROM profiles WHERE username=?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        req.setAttribute("address", rs.getString("address"));
                        req.setAttribute("bio", rs.getString("bio"));
                    }
                }
            }
        } catch (Exception e) {
            req.setAttribute("message", "Error: " + e.getMessage());
        }
        req.setAttribute("username", username);
        req.getRequestDispatcher("jsp/edit_profile.jsp").forward(req, resp);
    }

    // POST: DELETE / UPDATE / INSERT / then re-run doGet so the form is refreshed
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            resp.sendRedirect("login.html");
            return;
        }
        String username = (String) session.getAttribute("username");
        String address = req.getParameter("address");
        String bio = req.getParameter("bio");
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

                if ("delete".equals(req.getParameter("action"))) {
                    // 0) DELETE the profile
                    try (PreparedStatement ps = con.prepareStatement(
                            "DELETE FROM profiles WHERE username=?")) {
                        ps.setString(1, username);
                        ps.executeUpdate();
                    }
                    req.setAttribute("message", "Profile deleted successfully!");
                } else {
                    // 1) UPDATE first
                    int rows;
                    try (PreparedStatement ps = con.prepareStatement(
                            "UPDATE profiles SET address=?, bio=? WHERE username=?")) {
                        ps.setString(1, address);
                        ps.setString(2, bio);
                        ps.setString(3, username);
                        rows = ps.executeUpdate();
                    }

                    // 2) no existing row -> INSERT a new one
                    if (rows == 0) {
                        try (PreparedStatement ps = con.prepareStatement(
                                "INSERT INTO profiles(username, address, bio) VALUES(?,?,?)")) {
                            ps.setString(1, username);
                            ps.setString(2, address);
                            ps.setString(3, bio);
                            ps.executeUpdate();
                        }
                    }
                    req.setAttribute("message",
                            rows == 0 ? "Profile created successfully!" : "Profile updated successfully!");
                }
            }
        } catch (Exception e) {
            req.setAttribute("message", "Error: " + e.getMessage());
        }
        doGet(req, resp);
    }
}
