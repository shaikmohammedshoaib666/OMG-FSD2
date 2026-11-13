package casestudy;

import java.io.IOException;
import java.io.PrintWriter;
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

public class LoginServlet extends HttpServlet {
    private static final String URL = DbConfig.getUrl();
    private static final String DBUSER = DbConfig.getDbUser();
    private static final String DBPASS = DbConfig.getDbPassword();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uid = req.getParameter("uid");
        String pwd = req.getParameter("password");
        String ctx = req.getContextPath();
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        if (uid == null || uid.trim().isEmpty() || pwd == null || pwd.trim().isEmpty()) {
            out.println("<html><body><h3 style='color:red'>Username/Email and Password are required.</h3>"
                    + "<a href='" + ctx + "/case-study/login.html'>Back to Login</a></body></html>");
            return;
        }

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            try (Connection con = DriverManager.getConnection(URL, DBUSER, DBPASS);
                 PreparedStatement ps = con.prepareStatement(
                         "SELECT roll_no, sname, role FROM applicants WHERE (roll_no=? OR email=?) AND password=?")) {
                ps.setString(1, uid.trim());
                ps.setString(2, uid.trim());
                ps.setString(3, pwd);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        HttpSession session = req.getSession();
                        session.setAttribute("roll_no", rs.getString("roll_no"));
                        session.setAttribute("name", rs.getString("sname"));
                        session.setAttribute("role", rs.getString("role"));
                        String role = rs.getString("role");
                        if ("coordinator".equalsIgnoreCase(role)) {
                            resp.sendRedirect(ctx + "/cs/coord");
                        } else {
                            resp.sendRedirect(ctx + "/cs/home");
                        }
                        return;
                    }
                }
            }
        } catch (Exception e) {
            out.println("<html><body><h3 style='color:red'>Database Error: " + e.getMessage() + "</h3></body></html>");
            return;
        }
        out.println("<html><body><h3 style='color:red'>Invalid credentials. Please try again.</h3>"
                + "<a href='" + ctx + "/case-study/login.html'>Back to Login</a></body></html>");
    }
}
