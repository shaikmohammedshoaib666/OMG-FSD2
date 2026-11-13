<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%
    HttpSession ses = request.getSession(false);
    if (ses == null || ses.getAttribute("roll_no") == null
            || !"coordinator".equals(ses.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/case-study/login.html");
        return;
    }
    String ctx = request.getContextPath();
    String name = (String) ses.getAttribute("name");
    String msg = request.getParameter("msg");
    String err = (String) request.getAttribute("err");
    List<String[]> rows = (List<String[]>) request.getAttribute("internships");
%>
<html><head><title>Coordinator Panel</title>
<style>
body{font-family:Arial;margin:0;background:#f0f4f8}
.top{background:#6c3483;color:#fff;padding:12px 30px}
.top a{color:#fff;text-decoration:none;margin-left:15px}
.wrap{max-width:1000px;margin:25px auto}
.card{background:#fff;border-radius:10px;padding:25px;box-shadow:0 4px 12px rgba(0,0,0,.08);margin-bottom:20px}
table{width:100%;border-collapse:collapse}
th,td{border:1px solid #ddd;padding:8px;text-align:left;font-size:14px}
th{background:#6c3483;color:#fff}
input,select{padding:8px;border:1px solid #ccc;border-radius:5px}
button{padding:9px 16px;background:#6c3483;color:#fff;border:none;border-radius:5px;cursor:pointer}
.msg{color:green;font-weight:bold}.err{color:red;font-weight:bold}
</style></head><body>
<div class="top"><b>Coordinator Panel</b>
<a href="<%= ctx %>/cs/logout">Logout (<%= name %>)</a></div>
<div class="wrap">
<% if (msg != null) { %><p class="msg"><%= msg %></p><% } %>
<% if (err != null) { %><p class="err"><%= err %></p><% } %>
<div class="card"><h2>Publish Internship Opportunity</h2>
<form method="post" action="<%= ctx %>/cs/coord">
<input type="hidden" name="action" value="publish"/>
Organisation: <input type="text" name="org" required/>
Role: <input type="text" name="role" required/>
Eligibility: <input type="text" name="eligibility" placeholder="e.g. CSE 5th sem"/>
Location: <input type="text" name="location" placeholder="e.g. Hyderabad"/>
Stipend: <input type="text" name="stipend" placeholder="e.g. 15k/month"/>
Last Date: <input type="date" name="last_date" required/>
<button type="submit">Publish</button>
</form></div>
<div class="card"><h2>Published Internships</h2>
<table><tr><th>ID</th><th>Organisation</th><th>Role</th><th>Last Date</th><th>Applicants</th></tr>
<% if (rows != null) { for (String[] r : rows) { %>
<tr><td><%= r[0] %></td><td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td>
<td><a href="<%= ctx %>/cs/coord?action=applicants&iid=<%= r[0] %>">View Applicants</a></td></tr>
<% } } else { %><tr><td colspan="5">No internships published yet.</td></tr><% } %>
</table></div>
</div></body></html>
