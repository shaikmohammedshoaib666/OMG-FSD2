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
    String q = (String) request.getAttribute("q");
    String msg = request.getParameter("msg");
    String err = (String) request.getAttribute("err");
    List<String[]> rows = (List<String[]>) request.getAttribute("internships");
%>
<html><head><title>Internship Opportunities</title>
<style>
body{font-family:Arial;margin:0;background:#f0f4f8}
.top{background:#1a5276;color:#fff;padding:12px 30px}
.top a{color:#fff;text-decoration:none;margin-left:15px}
.wrap{max-width:1000px;margin:25px auto}
.card{background:#fff;border-radius:10px;padding:25px;box-shadow:0 4px 12px rgba(0,0,0,.08)}
table{width:100%;border-collapse:collapse}
th,td{border:1px solid #ddd;padding:9px;text-align:left;font-size:14px}
th{background:#1a5276;color:#fff}
input[type=text]{padding:9px;width:320px;border:1px solid #ccc;border-radius:5px}
button{padding:9px 16px;background:#1a5276;color:#fff;border:none;border-radius:5px;cursor:pointer}
.apply{background:#1a8a4c;padding:5px 12px;border:none;border-radius:4px;color:#fff;cursor:pointer;font-size:13px}
.closed{color:#999;font-size:12px}
.msg{color:green;font-weight:bold}.err{color:red;font-weight:bold}
</style></head><body>
<div class="top"><b>Internship Tracker</b>
<a href="<%= ctx %>/cs/home">Home</a>
<a href="<%= ctx %>/cs/internships?action=myapps">My Applications</a>
<a href="<%= ctx %>/cs/logout">Logout</a></div>
<div class="wrap"><div class="card">
<h2>Internship Opportunities</h2>
<% if (msg != null) { %><p class="msg"><%= msg %></p><% } %>
<% if (err != null) { %><p class="err"><%= err %></p><% } %>
<form method="get" action="<%= ctx %>/cs/internships">
<input type="hidden" name="action" value="view"/>
<input type="text" name="q" value="<%= q == null ? "" : q %>" placeholder="Search organisation, role or location"/>
<button type="submit">Search</button>
</form>
<br/>
<table><tr><th>Org</th><th>Role</th><th>Eligibility</th><th>Location</th><th>Stipend</th><th>Last Date</th><th></th></tr>
<% if (rows != null) {
   for (String[] r : rows) {
     boolean open = "1".equals(r[7]); %>
<tr><td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td><td><%= r[4] %></td>
<td><%= r[5] %></td><td><%= r[6] %></td>
<td><% if (open) { %>
<form method="post" action="<%= ctx %>/cs/internships" style="display:inline">
<input type="hidden" name="internship_id" value="<%= r[0] %>"/>
<button class="apply" type="submit">Apply</button></form>
<% } else { %><span class="closed">Closed</span><% } %></td></tr>
<% } } else { %><tr><td colspan="7">No internships found.</td></tr><% } %>
</table>
</div></div></body></html>
