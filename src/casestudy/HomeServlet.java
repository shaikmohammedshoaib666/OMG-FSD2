package casestudy;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String ctx = req.getContextPath();
        if (session == null || session.getAttribute("roll_no") == null) {
            resp.sendRedirect(ctx + "/case-study/login.html");
            return;
        }
        if ("coordinator".equals(session.getAttribute("role"))) {
            resp.sendRedirect(ctx + "/cs/coord");
            return;
        }
        req.getRequestDispatcher("/case-study/home.jsp").forward(req, resp);
    }
}
