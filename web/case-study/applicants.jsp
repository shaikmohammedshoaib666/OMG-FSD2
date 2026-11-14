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
    String iid = (String) request.getAttribute("iid");
    List<String[]> rows = (List<String[]>) request.getAttribute("applicants");
%>
<html><head><title>Applicants</title>
<style>
body{font-family:Arial;margin:0;background:#f0f4f8}
.top{background:#6c3483;color:#fff;padding:12px 30px}
.top a{color:#fff;text-decoration:none;margin-left:15px}
.wrap{max-width:1100px;margin:25px auto}
.card{background:#fff;border-radius:10px;padding:25px;box-shadow:0 4px 12px rgba(0,0,0,.08)}
table{width:100%;border-collapse:collapse}
th,td{border:1px solid #ddd;padding:8px;text-align:left;font-size:14px}
th{background:#6c3483;color:#fff}
input,select{padding:7px;border:1px solid #ccc;border-radius:5px}
button{padding:7px 12px;background:#6c3483;color:#fff;border:none;border-radius:5px;cursor:pointer}
</style></head><body>
<div class="top"><b>Coordinator Panel</b>
<a href="<%= ctx %>/cs/coord">Back to Dashboard</a>
<a href="<%= ctx %>/cs/logout">Logout</a></div>
<div class="wrap"><div class="card">
<h2>Applicants - Internship #<%= iid %></h2>
<table><tr><th>Roll No</th><th>Name</th><th>Branch</th><th>Applied On</th><th>Status</th><th>Remarks</th><th>Update</th></tr>
<% if (rows != null) { for (String[] r : rows) { %>
<tr><td><%= r[1] %></td><td><%= r[2] %></td><td><%= r[3] %></td><td><%= r[6] %></td><td><%= r[4] %></td>
<td><%= r[5] %></td>
<td><form method="post" action="<%= ctx %>/cs/coord">
<input type="hidden" name="action" value="update"/>
<input type="hidden" name="app_id" value="<%= r[0] %>"/>
<input type="hidden" name="done" value="1"/>
<select name="status">
<option value="Shortlisted">Shortlisted</option>
<option value="Rejected">Rejected</option>
<option value="Selected">Selected</option>
</select>
<input type="text" name="remarks" placeholder="Remarks" style="width:140px"/>
<button type="submit">Save</button></form>
<form method="post" action="<%= ctx %>/cs/coord" style="display:inline;margin-left:5px">
<input type="hidden" name="action" value="delete"/>
<input type="hidden" name="app_id" value="<%= r[0] %>"/>
<button type="submit" style="background:#c0392b">Delete</button></form></td></tr>
<% } } else { %><tr><td colspan="7">No applicants yet.</td></tr><% } %>
</table>
</div></div></body></html>
