package casestudy;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import servlets.DbConfig;

public class RegisterServlet extends HttpServlet {
    private static final String URL = DbConfig.getUrl();
    private static final String DBUSER = DbConfig.getDbUser();
    private static final String DBPASS = DbConfig.getDbPassword();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String roll = trim(req.getParameter("roll_no"));
        String name = trim(req.getParameter("name"));
        String branch = trim(req.getParameter("branch"));
        String semester = trim(req.getParameter("semester"));
        String email = trim(req.getParameter("email"));
        String phone = trim(req.getParameter("phone"));
        String password = trim(req.getParameter("password"));
        String ctx = req.getContextPath();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        if (roll.isEmpty() || name.isEmpty() || branch.isEmpty() || semester.isEmpty()
                || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            out.println(msg("error", "All fields are required.", ctx));
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            out.println(msg("error", "Invalid email address.", ctx));
            return;
        }
        if (!phone.matches("\\d{10}")) {
            out.println(msg("error", "Phone number must be 10 digits.", ctx));
            return;
        }
        if (password.length() < 4) {
            out.println(msg("error", "Password must be at least 4 characters.", ctx));
            return;
        }

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS)) {
                try (PreparedStatement chk = con.prepareStatement(
                        "SELECT COUNT(*) FROM applicants WHERE roll_no=? OR email=?")) {
                    chk.setString(1, roll);
                    chk.setString(2, email);
                    try (var rs = chk.executeQuery()) {
                        rs.next();
                        if (rs.getInt(1) > 0) {
                            out.println(msg("error", "Roll number or email already registered.", ctx));
                            return;
                        }
                    }
                }
                try (PreparedStatement ins = con.prepareStatement(
                        "INSERT INTO applicants (roll_no, sname, branch, semester, email, phone, password, role) "
                                + "VALUES (?,?,?,?,?,?,'student')")) {
                    ins.setString(1, roll);
                    ins.setString(2, name);
                    ins.setString(3, branch);
                    ins.setString(4, semester);
                    ins.setString(5, email);
                    ins.setString(6, phone);
                    ins.setString(7, password);
                    ins.executeUpdate();
                }
            }
        } catch (Exception e) {
            out.println(msg("error", "Registration failed: " + e.getMessage(), ctx));
            return;
        }
        out.println(msg("success", "Registration successful! You can now login.", ctx));
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }

    private static String msg(String type, String text, String ctx) {
        String color = "success".equals(type) ? "green" : "red";
        return "<html><body><h3 style='color:" + color + "'>" + text + "</h3>"
                + "<a href='" + ctx + "/case-study/login.html'>Go to Login</a> | "
                + "<a href='" + ctx + "/case-study/register.html'>Register Another</a></body></html>";
    }
}
