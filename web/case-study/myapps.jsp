<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("roll_no") == null
            || "coordinator".equals(ses.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/case-study/login.html");
        return;
    }
    String ctx = request.getContextPath();
    List<String[]> rows = (List<String[]>) request.getAttribute("myapps");
%>
<html><head><title>My Applications</title>
<style>
body{font-family:Arial;margin:0;background:#f0f4f8}
.top{background:#1a5276;color:#fff;padding:12px 30px}
.top a{color:#fff;text-decoration:none;margin-left:15px}
.wrap{max-width:1000px;margin:25px auto}
.card{background:#fff;border-radius:10px;padding:25px;box-shadow:0 4px 12px rgba(0,0,0,.08)}
table{width:100%;border-collapse:collapse}
th,td{border:1px solid #ddd;padding:9px;text-align:left;font-size:14px}
th{background:#1a5276;color:#fff}
.st-Applied{color:#b7950b;font-weight:bold}.st-Shortlisted{color:#1a5276;font-weight:bold}
.st-Selected{color:#1a8a4c;font-weight:bold}.st-Rejected{color:#c0392b;font-weight:bold}
</style></head><body>
<div class="top"><b>Internship Tracker</b>
<a href="<%= ctx %>/cs/home">Home</a>
<a href="<%= ctx %>/cs/internships?action=view">Internships</a>
<a href="<%= ctx %>/cs/logout">Logout</a></div>
<div class="wrap"><div class="card">
<h2>My Applications</h2>
<table><tr><th>#</th><th>Organisation</th><th>Role</th><th>Applied On</th><th>Status</th><th>Coordinator Remarks</th></tr>
<% if (rows != null) { int i=1; for (String[] r : rows) { %>
<tr><td><%= i++ %></td><td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td>
<td class="st-<%= r[4] %>"><%= r[4] %></td><td><%= r[5] %></td></tr>
<% } } else { %><tr><td colspan="6">No applications yet.</td></tr><% } %>
</table>
</div></div></body></html>
