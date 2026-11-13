<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("roll_no") == null) {
        response.sendRedirect(request.getContextPath() + "/case-study/login.html");
        return;
    }
    String name = (String) ses.getAttribute("name");
    String ctx = request.getContextPath();
%>
<html><head><title>Student Home</title>
<style>
body{font-family:Arial;margin:0;background:#f0f4f8}
.top{background:#1a5276;color:#fff;padding:15px 30px;display:flex;justify-content:space-between;align-items:center}
.top a{color:#fff;text-decoration:none;margin-left:15px}
.wrap{max-width:640px;margin:40px auto}
.card{background:#fff;border-radius:10px;padding:30px;box-shadow:0 4px 12px rgba(0,0,0,.08)}
h2{color:#1a5276;margin-top:0}
.menu a{display:block;background:#eaf2f8;border-left:5px solid #1a5276;padding:14px 18px;margin:10px 0;border-radius:6px;color:#1a5276;text-decoration:none;font-size:17px}
.menu a:hover{background:#d6eaf8}
</style></head><body>
<div class="top"><b>Internship Tracker</b><span><%= name %> &nbsp;|&nbsp; <a href="<%= ctx %>/cs/logout">Logout</a></span></div>
<div class="wrap"><div class="card">
<h2>Welcome, <%= name %>!</h2>
<div class="menu">
<a href="<%= ctx %>/cs/internships?action=view">View Internship Opportunities</a>
<a href="<%= ctx %>/cs/internships?action=view">Apply for Internship</a>
<a href="<%= ctx %>/cs/internships?action=myapps">My Applications</a>
<a href="<%= ctx %>/cs/logout">Logout</a>
</div>
</div></div>
</body></html>
